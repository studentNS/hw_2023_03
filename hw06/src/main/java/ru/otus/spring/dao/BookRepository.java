package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> getBookById(long bookId);

    Book insert(Book book);

    List<Book> findAllBooks();

    Book updateBook(Book book);

    void deleteBookById(long bookId);
}
