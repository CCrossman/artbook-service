package com.artbook.service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private static final Duration EXPIRATION_TIME = Duration.ofDays(1);

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    public String generateToken(Authentication authentication) {
        // Get the logged-in user's details
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        logger.info("Generating JWT token for user: {}", userPrincipal.getUsername());

        Instant now = Instant.now();

        // example of adding "limitations" via the JWT token
        Map<String, Object> limits = new HashMap<>();

        return Jwts.builder()
            .claim("limits", limits)
            .claim("permissions", userPrincipal.getAuthorities())
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(EXPIRATION_TIME)))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
