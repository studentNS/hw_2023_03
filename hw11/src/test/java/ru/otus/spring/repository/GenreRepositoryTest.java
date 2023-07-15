package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Репозиторий для работы с жанрами книг")
@SpringBootTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("получать жанр книги по ID")
    @Test
    void shouldGetGenreById() {
        Mono<Genre> genreMono = genreRepository.findById(1L);
        assertThat(genreMono).isNotNull();
    }

    @DisplayName("добавлять жанр книги в БД")
    @Test
    void shouldInsertGenre() {
        Mono<Genre> genreMono = genreRepository.save(new Genre(1, "Жанр"));
        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertNotNull(genre.getId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("возвращать ожидаемый список жанров книг")
    @Test
    void shouldFindAllStyles() {
        Flux<Genre> genreFlux = genreRepository.findAll();
        assertThat(genreFlux).isNotNull();
    }
}
