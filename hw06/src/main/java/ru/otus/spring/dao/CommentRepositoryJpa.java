package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;

import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Comment> getCommentById(long commentId) {
        return Optional.ofNullable(em.find(Comment.class, commentId));
    }

    @Override
    public Comment insert(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return em.merge(comment);
    }

    @Override
    public void deleteCommentById(long commentId) {
        Optional<Comment> comment = getCommentById(commentId);
        em.remove(comment.get());
    }
}
