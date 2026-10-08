package com.example.library.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.library.dto.BookForm;
import com.example.library.dto.GenreStat;
import com.example.library.exception.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import com.example.library.repository.BookRepository;

/** Слой Service: бизнес-логика (поиск, проверка дубликатов, преобразование формы в сущность). */
@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    /** Список книг с необязательным поиском по названию/автору и фильтром по жанру. */
    public List<Book> search(String query, Genre genre) {
        String needle = query == null ? "" : query.trim().toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : repository.findAll()) {
            boolean matchesText = needle.isEmpty()
                    || book.getTitle().toLowerCase().contains(needle)
                    || book.getAuthor().toLowerCase().contains(needle);
            boolean matchesGenre = genre == null || book.getGenre() == genre;
            if (matchesText && matchesGenre) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book get(Long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<Book> findByGenre(Genre genre) {
        return search(null, genre);
    }

    /** Последние добавленные книги (для главной страницы). */
    public List<Book> latest(int count) {
        List<Book> books = new ArrayList<>(repository.findAll());
        books.sort(Comparator.comparing(Book::getId).reversed());
        return books.subList(0, Math.min(count, books.size()));
    }

    /** Сколько книг в каждом жанре (жанры без книг тоже показываем, с нулём). */
    public List<GenreStat> genreStats() {
        Map<Genre, Long> counts = new EnumMap<>(Genre.class);
        for (Genre genre : Genre.values()) {
            counts.put(genre, 0L);
        }
        for (Book book : repository.findAll()) {
            counts.merge(book.getGenre(), 1L, Long::sum);
        }
        List<GenreStat> stats = new ArrayList<>();
        for (Map.Entry<Genre, Long> entry : counts.entrySet()) {
            stats.add(new GenreStat(entry.getKey(), entry.getValue()));
        }
        return stats;
    }

    public Book create(BookForm form) {
        Book book = new Book();
        copy(form, book);
        return repository.save(book);
    }

    public Book update(Long id, BookForm form) {
        Book book = get(id);          // если книги нет, будет 404
        copy(form, book);
        return repository.save(book);
    }

    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new BookNotFoundException(id);
        }
    }

    /** Данные книги для формы редактирования. */
    public BookForm toForm(Long id) {
        Book book = get(id);
        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setTitle(book.getTitle());
        form.setAuthor(book.getAuthor());
        form.setYear(book.getYear());
        form.setPages(book.getPages());
        form.setGenre(book.getGenre());
        form.setDescription(book.getDescription());
        return form;
    }

    /**
     * Бизнес-правило: нельзя завести две книги с одним названием и автором.
     * excludeId нужен при редактировании, чтобы книга не считалась дубликатом самой себя.
     */
    public boolean isDuplicate(String title, String author, Long excludeId) {
        if (title == null || author == null) {
            return false;
        }
        for (Book book : repository.findAll()) {
            boolean sameBook = excludeId != null && excludeId.equals(book.getId());
            if (!sameBook
                    && book.getTitle().equalsIgnoreCase(title.trim())
                    && book.getAuthor().equalsIgnoreCase(author.trim())) {
                return true;
            }
        }
        return false;
    }

    private void copy(BookForm form, Book book) {
        book.setTitle(form.getTitle().trim());
        book.setAuthor(form.getAuthor().trim());
        book.setYear(form.getYear());
        book.setPages(form.getPages());
        book.setGenre(form.getGenre());
        String description = form.getDescription();
        book.setDescription(description == null ? "" : description.trim());
    }
}
