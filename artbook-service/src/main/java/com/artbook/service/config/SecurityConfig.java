package com.artbook.service.config;

import com.artbook.service.service.JwtAuthenticationEntryPoint;
import com.artbook.service.service.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow specific origins (replace with your frontend's actual URL on Render/Heroku)
        // During development you might use "http://localhost:3000", but in production
        // you must specify the live URL (e.g., "https://your-frontend-app.onrender.com")
        config.setAllowedOrigins(Arrays.asList("https://artbook-ui.onrender.com", "http://localhost:5173"));

        // Allow all headers
        config.addAllowedHeader("*");

        // Allow specific HTTP methods, including OPTIONS for preflight requests
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow credentials (e.g., cookies, authorization headers)
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config); // Apply this configuration to all paths
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)        // disable CSRF for stateless APIs
            .cors(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/credentials/signin").permitAll()
                .requestMatchers("/api/v1/status/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/images").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/images/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/images").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/v1/images/*/like").authenticated()
                .anyRequest().denyAll()
            )
            .formLogin(Customizer.withDefaults())       // Enables the default login page
            .httpBasic(Customizer.withDefaults())       // Enables API basic auth
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
