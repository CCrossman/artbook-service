package com.artbook.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {

    @Value("{app.location.config}")
    private String configPath;

    // GET http://localhost:8080/api/v1/status/health
    @GetMapping("/health")
    public String checkHealth() throws SQLException, IOException {
        String postgresURL = System.getenv("DATABASE_URL");

        // connect to artbook-db
        try (Connection conn = DriverManager.getConnection(postgresURL)) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT 1")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    boolean hasRecord = rs.next();
                    if (!hasRecord) {
                        throw new SQLException("SELECT 1 returned nothing");
                    }
                }
            }
        }

        // connect to file space
        if (!Files.exists(Paths.get(configPath))) {
            throw new IOException("'" + configPath + "' does not exist");
        }

        // everything looks good
        return "System is up and running!";
    }
}
