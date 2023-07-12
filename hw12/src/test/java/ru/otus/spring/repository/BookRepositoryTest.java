package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final long FIRST_GENRE_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    private static final long SECOND_BOOK_ID = 2L;

    private static final int EXPECTED_BOOK_COUNT = 1;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetBookById() {
        Optional<Book> optionalActualBook = bookRepository.findById(FIRST_BOOK_ID);
        Book expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(optionalActualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Optional<Author> author = authorRepository.findById(FIRST_AUTHOR_ID);
        Optional<Genre> genre = genreRepository.findById(FIRST_GENRE_ID);
        Book book = new Book(0, "Дубровский", author.get(),
                genre.get(), null);
        Book actualBook = bookRepository.save(book);
        Book expectedBook = em.find(Book.class, SECOND_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldFindAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        String oldNameBook = book.getName();
        book.setName("Дубровский");
        Book updateBook = bookRepository.save(book);
        assertThat(updateBook.getName()).isNotEqualTo(oldNameBook);
    }

    @DisplayName("удалять книгу в БД")
    @Test
    void shouldDeleteBook() {
        bookRepository.deleteById(FIRST_BOOK_ID);
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(book).isNull();
    }
}
