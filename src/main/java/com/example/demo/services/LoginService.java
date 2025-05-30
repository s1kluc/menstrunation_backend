package com.example.demo.services;

import com.example.demo.model.User;
import org.springframework.web.client.HttpClientErrorException;

public interface LoginService {
    String login(User user) throws HttpClientErrorException;

    void register(User user) throws HttpClientErrorException;

    String refreshToken() throws HttpClientErrorException;
}
