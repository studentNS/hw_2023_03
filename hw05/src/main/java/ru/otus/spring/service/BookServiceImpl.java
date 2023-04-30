package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Book getBookById(long bookId) {
        return bookDao.getBookById(bookId);
    }

    @Override
    public long insert(Book book) {
        return bookDao.insert(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAllBooks();
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public void deleteBookById(long bookId) {
        bookDao.deleteBookById(bookId);
    }
}
