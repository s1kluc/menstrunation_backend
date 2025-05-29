package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.services.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Schnittstellen, welche sich um den Login und die Registration kümmern.
 */
@RestController
@RequestMapping(value = "/menstrunation/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    /**
     * Gibt bei erfolgreichem Login einen JWT zurück
     *
     * @param user der Übergebene user
     * @return ein valider JWT
     * @throws HttpClientErrorException Wenn ein Fehler auftritt
     */
    @PostMapping("/login")
    public String login(@RequestBody User user) throws HttpClientErrorException {
        return this.loginService.login(user);
    }

    /**
     * Registriert einen neuen User in der Datenbank.
     *
     * @param user der neue User
     * @throws HttpClientErrorException Wenn ein Fehler auftritt
     */
    @PostMapping("/register")
    public void register(@RequestBody User user) throws HttpClientErrorException {
        this.loginService.register(user);
    }
}
