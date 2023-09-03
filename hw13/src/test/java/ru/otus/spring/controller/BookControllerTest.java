package ru.otus.spring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.GenreService;

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

@DisplayName("Контроллер для работы с книгами")
@WebMvcTest(BookController.class)
@WithMockUser(username = "user")
public class BookControllerTest {

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
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", expectedBookList))
                .andExpect(view().name("listBooks"));
    }

    @DisplayName("возвращать данные для редактирования книги")
    @Test
    public void shouldGetEditBookTest() throws Exception {
        when(bookService.getBookDtoById(book.getId())).thenReturn(Optional.of(book));
        mvc.perform(get("/edit")
                .queryParam("id", Long.toString(book.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("editBook"));
    }

    @DisplayName("выполнять редактирование книги")
    @Test
    public void shouldPostEditBookTest() throws Exception {
        mvc.perform(post("/edit").with(csrf())
                .flashAttr("book", book))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("/"));
        verify(commentService).updateBook(book);
    }

    @DisplayName("возвращать данные для создания книги")
    @Test
    public void shouldGetCreateBookTest() throws Exception {
        mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("editBook"));
    }

    @DisplayName("выполнять создание книги")
    @Test
    public void shouldPostCreateBookTest() throws Exception {
        mvc.perform(post("/create").with(csrf())
                .flashAttr("bookDto", book))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("/"));
        verify(bookService).insertBook(book);
    }

    @DisplayName("выполнять удаление книги")
    @Test
    public void shoulDeleteBookTest() throws Exception {
        long bookId = book.getId();
        mvc.perform(post("/delete").with(csrf())
                .queryParam("id", Long.toString(bookId)))
                .andExpect(status().is3xxRedirection())
                .andDo(print())
                .andExpect(redirectedUrl("/"));
        verify(bookService).deleteBookById(bookId);
    }
}
