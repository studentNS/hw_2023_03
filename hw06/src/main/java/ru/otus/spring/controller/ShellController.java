package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {

    private final BookService bookService;

    private final CommentService commentService;

    @ShellMethod(value = "Show all books", key = {"l", "list"})
    public String getAllBooks() {
        List<Book> bookList = bookService.findAllBooks();
        return String.format("List of books: %s", bookList);
    }

    @ShellMethod(value = "Get book by id", key = {"g", "get"})
    public String getBookById(@ShellOption() long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        return String.format("Book: %s", book);
    }

    @ShellMethod(value = "Delete book", key = {"d", "delete"})
    public String deleteBookById(@ShellOption() long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        bookService.deleteBookById(bookId);
        return String.format("Delete book: %s", book);
    }

    @ShellMethod(value = "Create book", key = {"c", "create"})
    public String createBook(@ShellOption() String bookName,
                             @ShellOption() long authorId,
                             @ShellOption() long genreId) {
        Book bookId = bookService.insert(bookName, authorId, genreId);
        return String.format("Book was added id = %s", bookId);
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    public String updateBook(@ShellOption() long bookId,
                             @ShellOption() String bookName,
                             @ShellOption() long authorId,
                             @ShellOption() long genreId) {
        bookService.updateBook(bookId, bookName, authorId, genreId);
        return "Book was update";
    }

    @ShellMethod(value = "Get comment by id", key = {"gc", "getComment"})
    public String getCommentById(@ShellOption() long commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        return String.format("Comment: %s", comment.get());
    }

    @ShellMethod(value = "Get comment by book id", key = {"gcb", "getCommentBook"})
    public String getCommentByBookId(@ShellOption() long bookId) {
        List<Comment> commentList = commentService.getCommentByBookId(bookId);
        return String.format("Comment list: %s", commentList);
    }

    @ShellMethod(value = "Create comment", key = {"cc", "createComment"})
    public String createComment(@ShellOption() long bookId,
                             @ShellOption() String commentText) {
        commentService.insert(bookId, commentText);
        return "Comment was added";
    }

    @ShellMethod(value = "Update comment", key = {"uc", "updateComment"})
    public String updateBook(@ShellOption() long commentId,
                             @ShellOption() String commentText) {
        commentService.updateComment(commentId, commentText);
        return "Book was update";
    }

    @ShellMethod(value = "Delete comment", key = {"dc", "deleteComment"})
    public String deleteCommentById(@ShellOption() long commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        commentService.deleteCommentById(commentId);
        return String.format("Delete book: %s", comment);
    }
}
