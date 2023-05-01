package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {

    Genre getGenreById(long genreId);

    long insert(Genre genre);

    List<Genre> findAllGenres();
}
