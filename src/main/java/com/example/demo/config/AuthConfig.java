package com.example.demo.config;


import com.example.demo.repository.UserRepository;
import com.example.demo.services.JpaUserDetailsService;
import com.example.demo.utils.BCryptUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



import java.security.Key;
import java.util.UUID;

@Configuration
public class AuthConfig {

    @Value("${menstrunation.host}")
    private String host;

    private final BCryptUtils bCryptUtils;
    private final UserRepository userRepository;


    private static final String SECRET = "my-secret-key-my-secret-key-my-secret-key!";// must be 256-bit for HS25

    public AuthConfig(BCryptUtils bCryptUtils, UserRepository userRepository) {
        this.bCryptUtils = bCryptUtils;
        this.userRepository = userRepository;
    }

    private static Key getSigningKey() {
        byte[] keyBytes = SECRET.getBytes(); // in production use: Decoders.BASE64.decode(...)
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http
                .securityMatcher("/oauth2/**", "/.well-known/**")
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()))
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults()); // Enable OpenID Connect 1.0


        return http.build();
    }

    // 2️⃣ Your app endpoints like /register, /login, etc.
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, JpaUserDetailsService service) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/menstrunation/api/register", "/menstrunation/api/login", "/","http://localhost:8080/login/oauth2/code/client-oidc").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider(service))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))// optional, useful for manual login testing
                .csrf(AbstractHttpConfigurer::disable); // disable CSRF if you're testing with Postman or APIs

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder encoder) {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret(encoder.encode("secret"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
                .redirectUri("http://localhost:8080/menstrunation/api/login")
                .scope(OidcScopes.OPENID)
                .scope("read")
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }


    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            if (context.getTokenType().getValue().equals("access_token")|| context.getTokenType().getValue().equals("id_token")) {
                Authentication principal = context.getPrincipal();
                com.example.demo.model.User user = userRepository.findByUsername(principal.getName()).get();
                context.getClaims().claim("userId", user.getId());
                    context.getClaims().claim("username", user.getUsername());
                    context.getClaims().claim("email", user.getEmail());
                    // Add more claims if needed
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService(JpaUserDetailsService service) {
        return service;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptUtils)
                .and()
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptUtils);
        return provider;
    }


    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();// Use your own key generation
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer(host)
                .build();
    }


}
