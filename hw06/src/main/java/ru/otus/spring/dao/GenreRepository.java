package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Optional<Genre> getGenreById(long genreId);

    Genre insert(Genre genre);

    List<Genre> findAllGenres();
}
