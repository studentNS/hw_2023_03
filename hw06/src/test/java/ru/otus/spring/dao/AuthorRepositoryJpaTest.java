package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами книг должно")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final long SECOND_AUTHOR_ID = 2L;

    private static final int EXPECTED_AUTHOR_COUNT = 1;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получать автора по ID")
    @Test
    void shouldGetAuthorById() {
        Optional<Author> optionalActualAuthor = authorRepositoryJpa.getAuthorById(FIRST_AUTHOR_ID);
        Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author author = new Author(2, "Лев Толстой");
        Author actualAuthor = authorRepositoryJpa.insert(author);
        Author expectedAuthor = em.find(Author.class, SECOND_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldFindAllAuthors() {
        List<Author> authorList = authorRepositoryJpa.findAllAuthors();
        assertThat(authorList).hasSize(EXPECTED_AUTHOR_COUNT);
    }
}
