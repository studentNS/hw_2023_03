package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author getAuthorById(long authorId);

    long insert(Author author);

    List<Author> findAllAuthors();
}
