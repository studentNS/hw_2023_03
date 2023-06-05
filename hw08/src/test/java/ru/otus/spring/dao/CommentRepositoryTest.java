package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.spring.domain.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями должен")
@DataMongoTest
public class CommentRepositoryTest {

    private static final String FIRST_COMMENT_ID = "1";

    private static final String THIRD_COMMENT_ID = "3";

    @Autowired
    private CommentRepository commentRepository;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("получать комментарий по ID")
    @Test
    void shouldGetCommentByIdTest() {
        Comment actualComment = new Comment("1", "Интересно");
        Optional<Comment> expectedComment = commentRepository.findById(FIRST_COMMENT_ID);
        assertThat(expectedComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(actualComment);
    }

    @DisplayName("добавлять комментарий в БД")
    @Test
    void shouldInsertCommentTest() {
        Comment actualComment = new Comment(THIRD_COMMENT_ID, "Увлекательная книга");
        Comment expectedComment = commentRepository.save(actualComment);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @DisplayName("обновлять комментарий в БД")
    @Test
    void shouldUpdateComment() {
        Comment comment = commentRepository.findById(FIRST_COMMENT_ID).get();
        String oldTextComment = comment.getText();
        comment.setText("Увлекательная книга");
        Comment updateComment = commentRepository.save(comment);
        assertThat(updateComment.getText()).isNotEqualTo(oldTextComment);
    }

    @DisplayName("удалять комментарий в БД")
    @Test
    void shouldDeleteComment() {
        commentRepository.deleteById(FIRST_COMMENT_ID);
        Optional<Comment> comment = commentRepository.findById(FIRST_COMMENT_ID);
        assertThat(comment).isEmpty();
    }
}
