package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с комментариями")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest {

    private static final long FIRST_COMENT_ID = 1L;

    private static final long THIRD_COMENT_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получать комментарий по ID")
    @Test
    void shouldGetCommentByIdTest() {
        Optional<Comment> optionalActualComment = commentRepositoryJpa.getCommentById(FIRST_COMENT_ID);
        Comment expectedComment = em.find(Comment.class, FIRST_COMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("получать список комметариев по книге")
    @Test
    void shouldGetCommentByBookTest() {
        List<Comment> actualCommentList = commentRepositoryJpa.getCommentByBook(FIRST_BOOK_ID);
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        List<Comment> expectedCommentList = book.getComments();
        assertThat(actualCommentList).usingRecursiveComparison().isEqualTo(expectedCommentList);
    }

    @DisplayName("добавлять комментарий к книге в БД")
    @Test
    void shouldInsertCommentTest() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        Comment actualComment = new Comment(THIRD_COMENT_ID, "Увлекательная книга", book);
        commentRepositoryJpa.insert(actualComment);
        Comment expectedComment = em.find(Comment.class, THIRD_COMENT_ID);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("обновлять комментарий к книге в БД")
    @Test
    void shouldUpdateComment() {
        Comment comment = em.find(Comment.class, FIRST_COMENT_ID);
        String oldTextComment = comment.getText();
        comment.setText("Увлекательная книга");
        Comment updateComment = commentRepositoryJpa.updateComment(comment);
        assertThat(updateComment.getText()).isNotEqualTo(oldTextComment);
    }

    @DisplayName("удалять комментарий к книге в БД")
    @Test
    void shouldDeleteComment() {
        commentRepositoryJpa.deleteCommentById(FIRST_COMENT_ID);
        Comment comment = em.find(Comment.class, FIRST_COMENT_ID);
        assertThat(comment).isNull();
    }
}
