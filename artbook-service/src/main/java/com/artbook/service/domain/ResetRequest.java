package com.artbook.service.domain;

import static com.artbook.service.util.Preconditions.requireNonEmpty;

public record ResetRequest(String email) {
    public ResetRequest {
        requireNonEmpty(email, "email must not be empty");
    }
}
