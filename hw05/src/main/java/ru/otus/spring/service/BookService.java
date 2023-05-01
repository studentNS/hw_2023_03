package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {

    Book getBookById(long bookId);

    long insert(String bookName, long authorId,
                long genreId);

    List<Book> findAllBooks();

    void updateBook(long bookId, String bookName,
                    long authorId, long genreId);

    void deleteBookById(long bookId);
}

