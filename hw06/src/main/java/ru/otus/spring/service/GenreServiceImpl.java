package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreRepository;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreDao;

    public GenreServiceImpl(GenreRepository genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Optional<Genre> getGenreById(long genreId) {
        return genreDao.getGenreById(genreId);
    }
}
