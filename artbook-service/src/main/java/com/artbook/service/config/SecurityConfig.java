package com.artbook.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String AUDIENCE = "artbook-service-generic-auth";
    private static final String ISSUER = "https://artbook-ui.onrender.com";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
            .authorizeHttpRequests(auth -> auth
                // 1. SPECIFIC RULE: Lock down POST requests to /api/v1/images
                .requestMatchers(HttpMethod.POST, "/api/v1/images").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/images/*/like").authenticated()

                // 2. SPECIFIC RULE but not authenticated
                //.requestMatchers(HttpMethod.POST, "/api/v1/credentials/reset").hasAnyRole(Roles.labels())

                // 3. CATCH-ALL RULE: Allow everything else
                .anyRequest().permitAll()
            )
            .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return new JwtDecoder() {
            @Override
            public Jwt decode(String token) throws JwtException {
                throw new UnsupportedOperationException();
            }
        };
    }
}
