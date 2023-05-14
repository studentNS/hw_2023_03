package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

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

    @MockBean
    private BookRepository bookDao;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookDao, authorService, genreService);
    }

    @DisplayName("получать книгу по ID")
    @Test
    public void shouldGetBookByIdTest() {
        Author author = new Author(FIRST_AUTHOR_ID, "Александр Пушкин");
        Genre genre = new Genre(FIRST_GENRE_ID, "Роман");
        Book expectedBook = new Book(FIRST_BOOK_ID, "Евгений Онегин", author,
                genre, null);
        when(bookDao.getBookById(FIRST_BOOK_ID)).thenReturn(Optional.of(expectedBook));
        Optional<Book> actualBook = bookService.getBookById(expectedBook.getId());
        assertThat(actualBook.get()).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    public void shouldInsertBook() {
        String bookName = "Евгений Онегин";
        Author author = new Author(FIRST_AUTHOR_ID, "Александр Пушкин");
        Genre genre = new Genre(FIRST_GENRE_ID, "Роман");
        Book expectedBook = new Book(FIRST_BOOK_ID, "Евгений Онегин", author,
                genre, null);
        when(authorService.getAuthorById(FIRST_AUTHOR_ID)).thenReturn(Optional.of(author));
        when(genreService.getGenreById(FIRST_GENRE_ID)).thenReturn(Optional.of(genre));
        when(bookDao.insert(any(Book.class))).thenReturn(expectedBook);
        Book actualBook = bookService.insert(bookName, FIRST_AUTHOR_ID, FIRST_GENRE_ID);
        assertThat(actualBook).isNotNull();
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    public void shouldFindAllBooks() {
        Author author = new Author(FIRST_AUTHOR_ID, "Александр Пушкин");
        Genre genre = new Genre(FIRST_GENRE_ID, "Роман");
        Book expectedBook = new Book(FIRST_BOOK_ID, "Евгений Онегин", author,
                genre, null);
        List<Book> expectedBookList = new ArrayList<>();
        expectedBookList.add(expectedBook);
        when(bookDao.findAllBooks()).thenReturn(expectedBookList);
        List<Book> actualBookList = bookService.findAllBooks();
        assertThat(actualBookList.size())
                .isEqualTo(expectedBookList.size());
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    public void shouldUpdateBook() {
        String bookName = "Евгений Онегин";
        Optional<Book> expectedBook = bookService.getBookById(FIRST_BOOK_ID);
        Author author = new Author(FIRST_AUTHOR_ID, "Александр Пушкин");
        Genre genre = new Genre(FIRST_GENRE_ID, "Роман");
        Book actualBook = new Book(FIRST_BOOK_ID, bookName, author, genre, null);
        when(authorService.getAuthorById(FIRST_AUTHOR_ID)).thenReturn(Optional.of(author));
        when(genreService.getGenreById(FIRST_GENRE_ID)).thenReturn(Optional.of(genre));
        when(bookDao.updateBook(any(Book.class))).thenReturn(actualBook);
        bookService.updateBook(FIRST_BOOK_ID, bookName, FIRST_AUTHOR_ID, FIRST_GENRE_ID);
        assertThat(actualBook).usingRecursiveComparison().isNotEqualTo(expectedBook);
    }

    @DisplayName("удалять книгу в БД")
    @Test
    public void shouldDeleteBook() {
        bookService.deleteBookById(FIRST_BOOK_ID);
        when(bookDao.getBookById(FIRST_BOOK_ID)).thenReturn(null);
        assertThat(bookService.getBookById(FIRST_BOOK_ID)).isNull();
    }
}
