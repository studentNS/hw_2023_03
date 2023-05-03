package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Dao для работы с книгами")
@JdbcTest
@Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
public class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Autowired
    private GenreDaoJdbc styleDaoJdbc;

    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetBookByIdTest() {
        Author author = authorDaoJdbc.getAuthorById(1);
        Genre style = styleDaoJdbc.getGenreById(1);
        Book expectedBook = new Book(1, "Евгений Онегин", author, style);
        Book actualBook = bookDaoJdbc.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Author author = authorDaoJdbc.getAuthorById(1);
        Genre style = styleDaoJdbc.getGenreById(1);
        Book expectedBook = new Book(0, "Дубровский", author, style);
        long expectedBookId = bookDaoJdbc.insert(expectedBook);
        Book actualBook = bookDaoJdbc.getBookById(expectedBookId);
        assertThat(actualBook.getName()).isEqualTo(expectedBook.getName());
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldFindAllBooks() {
        Author author = authorDaoJdbc.getAuthorById(1);
        Genre style = styleDaoJdbc.getGenreById(1);
        Book expectedBook = new Book(1, "Евгений Онегин", author, style);
        List<Book> actualBookList = bookDaoJdbc.findAllBooks();
        assertThat(actualBookList)
                .containsExactlyInAnyOrder(expectedBook);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = bookDaoJdbc.getBookById(1);
        Author author = authorDaoJdbc.getAuthorById(1);
        Genre style = styleDaoJdbc.getGenreById(1);
        Book actualBook = new Book(1, "Дубровский", author, style);
        bookDaoJdbc.updateBook(actualBook);
        assertThat(actualBook).usingRecursiveComparison().isNotEqualTo(expectedBook);
    }

    @DisplayName("удалять книгу в БД")
    @Test
    void shouldDeleteBook() {
        bookDaoJdbc.deleteBookById(1);
        assertThrows(EmptyResultDataAccessException.class,
                () -> bookDaoJdbc.getBookById(1));
    }
}
