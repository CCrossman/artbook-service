package com.artbook.service.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HealthChecker {
    private static final Logger logger = LoggerFactory.getLogger(HealthChecker.class);

    @Value("${app.location.config}")
    private String configPath;

    @Value("${app.location.storage}")
    private String storagePath;

    @Autowired(required = false)
    private DataSource dataSource;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    public void checkHealth(boolean failHard) throws SQLException, IOException {
        try {
            logger.info("datasourceUrl: " + datasourceUrl);
            logger.info("datasourceUsername: " + datasourceUsername);
            logger.info("datasourcePassword: " + dataSourcePassword);

            // connect to artbook-db
            verifyDriverConnection();

            // connect to file space
            verifyPathUsable(configPath);
            verifyPathUsable(storagePath);
        } catch (RuntimeException | IOException | SQLException ex) {
            logger.error("checkHealth threw an exception", ex);
            if (failHard) {
                throw ex;
            }
        }
    }

    private void verifyDriverConnection() throws SQLException {
        logger.info("verifyDriverConnection(" + dataSource + ")");
        if (dataSource != null) {
            try (Connection conn = dataSource.getConnection()) {
                try (PreparedStatement stmt = conn.prepareStatement("SELECT 1")) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        boolean hasRecord = rs.next();
                        if (!hasRecord) {
                            throw new SQLException("SELECT 1 returned nothing");
                        }
                    }
                }
            }
        }
    }

    private void verifyPathUsable(String rawPath) throws InvalidPathException, IOException {
        logger.info("verifyPathUsable(" + rawPath + ")");
        // FIXME
//        Path path = Paths.get(rawPath);
//        Files.createDirectories(path, OWNER_RWE);
//
//        if (!Files.isReadable(path) || !Files.isWritable(path)) {
//            throw new IOException("Cannot read or write '" + rawPath + "'");
//        }
    }
}
