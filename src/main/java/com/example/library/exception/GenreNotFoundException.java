package com.example.library.exception;

public class GenreNotFoundException extends ResourceNotFoundException {

    public GenreNotFoundException(String code) {
        super("Жанр «" + code + "» не существует");
    }
}
