package com.example.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoggedInUser {
    private final String username;
    private final Long id;
    private final String email;

}
