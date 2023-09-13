package ru.otus.spring.service;

import ru.otus.spring.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAllBooks();
}

