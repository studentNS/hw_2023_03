package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Optional<Book> getBookById(String bookId);

    Book insertBook(String bookName, String authorId,
                    String genreId);

    List<Book> findAllBooks();

    void updateBook(String bookId, String bookName,
                    String authorId, String genreId);

    void deleteBookById(String bookId);

    List<Comment> getCommentByBookId(String bookId);

    Comment addComment(String bookId, String commentText);
}

