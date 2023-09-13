package ru.otus.spring.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.converter.AuthorDtoConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorDtoConverter authorDtoConverter;

    @Override
    @CircuitBreaker(name = "AuthorFind", fallbackMethod = "getAuthorByIdFallback")
    public Optional<AuthorDto> getAuthorById(long authorId) {
        return authorRepository.findById(authorId).map(authorDtoConverter::toDto);
    }

    public Optional<AuthorDto> getAuthorByIdFallback(RuntimeException e) {
        return Optional.of(new AuthorDto(0, "Author n/a"));
    }

    @Override
    @CircuitBreaker(name = "AuthorFindAll", fallbackMethod = "getAllAuthorFallback")
    public List<AuthorDto> getAllAuthor() {
        return authorRepository.findAll().stream()
                .map(authorDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<AuthorDto> getAllAuthorFallback(RuntimeException e) {
        return List.of(new AuthorDto(0, "Author n/a"));
    }
}
