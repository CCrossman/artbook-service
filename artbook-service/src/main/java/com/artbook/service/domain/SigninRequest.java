package com.artbook.service.domain;

import static com.artbook.service.util.Preconditions.requireNonEmpty;

public record SigninRequest(String email, String password) {
    public SigninRequest {
        requireNonEmpty(email, "email cannot be empty");
        requireNonEmpty(password, "password cannot be empty");
    }
}
