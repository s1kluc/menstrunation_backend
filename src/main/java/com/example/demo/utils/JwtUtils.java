package com.example.demo.utils;

import com.example.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Service kümmert sich um das Jwt-handling
 */
@Service
public class JwtUtils {
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("meinSuperGeheimesPasswort1234567890!".getBytes());
    private static final long EXPIRATION_TIME = 3600_000;

    /**
     * Erstellt einen JWT
     *
     * @param user ein valider User
     * @return ein gültiger Jwt mit Laufzeit von 1h
     */
    public String createToken(User user) {
        return Jwts.builder()
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
            .claim("userId", user.getId())
            .claim("username", user.getUsername())
            .claim("email", user.getEmail())
            .compact();
    }

    /**
     * Methode parst einen übergebenen Jwt.
     *
     * @param token der zu parsende Token
     * @return ein geparster Jwt
     */
    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * Methode überprüft, ob der Jwt abgelaufen ist
     *
     * @param token der zu prüfende Token
     * @return True bei valid, False bei abgelaufen
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token.substring(7))
                .getBody();

            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Methode extrahiert benötigten Jwt aus dem Header und prüft auf Fehler im Jwt.
     *
     * @param token der Raw-Jwt aus dem Header
     * @return ein extrahierter Jwt
     * @throws HttpClientErrorException Wenn ein Fehler auftritt
     */
    public String returnJwt(String token) throws HttpClientErrorException {
        if (!token.startsWith("Bearer ")) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(403), "Der Token ist Fehlerhaft");
        } else if (token.isBlank()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404), "Der Token ist nicht gesetzt");
        } else if (this.isTokenExpired(token)) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(401), "Der Token ist Abgelaufen");
        }
        return token.substring(7);
    }
}
