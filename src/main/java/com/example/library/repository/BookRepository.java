package com.example.library.repository;

import java.util.List;
import java.util.Optional;

import com.example.library.model.Book;

/** Слой Repository: только хранение. Реализацию можно заменить на базу данных, не трогая сервис. */
public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    /** Если у книги нет id, он назначается автоматически. */
    Book save(Book book);

    /** @return true, если книга существовала и была удалена */
    boolean deleteById(Long id);
}
