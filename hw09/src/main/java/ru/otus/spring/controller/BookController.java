package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    @GetMapping("/")
    public String allBooks(Model model) {
        List<BookDto> bookList = bookService.findAllBooks();
        model.addAttribute("books", bookList);
        return "listBooks";
    }

    @GetMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        BookDto book = bookService.getBookDtoById(id).get();
        model.addAttribute("book", book);

        List<AuthorDto> allAuthors = authorService.getAllAuthor();
        model.addAttribute("authors", allAuthors);

        List<GenreDto> allGenres = genreService.getAllGenre();
        model.addAttribute("genres", allGenres);
        return "editBook";
    }

    @PostMapping("/edit")
    public String saveBook(@ModelAttribute("book") BookDto bookDto) {
        commentService.updateBook(bookDto);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String insertBook(Model model) {
        BookDto book = new BookDto();
        model.addAttribute("book", book);

        List<AuthorDto> allAuthors = authorService.getAllAuthor();
        model.addAttribute("authors", allAuthors);

        List<GenreDto> allGenres = genreService.getAllGenre();
        model.addAttribute("genres", allGenres);

        return "createBook";
    }

    @PostMapping("/create")
    public String addBook(BookDto bookDto) {
        bookService.insertBook(bookDto);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long bookId) {
        bookService.deleteBookById(bookId);
        return "redirect:/";
    }
}
