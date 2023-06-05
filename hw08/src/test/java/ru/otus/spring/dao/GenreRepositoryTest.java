package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с жанрами книг должен")
@DataMongoTest
public class GenreRepositoryTest {

    private static final String FIRST_GENRE_ID = "1";

    private static final String SECOND_GENRE_ID = "2";

    private static final int EXPECTED_GENRE_COUNT = 1;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("получать жанр книги по ID")
    @Test
    void shouldGetGenreById() {
        Genre actualGenre = new Genre("1", "Роман");
        Optional<Genre> expectedGenre = genreRepository.findById(FIRST_GENRE_ID);
        assertThat(expectedGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(actualGenre);
    }

    @DisplayName("добавлять жанр книги в БД")
    @Test
    void shouldInsertGenre() {
        Genre genre = new Genre("2", "Литературный вестерн");
        Genre actualGenre = genreRepository.save(genre);
        Optional<Genre> expectedGenre = genreRepository.findById(SECOND_GENRE_ID);
        assertThat(expectedGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(actualGenre);
    }

    @DisplayName("возвращать ожидаемый список жанров книг")
    @Test
    void shouldFindAllStyles() {
        List<Genre> genreList = genreRepository.findAll();
        assertThat(genreList).hasSize(EXPECTED_GENRE_COUNT);
    }
}
