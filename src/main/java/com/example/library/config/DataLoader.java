package com.example.library.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.library.dto.BookForm;
import com.example.library.model.Genre;
import com.example.library.service.BookService;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookService bookService;

    public DataLoader(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {
        add("Мастер и Маргарита", "Михаил Булгаков", 1967, 480, Genre.CLASSIC,
                "Роман о визите дьявола в Москву 1930-х годов и о судьбе писателя, которого называют Мастером.");
        add("Хоббит, или Туда и обратно", "Джон Р. Р. Толкин", 1937, 310, Genre.FANTASY,
                "Путешествие хоббита Бильбо Бэггинса с отрядом гномов к Одинокой горе.");
        add("Краткая история времени", "Стивен Хокинг", 1988, 256, Genre.SCIENCE,
                "Популярный рассказ о Вселенной, чёрных дырах и природе времени.");
        add("Убийство в «Восточном экспрессе»", "Агата Кристи", 1934, 256, Genre.DETECTIVE,
                "Эркюль Пуаро расследует убийство в поезде, застрявшем в снегах.");
        add("Чистый код", "Роберт Мартин", 2008, 464, Genre.PROGRAMMING,
                "Как писать понятный и поддерживаемый код.");
        add("Effective Java", "Джошуа Блох", 2001, 416, Genre.PROGRAMMING,
                "Набор практических рекомендаций по языку Java.");
    }

    private void add(String title, String author, int year, int pages, Genre genre, String description) {
        BookForm form = new BookForm();
        form.setTitle(title);
        form.setAuthor(author);
        form.setYear(year);
        form.setPages(pages);
        form.setGenre(genre);
        form.setDescription(description);
        bookService.create(form);
    }
}
