package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами книг должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @DisplayName("получать автора по ID")
    @Test
    void shouldGetAuthorByIdTest() {
        Author expectedAuthor = new Author(1, "Александр Пушкин");
        Author actualAuthor = authorDaoJdbc.getAuthorById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(2, "Лев Толстой");
        authorDaoJdbc.insert(expectedAuthor);
        Author actualAuthor = authorDaoJdbc.getAuthorById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldFindAllAuthors() {
        Author expectedAuthor = new Author(1, "Александр Пушкин");
        List<Author> actualAuthorList = authorDaoJdbc.findAllAuthors();
        assertThat(actualAuthorList)
                .containsExactlyInAnyOrder(expectedAuthor);
    }
}
