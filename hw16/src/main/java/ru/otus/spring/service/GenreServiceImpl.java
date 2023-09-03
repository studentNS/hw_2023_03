package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.dto.converter.GenreDtoConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreDtoConverter genreDtoConverter;

    @Override
    public Optional<GenreDto> getGenreById(long genreId) {
        return genreRepository.findById(genreId).map(genreDtoConverter::toDto);
    }

    @Override
    public List<GenreDto> getAllGenre() {
        return genreRepository.findAll().stream()
                .map(genreDtoConverter::toDto)
                .collect(Collectors.toList());
    }
}
