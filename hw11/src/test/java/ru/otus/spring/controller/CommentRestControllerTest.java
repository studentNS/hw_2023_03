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
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.converter.CommentDtoConverter;
import ru.otus.spring.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Контроллер для работы с комментариями книг должен")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentDtoConverter commentDtoConverter;

    @MockBean
    private CommentRepository commentRepository;

    @DisplayName("получать комментарии книги")
    @Test
    public void shouldGetAllCommentsByBookTest() {
        Book book = new Book(1L, "Евгений Онегин", null, null);
        Comment comment = new Comment(1L, "Интересно", book);
        BookDto bookDto = new BookDto(1L, "Евгений Онегин", null, null);
        CommentDto commentDto = new CommentDto(1L, "Интересно", bookDto);
        List<CommentDto> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(commentDto);
        when(commentDtoConverter.toDto((any()))).thenReturn(commentDto);
        when(commentRepository.findByBookId(anyLong())).thenReturn(Flux.just(comment));
        webTestClient.get()
                .uri(String.format("/book/comment/%s", 1L))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(expectedCommentList.size())
                .value(actual -> {
                    assertThat(actual)
                            .usingRecursiveComparison()
                            .isEqualTo(expectedCommentList);
                });
    }

    @DisplayName("выполнять создание комментария книги")
    @Test
    public void shouldPostCreateCommentBookTest() {
        Book book = new Book(1L, "Евгений Онегин", null, null);
        Comment comment = new Comment(1L, "Интересно", book);
        when(commentDtoConverter.fromDto((any()))).thenReturn(comment);
        when(commentRepository.save("Почитать можно", 1L)).thenReturn(Mono.just(book));
        Flux<CommentDto> result = webTestClient.post()
                .uri(String.format("/book/comment/create/%s", 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(comment))
                .exchange()
                .expectStatus().isOk()
                .returnResult(CommentDto.class)
                .getResponseBody();
        CommentDto commentDtoResult = result.blockLast();
        assertThat(commentDtoResult).isEqualTo(commentDtoConverter.toDto(comment));
    }

    @DisplayName("выполнять удаление комментария книги")
    @Test
    public void shoulDeleteCommentBookTest() {
        long expectedCommentId = 1L;
        webTestClient.delete()
                .uri(String.format("/book/comment/delete/%s", expectedCommentId))
                .exchange()
                .expectStatus().isOk();
        verify(commentRepository).deleteById(expectedCommentId);
    }
}
