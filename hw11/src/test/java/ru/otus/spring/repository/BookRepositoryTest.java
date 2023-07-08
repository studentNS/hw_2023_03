package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами")
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("получать книгу по ID")
    @Test
    void shouldGetBookById() {
        Mono<Book> bookMono = bookRepository.findById(1L);
        assertThat(bookMono).isNotNull();
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Mono<Book> bookMono = bookRepository
                .save("Название",1L, 1L);
        StepVerifier
                .create(bookMono)
                .verifyComplete();
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldFindAllBooks() {
        Flux<Book> bookFlux = bookRepository.findAll();
        assertThat(bookFlux).isNotNull();
    }
}
