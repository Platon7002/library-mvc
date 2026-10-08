package com.example.library.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Единое место обработки ошибок для всех контроллеров (@ControllerAdvice).
 * Вместо страшной страницы с трассой пользователь видит аккуратные страницы 404 и 500.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** Нет книги или жанра: 404. */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(ResourceNotFoundException ex, HttpServletRequest request, Model model) {
        fill(model, 404, ex.getMessage(), request);
        return "error/404";
    }

    /** Несуществующий адрес (например, /abc): тоже 404. */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoResource(NoResourceFoundException ex, HttpServletRequest request, Model model) {
        fill(model, 404, "Такой страницы не существует", request);
        return "error/404";
    }

    /** В адресе вместо числа текст (например, /books/abc): тоже 404. */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleBadArgument(MethodArgumentTypeMismatchException ex, HttpServletRequest request, Model model) {
        fill(model, 404, "Неверный адрес страницы", request);
        return "error/404";
    }

    /** Например, по ссылке открыли адрес, который принимает только POST: 405. */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleMethod(HttpRequestMethodNotSupportedException ex, HttpServletRequest request, Model model) {
        fill(model, 405, "Этот адрес не открывается напрямую в браузере", request);
        return "error/error";
    }

    /** Любая непредвиденная ошибка: 500. Подробности пишем в лог, пользователю их не показываем. */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAny(Exception ex, HttpServletRequest request, Model model) {
        log.error("Необработанная ошибка при запросе {}", request.getRequestURI(), ex);
        fill(model, 500, "Внутренняя ошибка сервера. Мы уже знаем о проблеме.", request);
        return "error/500";
    }

    private void fill(Model model, int status, String message, HttpServletRequest request) {
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        model.addAttribute("path", request.getRequestURI());
    }
}
