package ru.otus.spring.dao;

import ru.otus.spring.domain.Comment;

import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> getCommentById(long commentId);

    Comment insert(Comment comment);

    Comment updateComment(Comment comment);

    void deleteCommentById(long commentId);
}
