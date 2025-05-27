package com.example.demo.tokenController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtGenerator {

    private static final String SECRET = "my-secret-key-my-secret-key-my-secret-key!"; // must be 256-bit for HS256

    private static Key getSigningKey() {
        byte[] keyBytes = SECRET.getBytes(); // in production use: Decoders.BASE64.decode(...)
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuer("menstruNation")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
