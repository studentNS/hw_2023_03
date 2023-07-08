package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.dto.converter.AuthorDtoConverter;
import ru.otus.spring.dto.converter.GenreDtoConverter;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.GenreRepository;

@Controller
@RequiredArgsConstructor
public class PagesController {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final AuthorDtoConverter authorDtoConverter;

    private final GenreDtoConverter genreDtoConverter;

    @GetMapping("/")
    public String allBooks(Model model) {
        Flux<AuthorDto> allAuthors = authorRepository.findAll().map(authorDtoConverter::toDto);
        model.addAttribute("authors", allAuthors);
        Flux<GenreDto> allGenres = genreRepository.findAll().map(genreDtoConverter::toDto);
        model.addAttribute("genres", allGenres);
        return "listBooks";
    }

    @GetMapping("/comment/{bookId}")
    public String commentBook(@PathVariable("bookId") long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "commentBook";
    }
}
