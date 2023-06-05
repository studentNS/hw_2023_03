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
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с книгами")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookServiceImpl.class)
public class BookServiceImplTest {

    private static final String FIRST_AUTHOR_ID = "1";

    private static final String FIRST_GENRE_ID = "1";

    private static final String FIRST_BOOK_ID = "1";

    private static final String FIRST_COMMENT_ID = "1";

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    private BookService bookService;

    private Author author;

    private Genre genre;

    private Book book;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(bookRepository, authorService, genreService, commentService);
        author = new Author(FIRST_AUTHOR_ID, "Александр Пушкин");
        genre = new Genre(FIRST_GENRE_ID, "Роман");
        book = new Book(FIRST_BOOK_ID, "Евгений Онегин", author,
                genre, null);
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
        String bookName = "Евгений Онегин";
        when(authorService.getAuthorById(FIRST_AUTHOR_ID)).thenReturn(Optional.of(author));
        when(genreService.getGenreById(FIRST_GENRE_ID)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book actualBook = bookService.insertBook(bookName, FIRST_AUTHOR_ID, FIRST_GENRE_ID);
        assertThat(actualBook).isNotNull();
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    public void shouldFindAllBooks() {
        List<Book> expectedBookList = new ArrayList<>();
        expectedBookList.add(book);
        when(bookRepository.findAll()).thenReturn(expectedBookList);
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
        when(bookRepository.save(any(Book.class))).thenReturn(actualBook);
        bookService.updateBook(FIRST_BOOK_ID, bookName, FIRST_AUTHOR_ID, FIRST_GENRE_ID);
        assertThat(actualBook).usingRecursiveComparison().isNotEqualTo(expectedBook);
    }

    @DisplayName("удалять книгу в БД")
    @Test
    public void shouldDeleteBook() {
        Comment comment = new Comment(FIRST_COMMENT_ID, "Увлекательная книга");
        book.setComments(Arrays.asList(comment));
        when(bookRepository.findById(FIRST_BOOK_ID)).thenReturn(Optional.of(book))
                .thenReturn(null);
        bookService.deleteBookById(FIRST_BOOK_ID);
        assertThat(bookService.getBookById(FIRST_BOOK_ID)).isNull();
    }

    @DisplayName("добавлять комментарий к книге в БД")
    @Test
    void shouldAddComment() {
        Comment comment = new Comment(FIRST_COMMENT_ID, "Увлекательная книга");
        book.setComments(Arrays.asList(comment));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book actualBookComment = bookRepository.save(book);
        assertThat(actualBookComment.getComments()).isNotNull();
    }
}
