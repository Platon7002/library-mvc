package com.example.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.library.dto.BookForm;
import com.example.library.model.Book;
import com.example.library.model.Genre;
import com.example.library.service.BookService;

import jakarta.validation.Valid;

/** Контроллер №2: CRUD книг (список, просмотр, создание, редактирование, удаление). */
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /** Список жанров доступен во всех страницах этого контроллера (для выпадающих списков). */
    @ModelAttribute("genres")
    public Genre[] genres() {
        return Genre.values();
    }

    // ---------- чтение ----------

    @GetMapping
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) Genre genre,
                       Model model) {
        model.addAttribute("books", bookService.search(q, genre));
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("selectedGenre", genre);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.get(id));
        return "books/detail";
    }

    // ---------- создание ----------

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        model.addAttribute("pageTitle", "Новая книга");
        return "books/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("bookForm") BookForm form,
                         BindingResult result,
                         RedirectAttributes redirect,
                         Model model) {
        checkDuplicate(form, null, result);
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Новая книга");
            return "books/form";                      // остаёмся на форме и показываем ошибки
        }
        Book book = bookService.create(form);
        redirect.addFlashAttribute("message", "Книга «" + book.getTitle() + "» добавлена");
        return "redirect:/books/" + book.getId();     // паттерн Post/Redirect/Get
    }

    // ---------- редактирование ----------

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("bookForm", bookService.toForm(id));
        model.addAttribute("pageTitle", "Редактирование книги");
        return "books/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("bookForm") BookForm form,
                         BindingResult result,
                         RedirectAttributes redirect,
                         Model model) {
        bookService.get(id);                          // если книги нет, получим 404
        form.setId(id);
        checkDuplicate(form, id, result);
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Редактирование книги");
            return "books/form";
        }
        Book book = bookService.update(id, form);
        redirect.addFlashAttribute("message", "Изменения в книге «" + book.getTitle() + "» сохранены");
        return "redirect:/books/" + id;
    }

    // ---------- удаление ----------

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        Book book = bookService.get(id);
        bookService.delete(id);
        redirect.addFlashAttribute("message", "Книга «" + book.getTitle() + "» удалена");
        return "redirect:/books";
    }

    /** Бизнес-проверка поверх аннотаций: ошибка привязывается к полю «title». */
    private void checkDuplicate(BookForm form, Long excludeId, BindingResult result) {
        if (!result.hasFieldErrors("title") && !result.hasFieldErrors("author")
                && bookService.isDuplicate(form.getTitle(), form.getAuthor(), excludeId)) {
            result.rejectValue("title", "duplicate", "Книга этого автора с таким названием уже есть");
        }
    }
}
