package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами книг должен")
@DataMongoTest
public class AuthorRepositoryTest {

    private static final String FIRST_AUTHOR_ID = "1";

    private static final String SECOND_AUTHOR_ID = "2";

    private static final int EXPECTED_AUTHOR_COUNT = 1;

    @Autowired
    private AuthorRepository authorRepository;


    @DisplayName("получать автора по ID")
    @Test
    void shouldGetAuthorById() {
        Author actualAuthor = new Author("1", "Александр Пушкин");
        Optional<Author> expectedAuthor = authorRepository.findById(FIRST_AUTHOR_ID);
        assertThat(expectedAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(actualAuthor);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        Author author = new Author("2", "Лев Толстой");
        Author actualAuthor = authorRepository.save(author);
        Optional<Author> expectedAuthor = authorRepository.findById(SECOND_AUTHOR_ID);
        assertThat(expectedAuthor).isPresent().get()
                .usingRecursiveComparison().isEqualTo(actualAuthor);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldFindAllAuthors() {
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(EXPECTED_AUTHOR_COUNT);
    }
}
