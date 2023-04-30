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
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    @MockBean
    private BookDao bookDao;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookDao);
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
        Author author = new Author(1, "Александр Пушкин");
        Genre genre = new Genre(1, "Роман");
        long expectedBookId = 1;
        Book expectedBook = new Book(expectedBookId, "Евгений Онегин", author, genre);
        when(bookDao.insert(expectedBook)).thenReturn(expectedBookId);
        long actualBookId = bookService.insert(expectedBook);
        assertThat(expectedBookId).usingRecursiveComparison().isEqualTo(actualBookId);
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
        Book expectedBook = bookService.getBookById(1);
        Author author = new Author(1, "Александр Пушкин");
        Genre genre = new Genre(1, "Роман");
        Book actualBook = new Book(1, "Евгений Онегин", author, genre);
        bookService.updateBook(actualBook);
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
