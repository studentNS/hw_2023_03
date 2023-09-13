package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.BasePersistenceTest;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий для работы с комментариями")
public class CommentRepositoryTest /*extends BasePersistenceTest*/ {

    private static final long FIRST_COMENT_ID = 1L;

    private static final long THIRD_COMENT_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получать комментарий по ID")
    @Test
    void shouldGetCommentByIdTest() {
        Optional<Comment> optionalActualComment = commentRepository.findById(FIRST_COMENT_ID);
        Comment expectedComment = em.find(Comment.class, FIRST_COMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("добавлять комментарий к книге в БД")
    @Test
    void shouldInsertCommentTest() {
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        Comment actualComment = new Comment(THIRD_COMENT_ID, "Увлекательная книга", book);
        commentRepository.save(actualComment);
        Comment expectedComment = em.find(Comment.class, THIRD_COMENT_ID);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("обновлять комментарий к книге в БД")
    @Test
    void shouldUpdateComment() {
        Comment comment = em.find(Comment.class, FIRST_COMENT_ID);
        String oldTextComment = comment.getText();
        comment.setText("Увлекательная книга");
        Comment updateComment = commentRepository.save(comment);
        assertThat(updateComment.getText()).isNotEqualTo(oldTextComment);
    }

    @DisplayName("удалять комментарий к книге в БД")
    @Test
    void shouldDeleteComment() {
        commentRepository.deleteById(FIRST_COMENT_ID);
        Comment comment = em.find(Comment.class, FIRST_COMENT_ID);
        assertThat(comment).isNull();
    }
}
