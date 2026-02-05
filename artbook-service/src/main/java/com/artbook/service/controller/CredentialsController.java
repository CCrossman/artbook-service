package com.artbook.service.controller;

import com.artbook.service.domain.ResetRequest;
import com.artbook.service.domain.ResetResponse;
import com.artbook.service.domain.SigninRequest;
import com.artbook.service.domain.SigninResponse;
import com.artbook.service.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credentials")
public class CredentialsController {
    private static final Logger logger = LoggerFactory.getLogger(CredentialsController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/reset")
    public ResetResponse reset(@RequestBody ResetRequest request) {
        logger.info("Resetting credentials for '{}'...", request);
        return new ResetResponse("Not Yet Implemented");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SigninRequest signinRequest) {
        try {
            // Authenticate the user
            // This line will throw a BadCredentialsException if the password is wrong
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    signinRequest.email(),
                    signinRequest.password()
                )
            );

            // If no exception, authentication succeeded. Set the context.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate the JWT
            String jwt = jwtService.generateToken(authentication);

            // Return the token in the response
            return ResponseEntity.ok(new SigninResponse(jwt));
        } catch (Exception e) {
            String message = e.getClass().getName() + ": " + e.getMessage();
            logger.error("Authentication failed: {}", message);
            return ResponseEntity.badRequest().build();
        }
    }
}
