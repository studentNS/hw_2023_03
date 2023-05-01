package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorService authorService;

    private final GenreService genreService;

    public BookServiceImpl(BookDao bookDao, AuthorService authorService,
                           GenreService genreService) {
        this.bookDao = bookDao;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public Book getBookById(long bookId) {
        return bookDao.getBookById(bookId);
    }

    @Override
    public long insert(String bookName, long authorId, long genreId) {
        Author author = authorService.getAuthorById(authorId);
        Genre genre = genreService.getGenreById(genreId);
        Book book = new Book(0, bookName, author, genre);
        return bookDao.insert(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAllBooks();
    }

    @Override
    public void updateBook(long bookId, String bookName,
                           long authorId, long genreId) {
        Author author = authorService.getAuthorById(authorId);
        Genre genre = genreService.getGenreById(genreId);
        Book book = new Book(bookId, bookName, author, genre);
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBookById(long bookId) {
        bookDao.deleteBookById(bookId);
    }
}
