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
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами")
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private CommentService commentService;

    private static final long FIRST_AUTHOR_ID = 1L;

    private static final long FIRST_GENRE_ID = 1L;

    private static final long FIRST_BOOK_ID = 1L;

    private AuthorDto author;

    private GenreDto genre;

    private BookDto book;

    @BeforeEach
    void setUp() {
        author = new AuthorDto(FIRST_AUTHOR_ID, "Александр Пушкин");
        genre = new GenreDto(FIRST_GENRE_ID, "Роман");
        book = new BookDto(FIRST_BOOK_ID, "Евгений Онегин", author, genre);
    }

    @DisplayName("получать все книги")
    @Test
    public void shouldGetAllBookTest() throws Exception {
        List<BookDto> expectedBookList = new ArrayList<>();
        expectedBookList.add(book);
        when(bookService.findAllBooks()).thenReturn(expectedBookList);
        mvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedBookList)));
    }

    @DisplayName("возвращать книгу по ID")
    @Test
    public void shouldGetBookByIdTest() throws Exception {
        when(bookService.getBookDtoById(anyLong())).thenReturn(Optional.of(book));
        mvc.perform(get(String.format("/book/%s", FIRST_BOOK_ID)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(book)));
    }

    @DisplayName("выполнять редактирование книги")
    @Test
    public void shouldPostEditBookTest() throws Exception {
        book.setName("Дубровский");
        mvc.perform(put(String.format("/book/edit/%s", FIRST_BOOK_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk());
        verify(commentService).updateBook(book);
    }

    @DisplayName("выполнять создание книги")
    @Test
    public void shouldPostCreateBookTest() throws Exception {
        mvc.perform(post("/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk());
        verify(bookService).insertBook(book);
    }

    @DisplayName("выполнять удаление книги")
    @Test
    public void shoulDeleteBookTest() throws Exception {
        mvc.perform(delete(String.format("/book/delete/%s", FIRST_BOOK_ID)))
                .andExpect(status().isOk());
        verify(bookService).deleteBookById(FIRST_BOOK_ID);
    }
}
