package com.artbook.service.domain;

import static com.artbook.service.util.Preconditions.requireNonEmpty;

public record SigninResponse(String token, String message) {
    public SigninResponse {
        if (token == null || token.isBlank()) {
            requireNonEmpty(message, "message cannot be empty when token is empty");
        }
        if (message == null || message.isBlank()) {
            requireNonEmpty(token, "token cannot be empty when message is empty");
        }
    }
}
