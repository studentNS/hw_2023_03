package ru.otus.spring.service;

import ru.otus.spring.dto.GenreDto;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    Optional<GenreDto> getGenreById(long genreId);

    List<GenreDto> getAllGenre();
}
