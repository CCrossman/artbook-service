package com.artbook.service.domain;

import java.util.Set;

public record ImageDTO(
    long imageId, String extension, ImageType imageType,
    String title, String description, Integer likes,
    Boolean liked, Set<ImageTag> tags, String encodedData
) {
}
