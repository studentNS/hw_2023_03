package ru.otus.spring.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.GenreDto;
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
    @CircuitBreaker(name = "BookFind", fallbackMethod = "getBookDtoByIdFallback")
    public Optional<BookDto> getBookDtoById(long bookId) {
        return bookRepository.findById(bookId).map(bookDtoConverter::toDto);
    }

    public Optional<BookDto> getBookDtoByIdFallback(RuntimeException e) {
        return Optional.of(new BookDto(0, "Book n/a", new AuthorDto(0, "Author n/a"),
                new GenreDto(0, "Genre n/a")));
    }

    @Override
    @CircuitBreaker(name = "BookFind", fallbackMethod = "getBookByIdFallback")
    public Optional<Book> getBookById(long bookId) {
        return bookRepository.findById(bookId);
    }

    public Optional<Book> getBookByIdFallback(RuntimeException e) {
        return Optional.of(new Book(0, "Book n/a", new Author(0, "Author n/a"),
                new Genre(0, "Genre n/a"), List.of()));
    }

    @Override
    public void insertBook(BookDto bookDto) {
        Book book = bookDtoConverter.fromDto(bookDto);
        bookRepository.save(book);
    }

    @Override
    @CircuitBreaker(name = "BookFindAll", fallbackMethod = "findAllBookFallback")
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> findAllBookFallback(RuntimeException e) {
        return List.of(new BookDto(0, "Book n/a", new AuthorDto(0, "Author n/a"),
                new GenreDto(0, "Genre n/a")));
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
