package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с комментариями книг")
@WebMvcTest(CommentRestController.class)
public class CommentRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    private static final long FIRST_COMMENT_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    private CommentDto comment;

    private BookDto book;

    @BeforeEach
    void setUp() {
        book = new BookDto(FIRST_BOOK_ID, "Евгений Онегин", null, null);
        comment = new CommentDto(FIRST_COMMENT_ID, "Интересно", book);
    }

    @DisplayName("получать комментарии книги")
    @Test
    public void shouldGetAllCommentsByBookTest() throws Exception {
        List<CommentDto> expectedCommentList = new ArrayList<>();
        expectedCommentList.add(comment);
        when(commentService.getCommentByBookId(book.getId())).thenReturn(expectedCommentList);
        mvc.perform(get(String.format("/book/comment/%s", FIRST_BOOK_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedCommentList)));
    }

    @DisplayName("возвращать комментарий по ID")
    @Test
    public void shouldGetCommentByIdTest() throws Exception {
        when(commentService.getCommentById(anyLong())).thenReturn(Optional.of(comment));
        mvc.perform(get(String.format("/book/comment/one/%s", FIRST_COMMENT_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(comment)));
    }

    @DisplayName("выполнять редактирование комментария книги")
    @Test
    public void shouldPostEditCommentBookTest() throws Exception {
        when(commentService.getCommentById(anyLong())).thenReturn(Optional.of(comment));
        comment.setText("Увлекательная книга");
        mvc.perform(put(String.format("/book/comment/edit/%s", FIRST_COMMENT_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(comment)))
                .andExpect(status().isOk());
        verify(commentService).updateComment(comment);
    }

    @DisplayName("выполнять создание комментария книги")
    @Test
    public void shouldPostCreateCommentBookTest() throws Exception {
        when(bookService.getBookDtoById(anyLong())).thenReturn(Optional.of(book));
        CommentDto expectedComment = new CommentDto(0L, "Интересно", book);
        mvc.perform(post(String.format("/book/comment/create/%s", FIRST_BOOK_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expectedComment)))
                .andExpect(status().isOk());
        verify(commentService).insert(expectedComment);
    }

    @DisplayName("выполнять удаление комментария книги")
    @Test
    public void shoulDeleteCommentBookTest() throws Exception {
        mvc.perform(delete(String.format("/book/comment/delete/%s", FIRST_COMMENT_ID)))
                .andExpect(status().isOk());
        verify(commentService).deleteCommentById(FIRST_COMMENT_ID);
    }
}
