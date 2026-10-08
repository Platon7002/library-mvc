package com.example.library.model;

import java.util.Optional;

public enum Genre {
    FANTASY("Фэнтези"),
    CLASSIC("Классика"),
    SCIENCE("Наука"),
    DETECTIVE("Детектив"),
    HISTORY("История"),
    PROGRAMMING("Программирование");

    private final String title;

    Genre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return name().toLowerCase();
    }

    public static Optional<Genre> fromCode(String code) {
        if (code == null) {
            return Optional.empty();
        }
        for (Genre genre : values()) {
            if (genre.getCode().equals(code.toLowerCase())) {
                return Optional.of(genre);
            }
        }
        return Optional.empty();
    }
}
