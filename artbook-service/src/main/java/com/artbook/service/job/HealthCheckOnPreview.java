package com.artbook.service.job;

import com.artbook.service.controller.StatusController;
import com.artbook.service.model.HealthChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
@ConditionalOnProperty(name = "IS_PULL_REQUEST", havingValue = "true", matchIfMissing = false)
public class HealthCheckOnPreview implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HealthCheckOnPreview.class);

    @Autowired
    private HealthChecker healthChecker;

    @Override
    public void run(String... args) throws SQLException, IOException {
        logger.info("✅ Environment variable is true. Checking health immediately and report to GitHub.");
        healthChecker.checkHealth(true);
    }
}
