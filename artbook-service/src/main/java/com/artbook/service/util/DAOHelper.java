package com.artbook.service.util;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DAOHelper {

    @Value("${DAO_HOST}")
    @NonNull
    private String host;
}
