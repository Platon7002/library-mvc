package com.example.library.model;

public class Book {

    private Long id;
    private String title;
    private String author;
    private int year;
    private int pages;
    private Genre genre;
    private String description;

    public Book() {
    }

    public Book(Long id, String title, String author, int year, int pages, Genre genre, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.genre = genre;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
