package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VibeRepository;
import com.example.demo.utils.BCryptUtils;
import com.example.demo.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Schnittstelle die sich um ein UserObjekt kümmert.
 */
@RestController
@RequestMapping("/menstrunation/api")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptUtils bCryptUtils;
    private final VibeRepository vibeRepository;

    /**
     * Liefert anhand des JWT den User zurück
     *
     * @param bearerToken ein Bearer Token
     * @return den gefundenen User
     * @throws HttpClientErrorException Wenn ein Fehler auftritt
     */
    @GetMapping("/getUser")
    public ResponseEntity<User> getUser(
        @RequestHeader("Authorization") String bearerToken
    ) throws
      HttpClientErrorException {

        Optional<User> user = this.userRepository.findById(
            this.jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken)).get("userId", Long.class)
        );

        return user.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Aktualisiert einen User
     *
     * @param bearerToken ein Bearer Token
     * @param user        der zu aktualisierende User
     * @return Der aktualisierte User
     * @throws HttpClientErrorException Wenn ein Fehler auftritt
     */
    @PutMapping("/editUser")
    public ResponseEntity<User> editUser(@RequestHeader("Authorization") String bearerToken, @RequestBody User user)
        throws HttpClientErrorException {

        Optional<User> existingUserOptional = this.userRepository.findById(this.jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken))
                                                                               .get("userId", Long.class));

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setBirthdate(user.getBirthdate());
            existingUser.setWeight(user.getWeight());
            existingUser.setHeight(user.getHeight());
            existingUser.setPassword(this.bCryptUtils.hashPassword(user.getPassword()));
            existingUser.setUpdatedAt(LocalDate.now().atStartOfDay());

            User updatedUser = this.userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Aktualisiert einen User
     *
     * @param bearerToken ein Bearer Token
     * @param user        der zu aktualisierende User
     * @return Der aktualisierte User
     * @throws HttpClientErrorException Wenn ein Fehler auftritt
     */
    @PatchMapping("/editUser")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String bearerToken, @RequestBody User user)
        throws HttpClientErrorException {
        Optional<User> existingUserOptional = this.userRepository.findById(this.jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken))
                                                                               .get("userId", Long.class));

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            if (user.getUsername() != null) {
                existingUser.setUsername(user.getUsername());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getBirthdate() != null) {
                existingUser.setBirthdate(user.getBirthdate());
            }
            if (user.getWeight() != 0) {
                existingUser.setWeight(user.getWeight());
            }
            if (user.getHeight() != 0) {
                existingUser.setHeight(user.getHeight());
            }
            user.setUpdatedAt(LocalDate.now().atStartOfDay());
            User updatedUser = this.userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Löscht einen User aus der Datenbank
     *
     * @param bearerToken ein Token mit einem zugehörigen User
     * @return No Content, da user erfolgreich gelöscht wurde
     * @throws HttpClientErrorException Wenn ein Fehler auftritt
     */
    @DeleteMapping("/deleteUser")
    @Transactional
    public ResponseEntity<User> deleteUser(@RequestHeader("Authorization") String bearerToken) throws HttpClientErrorException {
        Optional<User> user = userRepository.findById(this.jwtUtils.parseToken(this.jwtUtils.returnJwt(bearerToken))
                                                          .get("userId", Long.class));

        if (user.isPresent()) {
            //Löscht alle bestehenden Vibes, damit keine Leichen in der Datenbank bleiben.
            this.vibeRepository.deleteVibesByUserId(user.get().getId());

            //Löscht zuerst Vibes, dann User, weil Fremdschlüssel
            this.userRepository.deleteById(user.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
