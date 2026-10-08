package com.example.library.dto;

import com.example.library.model.Genre;

/** Жанр и количество книг в нём (для страницы «Жанры»). */
public class GenreStat {

    private final Genre genre;
    private final long count;

    public GenreStat(Genre genre, long count) {
        this.genre = genre;
        this.count = count;
    }

    public Genre getGenre() { return genre; }
    public long getCount() { return count; }
}
