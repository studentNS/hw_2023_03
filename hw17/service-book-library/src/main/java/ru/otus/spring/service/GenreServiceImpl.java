package ru.otus.spring.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
    @CircuitBreaker(name = "GenreFind", fallbackMethod = "getGenreByIdFallback")
    public Optional<GenreDto> getGenreById(long genreId) {
        return genreRepository.findById(genreId).map(genreDtoConverter::toDto);
    }

    public Optional<GenreDto> getGenreByIdFallback(RuntimeException e) {
        return Optional.of(new GenreDto(0, "Genre n/a"));
    }

    @Override
    @CircuitBreaker(name = "GenreFindAll", fallbackMethod = "getAllGenreFallback")
    public List<GenreDto> getAllGenre() {
        return genreRepository.findAll().stream()
                .map(genreDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<GenreDto> getAllGenreFallback(RuntimeException e) {
        return List.of(new GenreDto(0, "Genre n/a"));
    }
}
