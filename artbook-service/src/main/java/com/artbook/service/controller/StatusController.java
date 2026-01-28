package com.artbook.service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @Value("${app.location.config}")
    private String configPath;

    @Value("${app.location.storage}")
    private String storagePath;

    @Value("${health.verify.fail-hard}")
    private Boolean failHard;

    // GET http://localhost:8080/api/v1/status/health
    @GetMapping("/health")
    public String checkHealth() throws SQLException, IOException {
        // connect to artbook-db
        verifyDriverConnection();

        // connect to file space
        verifyPathUsable(configPath);
        verifyPathUsable(storagePath);

        // everything looks good
        return "System is up and running!";
    }

    private void verifyDriverConnection() throws SQLException {
        try {
            String postgresURL = System.getenv("DATABASE_URL");
            logger.atDebug().log("database url: " + postgresURL);

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
        } catch (SQLException sqlEx) {
            logger.error("verify driver connection failed", sqlEx);
            if (Boolean.TRUE.equals(failHard)) {
                throw sqlEx;
            }
        }
    }

    private void verifyPathUsable(String rawPath) throws IOException {
        try {
            Path path = Paths.get(rawPath);
            if (!Files.isReadable(path) || !Files.isWritable(path)) {
                throw new IOException("Cannot read or write '" + rawPath + "'");
            }
        } catch (InvalidPathException | IOException ex) {
            logger.error("verify path usable failed", ex);
            if (Boolean.TRUE.equals(failHard)) {
                throw ex;
            }
        }
    }
}
