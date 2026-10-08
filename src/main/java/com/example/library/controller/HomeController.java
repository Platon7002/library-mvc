package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.library.service.BookService;

@Controller
public class HomeController {

    private final BookService bookService;

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("total", bookService.findAll().size());
        model.addAttribute("genreCount", bookService.genreStats().size());
        model.addAttribute("latest", bookService.latest(3));
        return "index";
    }

    @GetMapping("/test-500")
    public String testServerError() {
        throw new IllegalStateException("Тестовая ошибка для проверки страницы 500");
    }
}
