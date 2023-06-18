package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreRepository;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<Genre> getGenreById(String genreId) {
        return genreRepository.findById(genreId);
    }
}
