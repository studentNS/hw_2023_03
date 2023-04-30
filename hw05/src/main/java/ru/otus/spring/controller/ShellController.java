package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @ShellMethod(value = "Show all books", key = {"l", "list"})
    public String getAllBooks() {
        List<Book> bookList = bookService.findAllBooks();
        return String.format("List of books: %s", bookList.toString());
    }

    @ShellMethod(value = "Get book by id", key = {"g", "get"})
    public String getBookById(@ShellOption() long bookId) {
        Book book = bookService.getBookById(bookId);
        return String.format("Book: %s", book);
    }

    @ShellMethod(value = "Delete book", key = {"d", "delete"})
    public String deleteBookById(@ShellOption() long bookId) {
        Book book = bookService.getBookById(bookId);
        bookService.deleteBookById(bookId);
        return String.format("Delete book: %s", book);
    }

    @ShellMethod(value = "Create book", key = {"c", "create"})
    public String createBook(@ShellOption() String bookName,
                             @ShellOption() long authorId,
                             @ShellOption() long genreId) {
        Author author = authorService.getAuthorById(authorId);
        Genre genre = genreService.getGenreById(genreId);
        Book book = new Book(0, bookName, author, genre);
        long bookId = bookService.insert(book);
        return String.format("Book was added id = %s", bookId);
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    public String updateBook(@ShellOption() long bookId,
                             @ShellOption() String bookName,
                             @ShellOption() long authorId,
                             @ShellOption() long genreId) {
        Author author = authorService.getAuthorById(authorId);
        Genre genre = genreService.getGenreById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookService.updateBook(book);
        return "Book was update";
    }
}
