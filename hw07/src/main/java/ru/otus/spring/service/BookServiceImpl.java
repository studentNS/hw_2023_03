package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService,
                           GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Optional<Book> getBookById(long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public Book insert(String bookName, long authorId, long genreId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        Optional<Genre> genre = genreService.getGenreById(genreId);
        Book book = new Book(0, bookName, author.get(),
                genre.get(), null);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public void updateBook(long bookId, String bookName,
                           long authorId, long genreId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        Optional<Genre> genre = genreService.getGenreById(genreId);
        Book book = new Book(bookId, bookName, author.get(),
                genre.get(), null);
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(long bookId) {
        bookRepository.deleteById(bookId);
    }
}
