package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    private final CommentService commentService;

    @GetMapping("/books")
    public List<BookDto> allBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/book/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        return bookService.getBookDtoById(id).get();
    }

    @PutMapping("/book/edit/{id}")
    public void saveBook(@PathVariable("id") long id, @RequestBody BookDto bookDto) {
        commentService.updateBook(bookDto);
    }

    @PostMapping("/book/create")
    public void insertBook(@RequestBody BookDto bookDto) {
        bookService.insertBook(bookDto);
    }

    @DeleteMapping("/book/delete/{id}")
    public void deleteBook(@PathVariable("id") long bookId) {
        bookService.deleteBookById(bookId);
    }
}
