package ru.otus.spring.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Проверка защиты необходимых ресурсов " +
        "(доступ запрещен для неавторизованных пользователей) ")
@WebMvcTest
public class SecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private CommentService commentService;

    @DisplayName("получение списка книг")
    @Test
    public void shouldGetAllBook() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("редактирование книги")
    @Test
    public void shouldGetEditBook() throws Exception {
        mvc.perform(get("/edit?id=1"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("создание книги")
    @Test
    public void shouldGetCreateBook() throws Exception {
        mvc.perform(get("/create"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("удаление книги")
    @Test
    public void shoulDeleteBook() throws Exception {
        mvc.perform(get("/delete?id=1"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("получение комментариев книги")
    @Test
    public void shouldGetAllCommentsByBook() throws Exception {
        mvc.perform(get("/comment?id=1"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("редактирование комментария книги")
    @Test
    public void shouldPostEditCommentBook() throws Exception {
        mvc.perform(get("/comment/edit?commentId=1"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("создание комментария книги")
    @Test
    public void shouldPostCreateCommentBook() throws Exception {
        mvc.perform(get("/comment/create?bookId=1"))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("удаление комментария книги")
    @Test
    public void shoulDeleteCommentBook() throws Exception {
        mvc.perform(get("/comment/delete?commentId=1"))
                .andExpect(status().isUnauthorized());
    }
}