package com.example.demo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Eine Hilfsklasse welche sich um das Hashen kümmert.
 */
@RequiredArgsConstructor
@Service
public class BCryptUtils implements PasswordEncoder {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Hasht und salted ein übergebenes Passwort
     * @param password das Passwort als String
     * @return ein gesaltetes und gehashtes Passwort
     */
    private String hashPassword(String password) {
        return encoder.encode(password);
    }

    /**
     * Überprüft, ob das übergebene Passwort mit dem gehashten Passwort in der Datenbank übereinstimmt
     * @param password das zu prüfende Passwort
     * @param hashed das gehashte Passwort aus der Datenbank
     * @return True Passwörter stimmen, False Passwörter stimmen nicht überein.
     */
    public boolean matchPassword(String password, String hashed) {
        return encoder.matches(password, hashed);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return hashPassword(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return matchPassword(rawPassword.toString(), encodedPassword);
    }
}
