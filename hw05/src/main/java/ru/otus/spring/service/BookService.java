package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {

    Book getBookById(long bookId);

    long insert(Book book);

    List<Book> findAllBooks();

    void updateBook(Book book);

    void deleteBookById(long bookId);
}

