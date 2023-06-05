package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.CommentRepository;
import ru.otus.spring.domain.Comment;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> getCommentById(String commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public Comment insertComment(String commentText) {
        Comment comment = new Comment(null, commentText);
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(String commentId, String commentText) {
        Optional<Comment> comment = getCommentById(commentId);
        comment.get().setText(commentText);
        return commentRepository.save(comment.get());
    }

    @Override
    public void deleteCommentById(String commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}
