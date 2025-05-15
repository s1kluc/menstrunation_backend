package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;





@RestController
@RequestMapping("/menstrunation/api")
public class UserController {

    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestBody Integer id){

        Optional<User> user = userRepository.findById(id);

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        User savedUser = userRepository.save(user);


        return ResponseEntity.status(201).body(savedUser);
    }

    @PutMapping("/editUser")
public ResponseEntity<User> editUser(@RequestBody User user) {

    Optional<User> existingUserOptional = userRepository.findById(user.getId());

    if (existingUserOptional.isPresent()) {
        User existingUser = existingUserOptional.get();


        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setBirthdate(user.getBirthdate());
        existingUser.setWeight(user.getWeight());
        existingUser.setHeight(user.getHeight());

        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    } else {
        return ResponseEntity.notFound().build();
    }
}

@PatchMapping("/editUser")
public ResponseEntity<User> updateUser(@RequestBody User user) {
    Optional<User> existingUserOptional = userRepository.findById(user.getId());

    if (existingUserOptional.isPresent()) {
        User existingUser = existingUserOptional.get();

        if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getBirthdate() != null) existingUser.setBirthdate(user.getBirthdate());
        if (user.getWeight() != 0) existingUser.setWeight(user.getWeight());
        if (user.getHeight() != 0) existingUser.setHeight(user.getHeight());

        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    } else {
        return ResponseEntity.notFound().build();
    }
}


    @DeleteMapping("/deleteUser")
    public ResponseEntity<User> deleteUser(@RequestBody Integer id) {
        Optional<User> user = userRepository.findById(id); 

    if (user.isPresent()) {
        userRepository.deleteById(id); 
        return ResponseEntity.noContent().build(); 
    } else {
        return ResponseEntity.notFound().build();
    }
    }






    
}
