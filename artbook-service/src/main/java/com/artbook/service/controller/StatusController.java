package com.artbook.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.sql.*;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    // GET http://localhost:8080/api/v1/status/health
    @GetMapping("/health")
    public void checkHealth() {
        logger.atDebug().log("Health check passed.");
    }
}
