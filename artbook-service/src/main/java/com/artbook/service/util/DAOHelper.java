package com.artbook.service.util;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DAOHelper {
    private static final Logger logger = LoggerFactory.getLogger(DAOHelper.class);

    @Value("${DAO_HOST}")
    @NonNull
    private String host;

    private RestClient restClient;

    @PostConstruct
    public void init() {
        restClient = RestClient.create(host);
    }
}
