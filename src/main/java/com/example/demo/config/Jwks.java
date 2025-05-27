//package com.example.demo.config;
//
//import com.nimbusds.jose.jwk.RSAKey;
//
//import java.security.KeyPair;
//import java.security.KeyPairGenerator;
//import java.security.interfaces.RSAPublicKey;
//import java.security.interfaces.RSAPrivateKey;
//import java.util.UUID;
//
//public final class Jwks {
//    private Jwks() {}
//
//    public static RSAKey generateRsa() {
//        KeyPair keyPair = generateRsaKey();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//
//        return new RSAKey.Builder(publicKey)
//                .privateKey(privateKey)
//                .keyID(UUID.randomUUID().toString())
//                .build();
//    }
//
//    private static KeyPair generateRsaKey() {
//        try {
//            KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
//            keyGenerator.initialize(2048);
//            return keyGenerator.generateKeyPair();
//        } catch (Exception ex) {
//            throw new IllegalStateException("Failed to generate RSA key pair", ex);
//        }
//    }
//}
//
