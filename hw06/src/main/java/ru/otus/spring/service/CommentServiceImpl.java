package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.dao.CommentRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookService bookService;

    public CommentServiceImpl(CommentRepository commentRepository, BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getCommentById(long commentId) {
        return commentRepository.getCommentById(commentId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getCommentByBookId(long bookId) {
        return commentRepository.getCommentByBook(bookId);
    }

    @Transactional
    @Override
    public Comment insert(long bookId, String commentText) {
        Optional<Book> book = bookService.getBookById(bookId);
        Comment comment = new Comment(0, commentText, book.get());
        return commentRepository.insert(comment);
    }

    @Transactional
    @Override
    public Comment updateComment(long commentId, String commentText) {
        Optional<Comment> comment = commentRepository.getCommentById(commentId);
        comment.get().setText(commentText);
        return commentRepository.updateComment(comment.get());
    }

    @Transactional
    @Override
    public void deleteCommentById(long commentId) {
        commentRepository.deleteCommentById(commentId);
    }
}
