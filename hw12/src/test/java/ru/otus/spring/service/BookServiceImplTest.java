package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.dto.converter.BookDtoConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final long FIRST_GENRE_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    private Author author;

    private Genre genre;

    private Book book;

    private AuthorDto authorDto;

    private GenreDto genreDto;

    private BookDto bookDto;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookDtoConverter bookDtoConverter;

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setUp() {
        author = new Author(FIRST_AUTHOR_ID, "Александр Пушкин");
        genre = new Genre(FIRST_GENRE_ID, "Роман");
        book = new Book(FIRST_BOOK_ID, "Евгений Онегин", author,
                genre, null);
        authorDto = new AuthorDto(FIRST_AUTHOR_ID, "Александр Пушкин");
        genreDto = new GenreDto(FIRST_GENRE_ID, "Роман");
        bookDto = new BookDto(FIRST_BOOK_ID, "Евгений Онегин",
                authorDto, genreDto);
    }

    @DisplayName("получать книгу по ID")
    @Test
    public void shouldGetBookByIdTest() {
        when(bookRepository.findById(FIRST_BOOK_ID)).thenReturn(Optional.of(book));
        Optional<Book> actualBook = bookService.getBookById(book.getId());
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    public void shouldInsertBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        bookService.insertBook(bookDto);
        when(bookRepository.findById(FIRST_BOOK_ID)).thenReturn(Optional.of(book));
        Optional<Book> actualBook = bookService.getBookById(book.getId());
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    public void shouldFindAllBooks() {
        List<Book> expectedBookList = new ArrayList<>();
        expectedBookList.add(book);
        when(bookRepository.findAll()).thenReturn(expectedBookList);
        List<BookDto> actualBookList = bookService.findAllBooks();
        assertThat(actualBookList.size())
                .isEqualTo(expectedBookList.size());
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    public void shouldUpdateBook() {
        String bookName = "Евгений Онегин";
        book.setName(bookName);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        bookService.update(book);
        when(bookRepository.findById(FIRST_BOOK_ID)).thenReturn(Optional.of(book));
        Optional<Book> actualBook = bookService.getBookById(book.getId());
        assertThat(actualBook).usingRecursiveComparison().isNotEqualTo(book);
    }

    @DisplayName("удалять книгу в БД")
    @Test
    public void shouldDeleteBook() {
        bookService.deleteBookById(FIRST_BOOK_ID);
        when(bookRepository.findById(FIRST_BOOK_ID)).thenReturn(null);
        assertThat(bookService.getBookById(FIRST_BOOK_ID)).isNull();
    }
}
