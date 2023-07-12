package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<BookDto> getBookDtoById(long bookId);

    Optional<Book> getBookById(long bookId);

    void insertBook(BookDto bookDto);

    List<BookDto> findAllBooks();

    void update(Book book);

    void deleteBookById(long bookId);
}

