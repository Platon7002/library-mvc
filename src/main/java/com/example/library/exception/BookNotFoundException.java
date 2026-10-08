package com.example.library.exception;

public class BookNotFoundException extends ResourceNotFoundException {

    public BookNotFoundException(Long id) {
        super("Книга с номером " + id + " не найдена");
    }
}
