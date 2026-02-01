package com.artbook.service.domain;

import static com.artbook.service.util.Preconditions.requireNonEmpty;

public record ResetResponse(String message) {
    public ResetResponse {
        requireNonEmpty(message, "message must not be empty");
    }
}
