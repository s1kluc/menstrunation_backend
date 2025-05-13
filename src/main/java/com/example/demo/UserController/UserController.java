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
@RequestMapping("/menstronation/api")
public class UserController {

    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/getUser")
    public ResponseEntity<User> getUser(@RequestBody Long id){

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
    // Проверка, существует ли пользователь с таким ID
    Optional<User> existingUserOptional = userRepository.findById(user.getId());

    if (existingUserOptional.isPresent()) {
        User existingUser = existingUserOptional.get();

        // Обновляем все поля
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setBirthdate(user.getBirthdate());
        existingUser.setWeight(user.getWeight());
        existingUser.setHeight(user.getHeight());

        // Сохраняем
        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    } else {
        // Пользователь не найден
        return ResponseEntity.notFound().build();
    }
}

@PatchMapping("/editUser")
public ResponseEntity<User> updateUser(@RequestBody User user) {
    // Проверка, существует ли пользователь с таким ID
    Optional<User> existingUserOptional = userRepository.findById(user.getId());

    if (existingUserOptional.isPresent()) {
        User existingUser = existingUserOptional.get();

        // Обновляем только те поля, которые были переданы
        if (user.getUsername() != null) existingUser.setUsername(user.getUsername());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());
        if (user.getBirthdate() != null) existingUser.setBirthdate(user.getBirthdate());
        if (user.getWeight() != 0) existingUser.setWeight(user.getWeight());
        if (user.getHeight() != 0) existingUser.setHeight(user.getHeight());

        // Сохраняем обновленного пользователя
        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    } else {
        // Если пользователь не найден
        return ResponseEntity.notFound().build();
    }
}


    @DeleteMapping("/deleteUser")
    public ResponseEntity<User> deleteUser(@RequestBody Long id) {
        Optional<User> user = userRepository.findById(id); 

    if (user.isPresent()) {
        userRepository.deleteById(id); 
        return ResponseEntity.noContent().build(); 
    } else {
        return ResponseEntity.notFound().build();
    }
    }






    
}
