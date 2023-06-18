package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService,
                           GenreService genreService, CommentService commentService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @Override
    public Optional<Book> getBookById(String bookId) {
        return bookRepository.findById(bookId);
    }

    @Transactional
    @Override
    public Book insertBook(String bookName, String authorId, String genreId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        Optional<Genre> genre = genreService.getGenreById(genreId);
        Book book = new Book(null, bookName, author.get(),
                genre.get(), null);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    @Override
    public void updateBook(String bookId, String bookName,
                           String authorId, String genreId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        Optional<Genre> genre = genreService.getGenreById(genreId);
        Book book = new Book(bookId, bookName, author.get(),
                genre.get(), null);
        bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBookById(String bookId) {
        List<Comment> comments = getCommentByBookId(bookId);
        comments.forEach((i) -> commentService.deleteComment(i));
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Comment> getCommentByBookId(String bookId) {
        Book book = bookRepository.findById(bookId).get();
        return List.copyOf(book.getComments());
    }

    @Transactional
    @Override
    public Comment addComment(String bookId, String commentText) {
        Book book = bookRepository.findById(bookId).get();
        Comment comment = commentService.insertComment(commentText);
        book.setComments(Arrays.asList(comment));
        bookRepository.save(book);
        return comment;
    }
}
