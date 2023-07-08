package ru.otus.spring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.converter.BookDtoConverter;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Контроллер для работы с книгами должен")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookDtoConverter bookDtoConverter;

    @MockBean
    private CommentRepository commentRepository;

    @DisplayName("получать все книги")
    @Test
    public void shouldGetAllBookTest() {
        Book book = new Book(1L, "Евгений Онегин", null, null);
        BookDto bookDto = new BookDto(1L, "Евгений Онегин", null, null);
        List<BookDto> expectedBookList = new ArrayList<>();
        expectedBookList.add(bookDto);
        when(bookDtoConverter.toDto((any()))).thenReturn(bookDto);
        when(bookRepository.findAll()).thenReturn(Flux.just(book));
        webTestClient.get()
                .uri("/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookDto.class)
                .hasSize(expectedBookList.size())
                .value(actual -> {
                    assertThat(actual)
                            .usingRecursiveComparison()
                            .isEqualTo(expectedBookList);
                });
    }

    @DisplayName("выполнять создание книги")
    @Test
    public void shouldPostCreateBookTest() {
        Book book = new Book(1L, "Евгений Онегин",
                new Author(1L, "Александр Пушкин"), new Genre(1L, "Роман"));
        when(bookDtoConverter.fromDto((any()))).thenReturn(book);
        when(bookRepository.save("Дубровский",1L, 1L)).thenReturn(Mono.just(book));
        Flux<BookDto> result = webTestClient.post()
                .uri("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(book))
                .exchange()
                .expectStatus().isOk()
                .returnResult(BookDto.class)
                .getResponseBody();
        BookDto bookDtoResult = result.blockLast();
        assertThat(bookDtoResult).isEqualTo(bookDtoConverter.toDto(book));
    }

    @DisplayName("выполнять удаление книги")
    @Test
    public void shoulDeleteBookTest() {
        long expectedBookId = 1L;
        webTestClient.delete()
                .uri(String.format("/book/delete/%s", expectedBookId))
                .exchange()
                .expectStatus().isOk();
        verify(bookRepository).deleteById(expectedBookId);
    }
}
