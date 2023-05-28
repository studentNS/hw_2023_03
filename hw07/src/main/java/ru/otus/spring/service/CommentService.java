package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> getCommentById(long commentId);

    List<Comment> getCommentByBookId(long bookId);

    Comment insert(long bookId, String commentText);

    Comment updateComment(long commentId, String commentText);

    void deleteCommentById(long commentId);
}
