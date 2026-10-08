package com.example.library.dto;

import com.example.library.model.Genre;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookForm {

    private Long id;

    @NotBlank(message = "Название обязательно")
    @Size(min = 2, max = 100, message = "Название должно быть от 2 до 100 символов")
    private String title;

    @NotBlank(message = "Автор обязателен")
    @Size(min = 2, max = 80, message = "Имя автора должно быть от 2 до 80 символов")
    private String author;

    @NotNull(message = "Укажите год издания")
    @Min(value = 1450, message = "Год не может быть меньше 1450")
    @Max(value = 2100, message = "Год не может быть больше 2100")
    private Integer year;

    @NotNull(message = "Укажите количество страниц")
    @Min(value = 1, message = "Страниц должно быть не меньше 1")
    @Max(value = 5000, message = "Страниц должно быть не больше 5000")
    private Integer pages;

    @NotNull(message = "Выберите жанр")
    private Genre genre;

    @Size(max = 500, message = "Описание не должно быть длиннее 500 символов")
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public Integer getPages() { return pages; }
    public void setPages(Integer pages) { this.pages = pages; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
