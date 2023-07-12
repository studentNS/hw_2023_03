package ru.otus.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с комментариями книг")
@WebMvcTest(CommentController.class)
@WithMockUser(username = "user")
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

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
        when(bookService.getBookDtoById(book.getId())).thenReturn(Optional.of(book));
        mvc.perform(get("/comment")
                .queryParam("bookId", Long.toString(book.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", book))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attribute("comments", expectedCommentList))
                .andExpect(view().name("commentBook"));
    }

    @DisplayName("возвращать данные для редактирования комментария книги")
    @Test
    public void shouldGetEditCommentBookTest() throws Exception {
        when(commentService.getCommentById(comment.getId())).thenReturn(Optional.of(comment));
        mvc.perform(get("/comment/edit")
                .queryParam("commentId", Long.toString(comment.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("comment"))
                .andExpect(view().name("editComment"));
    }

    @DisplayName("выполнять редактирование комментария книги")
    @Test
    public void shouldPostEditCommentBookTest() throws Exception {
        when(commentService.getCommentById(comment.getId())).thenReturn(Optional.of(comment));
        mvc.perform(post("/comment/edit").with(csrf())
                .flashAttr("comment", comment))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("/"));
        verify(commentService).updateComment(comment);
    }

    @DisplayName("возвращать данные для создания комментария книги")
    @Test
    public void shouldGetCreateCommentBookTest() throws Exception {
        mvc.perform(get("/comment/create")
                .queryParam("bookId", Long.toString(book.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bookId"))
                .andExpect(view().name("createComment"));
    }

    @DisplayName("выполнять создание комментария книги")
    @Test
    public void shouldPostCreateCommentBookTest() throws Exception {
        when(bookService.getBookDtoById(book.getId())).thenReturn(Optional.of(book));
        CommentDto expectedComment = new CommentDto(0L, "Интересно", book);
        mvc.perform(post("/comment/create").with(csrf())
                .queryParam("bookId", Long.toString(book.getId()))
                .flashAttr("comment", expectedComment.getText()))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("/comment?bookId=" + book.getId()));
        verify(commentService).insert(expectedComment);
    }

    @DisplayName("выполнять удаление комментария книги")
    @Test
    public void shoulDeleteCommentBookTest() throws Exception {
        long commentId = comment.getId();
        mvc.perform(post("/comment/delete").with(csrf())
                .queryParam("commentId", Long.toString(commentId)))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("/"));
        verify(commentService).deleteCommentById(commentId);
    }
}
