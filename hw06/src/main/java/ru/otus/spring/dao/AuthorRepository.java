package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> getAuthorById(long authorId);

    Author insert(Author author);

    List<Author> findAllAuthors();
}
