package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами книг")
@JdbcTest
@Import(GenreDaoJdbc.class)
public class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @DisplayName("получать жанр книги по ID")
    @Test
    void shouldGetGenreByIdTest() {
        Genre expectedGenre = new Genre(1, "Роман");
        Genre actualGenre = genreDaoJdbc.getGenreById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("добавлять жанр книги в БД")
    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(2, "Литературный вестерн");
        genreDaoJdbc.insert(expectedGenre);
        Genre actualGenre = genreDaoJdbc.getGenreById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый список жанров книг")
    @Test
    void shouldFindAllStyles() {
        Genre expectedGenre = new Genre(1, "Роман");
        List<Genre> actualGenreList = genreDaoJdbc.findAllGenres();
        assertThat(actualGenreList)
                .containsExactlyInAnyOrder(expectedGenre);
    }
}
