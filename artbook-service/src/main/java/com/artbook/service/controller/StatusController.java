package com.artbook.service.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

    // GET http://localhost:8080/api/v1/status/health
    @GetMapping("/health")
    public String checkHealth() {
        return "System is up and running!";
    }
}
