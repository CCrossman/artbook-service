package com.artbook.service.config;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Roles {
    REGISTERED_ARTIST("registered-artist"),
    REGISTERED_VIEWER("registered-viewer");

    private static final String[] LABELS = Stream.of(values()).map(r -> r.label).toArray(String[]::new);

    private final String label;

    private Roles(String label) {
        this.label = label;
    }

    public static String[] labels() {
        return LABELS;
    }
}
