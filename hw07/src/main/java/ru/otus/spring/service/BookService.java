package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getBookById(long bookId);

    Book insert(String bookName, long authorId,
                long genreId);

    List<Book> findAllBooks();

    void updateBook(long bookId, String bookName,
                    long authorId, long genreId);

    void deleteBookById(long bookId);
}

