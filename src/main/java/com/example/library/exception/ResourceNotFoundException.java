package com.example.library.exception;

/** Базовое исключение «не найдено». Обрабатывается в GlobalExceptionHandler и даёт страницу 404. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
