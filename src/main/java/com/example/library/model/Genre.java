package com.example.library.model;

import java.util.Optional;

/** Жанр книги. title — русское название для отображения на страницах. */
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

    /** Код для адреса страницы: /genres/fantasy */
    public String getCode() {
        return name().toLowerCase();
    }

    /** Ищет жанр по коду из адреса. Неизвестный код даёт пустой Optional. */
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
