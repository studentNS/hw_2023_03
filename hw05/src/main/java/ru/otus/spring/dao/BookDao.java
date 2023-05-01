package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {

    Book getBookById(long bookId);

    long insert(Book book);

    List<Book> findAllBooks();

    void updateBook(Book book);

    void deleteBookById(long bookId);
}
