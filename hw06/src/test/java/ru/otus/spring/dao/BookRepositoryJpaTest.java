package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами")
@DataJpaTest
@Import({BookRepositoryJpa.class, AuthorRepositoryJpa.class, GenreRepositoryJpa.class})
public class BookRepositoryJpaTest {

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final long FIRST_GENRE_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    private static final long SECOND_BOOK_ID = 2L;

    private static final int EXPECTED_BOOK_COUNT = 1;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetBookById() {
        Optional<Book> optionalActualBook = bookRepositoryJpa.getBookById(FIRST_BOOK_ID);
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Optional<Author> author = authorRepositoryJpa.getAuthorById(FIRST_AUTHOR_ID);
        Optional<Genre> genre = genreRepositoryJpa.getGenreById(FIRST_GENRE_ID);
        Book book = new Book(0, "Дубровский", author.get(),
                genre.get(), null);
        Book actualBook = bookRepositoryJpa.insert(book);
        Book expectedBook = em.find(Book.class, SECOND_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldFindAllBooks() {
        List<Book> bookList = bookRepositoryJpa.findAllBooks();
        assertThat(bookList).hasSize(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        String oldNameBook = book.getName();
        book.setName("Дубровский");
        Book updateBook = bookRepositoryJpa.updateBook(book);
        assertThat(updateBook.getName()).isNotEqualTo(oldNameBook);
    }

    @DisplayName("удалять книгу в БД")
    @Test
    void shouldDeleteBook() {
        bookRepositoryJpa.deleteBookById(FIRST_BOOK_ID);
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isNull();
    }
}
