package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами книг")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryJpaTest {

    private static final long FIRST_GENRE_ID = 1L;

    private static final long SECOND_GENRE_ID = 2L;

    private static final int EXPECTED_GENRE_COUNT = 1;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получать жанр книги по ID")
    @Test
    void shouldGetGenreById() {
        Optional<Genre> optionalActualGenre = genreRepositoryJpa.getGenreById(FIRST_GENRE_ID);
        Genre expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(optionalActualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("добавлять жанр книги в БД")
    @Test
    void shouldInsertGenre() {
        Genre genre = new Genre(2, "Литературный вестерн");
        Genre actualGenre = genreRepositoryJpa.insert(genre);
        Genre expectedGenre = em.find(Genre.class, SECOND_GENRE_ID);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый список жанров книг")
    @Test
    void shouldFindAllStyles() {
        List<Genre> genreList = genreRepositoryJpa.findAllGenres();
        assertThat(genreList).hasSize(EXPECTED_GENRE_COUNT);
    }
}
