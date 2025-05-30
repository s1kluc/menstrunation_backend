package com.example.demo.services;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.BCryptUtils;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.LoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

/**
 * Eine Serviceklasse welche sich um den Login kümmert.
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;
    private final BCryptUtils bCryptUtils;
    private final JwtUtils jwtUtils;
    private LoggedInUser loggedInUser;

    /**
     * Gibt bei erfolgreichem Login einen validen JWT mit einer Ablaufzeit von 1h zurück.
     *
     * @param user Der zu überprüfende User
     * @return Ein valider JWT
     * @throws HttpClientErrorException Wenn ein Fehler auftritt.
     */
    @Override
    public String login(User user) throws HttpClientErrorException {
        User foundUser = this.userRepository.findByUsername(user.getUsername());

        if (foundUser == null) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Der User konnte nicht gefunden werden.");
        } else if (!this.bCryptUtils.matchPassword(user.getPassword(), foundUser.getPassword())) {
            throw new HttpClientErrorException(
                HttpStatusCode.valueOf(401),
                "Der User oder das Passwort ist falsch du Affe leck :)B========D"
            );
        }

        this.loggedInUser = new LoggedInUser(user.getUsername(),user.getId(), user.getEmail());

        return this.jwtUtils.createToken(foundUser);
    }

    /**
     * Registriert einen neuen User und hasht das übergebene Passwort.
     *
     * @param user Der neue User
     */
    @Override
    public void register(User user) throws HttpClientErrorException {
        user.setUpdatedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(this.bCryptUtils.hashPassword(user.getPassword()));
        try {
            if (this.userRepository.existsUserByUsernameAndEmail(user.getUsername(), user.getEmail())) {
                throw new HttpClientErrorException(
                    HttpStatusCode.valueOf(403),
                    "Ein User mit dem Usernamen " + user.getUsername() + " existiert bereits."
                );
            }
            this.userRepository.save(user);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(500), e.getMessage());
        }
    }

    @Override
    public String refreshToken() throws HttpClientErrorException {
        return this.jwtUtils.createNewRefreshToken(this.loggedInUser);
    }
}
