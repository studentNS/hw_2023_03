package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.Optional;

public interface GenreService {
    Optional<Genre> getGenreById(String genreId);
}
