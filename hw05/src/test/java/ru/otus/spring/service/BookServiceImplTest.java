package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

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
        Author author = new Author(1, "Александр Пушкин");
        Genre genre = new Genre(1, "Роман");
        long bookId = 1;
        Book expectedBook = new Book(bookId, "Евгений Онегин", author, genre);
        when(bookDao.getBookById(bookId)).thenReturn(expectedBook);
        Book actualBook = bookService.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    public void shouldInsertBook() {
        long authorId = 1;
        long genreId = 1;
        String bookName = "Евгений Онегин";
        long actualBookId = bookService.insert(bookName, authorId, genreId);
        assertThat(actualBookId).isNotNull();
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    public void shouldFindAllBooks() {
        Author author = new Author(1, "Александр Пушкин");
        Genre genre = new Genre(1, "Роман");
        long expectedBookId = 1;
        Book expectedBook = new Book(expectedBookId, "Евгений Онегин", author, genre);
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
        long authorId = 1;
        long genreId = 1;
        long expectedBookId = 1;
        String bookName = "Евгений Онегин";
        Book expectedBook = bookService.getBookById(expectedBookId);
        Author author = new Author(authorId, "Александр Пушкин");
        Genre genre = new Genre(genreId, "Роман");
        Book actualBook = new Book(expectedBookId, bookName, author, genre);
        bookService.updateBook(expectedBookId, bookName, authorId, genreId);
        assertThat(actualBook).usingRecursiveComparison().isNotEqualTo(expectedBook);
    }

    @DisplayName("удалять книгу в БД")
    @Test
    public void shouldDeleteBook() {
        bookService.deleteBookById(1);
        when(bookDao.getBookById(1)).thenReturn(null);
        assertThat(bookService.getBookById(1)).isNull();
    }
}
