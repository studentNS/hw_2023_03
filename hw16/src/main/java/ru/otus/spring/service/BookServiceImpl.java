package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.converter.BookDtoConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookDtoConverter bookDtoConverter;

    @Override
    public Optional<BookDto> getBookDtoById(long bookId) {
        return bookRepository.findById(bookId).map(bookDtoConverter::toDto);
    }

    @Override
    public Optional<Book> getBookById(long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void insertBook(BookDto bookDto) {
        Book book = bookDtoConverter.fromDto(bookDto);
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
