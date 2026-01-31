package com.artbook.service.domain;

import java.util.Collection;

public record ImageSearchRequest(
    String title, Collection<String> tags, String startDate, String endDate,
    Integer pageNo, Integer pageSize, String sortBy, String sortOrder
) {
}
