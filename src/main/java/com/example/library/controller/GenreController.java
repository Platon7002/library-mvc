package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.library.exception.GenreNotFoundException;
import com.example.library.model.Genre;
import com.example.library.service.BookService;

/** Контроллер №3: жанры (список жанров с количеством книг и книги выбранного жанра). */
@Controller
@RequestMapping("/genres")
public class GenreController {

    private final BookService bookService;

    public GenreController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("stats", bookService.genreStats());
        return "genres/list";
    }

    @GetMapping("/{code}")
    public String booksOfGenre(@PathVariable String code, Model model) {
        Genre genre = Genre.fromCode(code).orElseThrow(() -> new GenreNotFoundException(code));
        model.addAttribute("genre", genre);
        model.addAttribute("books", bookService.findByGenre(genre));
        return "genres/books";
    }
}
