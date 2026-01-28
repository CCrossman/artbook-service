package com.artbook.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(@Value("${DATABASE_URL}") String dbUrl) throws URISyntaxException {
        // Parse the "postgres://..." string using Java's URI class
        URI uri = new URI(dbUrl);

        // Extract credentials
        String[] uriParts = uri.getUserInfo().split(":");
        String username = uriParts[0];
        String password = uriParts[1];

        // Reconstruct the JDBC URL
        // From: postgres://user:pass@host:port/path
        // To:   jdbc:postgresql://host:port/path
        String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath();

        // Configure the Pool
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);

        // Render requires SSL for external connections (and often internal ones)
        // This ensures the driver uses SSL
        config.addDataSourceProperty("ssl", "true");
        config.addDataSourceProperty("sslmode", "require");

        return new HikariDataSource(config);
    }
}
