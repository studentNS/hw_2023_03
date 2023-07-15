package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.domain.Author;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами книг должно")
@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;


    @DisplayName("получать автора по ID")
    @Test
    void shouldGetAuthorById() {
        Mono<Author> authorMono = authorRepository.findById(1L);
        assertThat(authorMono).isNotNull();
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Mono<Author> authorMono = authorRepository.save(new Author(1, "Автор"));
        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertNotNull(author.getId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldFindAllAuthors() {
        Flux<Author> authorFlux = authorRepository.findAll();
        assertThat(authorFlux).isNotNull();
    }
}
