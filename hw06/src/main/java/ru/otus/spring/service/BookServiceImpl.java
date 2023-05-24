package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookDao;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookServiceImpl(BookRepository bookDao, AuthorService authorService,
                           GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Optional<Book> getBookById(long bookId) {
        return bookDao.getBookById(bookId);
    }

    @Override
    public Book insert(String bookName, long authorId, long genreId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        Optional<Genre> genre = genreService.getGenreById(genreId);
        Book book = new Book(0, bookName, author.get(),
                genre.get(), null);
        return bookDao.insert(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAllBooks();
    }

    @Transactional
    @Override
    public void updateBook(long bookId, String bookName,
                           long authorId, long genreId) {
        Optional<Author> author = authorService.getAuthorById(authorId);
        Optional<Genre> genre = genreService.getGenreById(genreId);
        Book book = new Book(bookId, bookName, author.get(),
                genre.get(), null);
        bookDao.updateBook(book);
    }

    @Transactional
    @Override
    public void deleteBookById(long bookId) {
        bookDao.deleteBookById(bookId);
    }
}
