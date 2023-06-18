package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.Optional;

public interface AuthorService {
    Optional<Author> getAuthorById(String authorId);
}
