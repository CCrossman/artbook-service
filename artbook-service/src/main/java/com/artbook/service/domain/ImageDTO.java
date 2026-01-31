package com.artbook.service.domain;

import java.net.URI;
import java.util.Set;

public record ImageDTO(
    long imageId, URI imageURI, ImageType imageType,
    String title, String description, Integer likes,
    Boolean likedByViewer, Set<ImageTag> tags
) {
}
