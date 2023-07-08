package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями")
@SpringBootTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("получать комментарий по ID")
    @Test
    void shouldGetCommentByIdTest() {
        Mono<Comment> commentMono = commentRepository.findById(1L);
        assertThat(commentMono).isNotNull();
    }

    @DisplayName("добавлять комментарий к книге в БД")
    @Test
    void shouldInsertCommentTest() {
        Mono<Comment> commentMono = commentRepository.save("Текст", 1L);
        StepVerifier
                .create(commentMono)
                .expectComplete()
                .verify();
    }
}
