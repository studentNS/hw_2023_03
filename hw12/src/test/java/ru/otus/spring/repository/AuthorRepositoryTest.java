package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами книг должно")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthorRepositoryTest {

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final long SECOND_AUTHOR_ID = 2L;

    private static final int EXPECTED_AUTHOR_COUNT = 1;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("получать автора по ID")
    @Test
    void shouldGetAuthorById() {
        Optional<Author> optionalActualAuthor = authorRepository.findById(FIRST_AUTHOR_ID);
        Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(optionalActualAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author author = new Author(2, "Лев Толстой");
        Author actualAuthor = authorRepository.save(author);
        Author expectedAuthor = em.find(Author.class, SECOND_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldFindAllAuthors() {
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(EXPECTED_AUTHOR_COUNT);
    }
}
