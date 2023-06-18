package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами должен")
@DataMongoTest
public class BookRepositoryTest {

    private static final String FIRST_AUTHOR_ID = "1";

    private static final String FIRST_GENRE_ID = "1";

    private static final String FIRST_BOOK_ID = "1";

    private static final int EXPECTED_BOOK_COUNT = 1;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private CommentRepository commentRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetBookById() {
        Author author = authorRepository.findById("1").get();
        Genre genre = genreRepository.findById("1").get();
        Comment commentFirst = commentRepository.findById("1").get();
        Comment commentSecond = commentRepository.findById("2").get();
        Book actualBook = new Book("1", "Евгений Онегин", author, genre,
                Arrays.asList(commentFirst, commentSecond));

        Optional<Book> expectedBook = bookRepository.findById(FIRST_BOOK_ID);
        assertThat(expectedBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(actualBook);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Optional<Author> author = authorRepository.findById(FIRST_AUTHOR_ID);
        Optional<Genre> genre = genreRepository.findById(FIRST_GENRE_ID);
        Book book = new Book(null, "Дубровский", author.get(),
                genre.get(), null);
        Book actualBook = bookRepository.save(book);
        assertThat(actualBook).isNotNull();
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldFindAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(EXPECTED_BOOK_COUNT);
    }

    @DisplayName("обновлять книгу в БД")
    @Test
    void shouldUpdateBook() {
        Book book = bookRepository.findById(FIRST_BOOK_ID).get();
        String oldNameBook = book.getName();
        book.setName("Дубровский");
        Book updateBook = bookRepository.save(book);
        assertThat(updateBook.getName()).isNotEqualTo(oldNameBook);
    }

    @DisplayName("удалять книгу в БД")
    @Test
    void shouldDeleteBook() {
        bookRepository.deleteById(FIRST_BOOK_ID);
        Optional<Book> book = bookRepository.findById(FIRST_BOOK_ID);
        assertThat(book).isEmpty();
    }

    @DisplayName("добавлять комментарий к книге в БД")
    @Test
    void shouldAddComment() {
        Optional<Author> author = authorRepository.findById(FIRST_AUTHOR_ID);
        Optional<Genre> genre = genreRepository.findById(FIRST_GENRE_ID);
        Book book = new Book(null, "Дубровский", author.get(),
                genre.get(), null);
        Book actualBook = bookRepository.save(book);
        Comment comment = commentRepository.findById("1").get();
        actualBook.setComments(Arrays.asList(comment));
        Book actualBookComment = bookRepository.save(actualBook);
        assertThat(actualBookComment.getComments()).isNotNull();
    }
}
