package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PagesController {

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String allBooks(Model model) {
        List<AuthorDto> allAuthors = authorService.getAllAuthor();
        model.addAttribute("authors", allAuthors);
        List<GenreDto> allGenres = genreService.getAllGenre();
        model.addAttribute("genres", allGenres);
        return "listBooks";
    }

    @GetMapping("/comment/{bookId}")
    public String commentBook(@PathVariable("bookId") long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "commentBook";
    }
}
