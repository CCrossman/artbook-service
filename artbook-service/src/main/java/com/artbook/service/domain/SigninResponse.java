package com.artbook.service.domain;

import static com.artbook.service.util.Preconditions.requireNonEmpty;

public record SigninResponse(String token) {
    public SigninResponse {
        requireNonEmpty(token, "token cannot be empty");
    }
}
