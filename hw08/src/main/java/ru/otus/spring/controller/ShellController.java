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
    public String getBookById(@ShellOption() String bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        return String.format("Book: %s", book);
    }

    @ShellMethod(value = "Delete book", key = {"d", "delete"})
    public String deleteBookById(@ShellOption() String bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        bookService.deleteBookById(bookId);
        return String.format("Delete book: %s", book);
    }

    @ShellMethod(value = "Create book", key = {"c", "create"})
    public String createBook(@ShellOption() String bookName,
                             @ShellOption() String authorId,
                             @ShellOption() String genreId) {
        Book bookId = bookService.insertBook(bookName, authorId, genreId);
        return String.format("Book was added id = %s", bookId);
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    public String updateBook(@ShellOption() String bookId,
                             @ShellOption() String bookName,
                             @ShellOption() String authorId,
                             @ShellOption() String genreId) {
        bookService.updateBook(bookId, bookName, authorId, genreId);
        return "Book was update";
    }

    @ShellMethod(value = "Get comment by id", key = {"gc", "getComment"})
    public String getCommentById(@ShellOption() String commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        return String.format("Comment: %s", comment.get());
    }

    @ShellMethod(value = "Get comment by book id", key = {"gcb", "getCommentBook"})
    public String getCommentByBookId(@ShellOption() String bookId) {
        List<Comment> commentList = bookService.getCommentByBookId(bookId);
        return String.format("Comment list: %s", commentList);
    }

    @ShellMethod(value = "Create comment by book id", key = {"cc", "createComment"})
    public String createComment(@ShellOption() String bookId,
                             @ShellOption() String commentText) {
        Comment comment = bookService.addComment(bookId, commentText);
        return String.format("Comment was added:  %s", comment);
    }

    @ShellMethod(value = "Update comment", key = {"uc", "updateComment"})
    public String updateBook(@ShellOption() String commentId,
                             @ShellOption() String commentText) {
        commentService.updateComment(commentId, commentText);
        return "Comment was update";
    }

    @ShellMethod(value = "Delete comment", key = {"dc", "deleteComment"})
    public String deleteCommentById(@ShellOption() String commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        commentService.deleteCommentById(commentId);
        return String.format("Delete comment: %s", comment);
    }
}
