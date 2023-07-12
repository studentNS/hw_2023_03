package ru.otus.spring.service;

import ru.otus.spring.dto.AuthorDto;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Optional<AuthorDto> getAuthorById(long authorId);

    List<AuthorDto> getAllAuthor();
}
