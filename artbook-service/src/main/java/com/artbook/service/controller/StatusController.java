package com.artbook.service.controller;

import com.artbook.service.model.HealthChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.*;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @Value("${health.verify.fail-hard}")
    private Boolean failHard;

    @Autowired
    private HealthChecker healthChecker;

    // GET http://localhost:8080/api/v1/status/health
    @GetMapping("/health")
    public void checkHealth() throws SQLException, IOException {
        boolean shouldFailHard = Boolean.TRUE.equals(failHard);
        healthChecker.checkHealth(shouldFailHard);
    }
}
