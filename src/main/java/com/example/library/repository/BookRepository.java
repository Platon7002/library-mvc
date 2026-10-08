package com.example.library.repository;

import java.util.List;
import java.util.Optional;

import com.example.library.model.Book;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book save(Book book);

    boolean deleteById(Long id);
}
