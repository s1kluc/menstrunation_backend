package com.example.demo.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Exceptionhandler, welcher sich um geworfene Exceptions kümmert
 */
@ControllerAdvice
public class ExceptionHandler {
    /**
     * Hier wurde einfachheitshalber die HttpClientErrorException-Klasse verwendet um CustomExceptions zu vermeiden.
     * @param ex Die geworfene Fehlermeldung
     * @return Eine neue Response-Entity, welche den Fehler zurückgibt
     */
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<String> handleHttpClientExceptions(HttpClientErrorException ex) {
        return switch (ex.getStatusCode()) {
            case HttpStatus.NOT_FOUND -> new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            case HttpStatus.UNAUTHORIZED -> new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
            case HttpStatus.INTERNAL_SERVER_ERROR -> new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            case HttpStatus.BAD_REQUEST -> new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            default -> null;
        };
    }
}
