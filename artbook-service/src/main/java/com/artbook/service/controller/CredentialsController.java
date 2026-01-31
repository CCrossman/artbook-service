package com.artbook.service.controller;

import com.artbook.service.domain.SigninRequest;
import com.artbook.service.domain.SigninResponse;
import com.artbook.service.util.DAOHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/credentials")
public class CredentialsController {
    private static final Logger logger = LoggerFactory.getLogger(CredentialsController.class);

    @Autowired
    private DAOHelper daoHelper;

    @PostMapping("/reset")
    public String reset(@RequestParam(name = "email") String email) {
        logger.info("Resetting credentials for '{}'...", email);
        return "Not Yet Implemented";
    }

    @PostMapping("/signin")
    public SigninResponse signIn(@RequestBody SigninRequest request) {
        logger.info("Signing in...");
        return new SigninResponse(null, "Not Yet Implemented");
    }
}
