package ru.otus.spring.service;

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
    public Optional<AuthorDto> getAuthorById(long authorId) {
        return authorRepository.findById(authorId).map(authorDtoConverter::toDto);
    }

    @Override
    public List<AuthorDto> getAllAuthor() {
        return authorRepository.findAll().stream()
                .map(authorDtoConverter::toDto)
                .collect(Collectors.toList());
    }
}
