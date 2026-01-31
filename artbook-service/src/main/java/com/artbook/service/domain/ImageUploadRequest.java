package com.artbook.service.domain;

import java.nio.ByteBuffer;
import java.util.Collection;

import static com.artbook.service.util.Preconditions.requireNonEmpty;
import static java.util.Objects.requireNonNull;

public record ImageUploadRequest(
    String title, String description, Collection<String> tags, ByteBuffer image
) {
    public ImageUploadRequest {
        requireNonEmpty(title, "title cannot be empty");
        requireNonNull(image, "image cannot be null");
    }
}
