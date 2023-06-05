package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.Optional;

public interface CommentService {

    Optional<Comment> getCommentById(String commentId);

    Comment insertComment(String commentText);

    Comment updateComment(String commentId, String commentText);

    void deleteCommentById(String commentId);

    void deleteComment(Comment comment);
}
