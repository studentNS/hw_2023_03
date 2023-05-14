package ru.otus.spring.dao;

import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> getCommentById(long commentId);

    List<Comment> getCommentByBook(long bookId);

    Comment insert(Comment comment);

    Comment updateComment(Comment comment);

    void deleteCommentById(long commentId);
}
