package ru.otus.spring.dto.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.GenreDto;

@RequiredArgsConstructor
@Component
public class BookDtoConverter implements DtoConverter<Book, BookDto> {

    private final DtoConverter<Author, AuthorDto> authorConverter;

    private final DtoConverter<Genre, GenreDto> genreConverter;

/*    @Override
    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getGenre());
    }

    @Override
    public Book fromDto(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getName(),
                bookDto.getAuthorDto(), bookDto.getGenreDto());
    }*/

    @Override
    public BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(),
                book.getAuthor() == null ? null : authorConverter.toDto(book.getAuthor()),
                book.getGenre() == null ? null : genreConverter.toDto(book.getGenre()));
    }

    @Override
    public Book fromDto(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getName(),
                bookDto.getAuthor() == null ? null : authorConverter.fromDto(bookDto.getAuthor()),
                bookDto.getGenre() == null ? null : genreConverter.fromDto(bookDto.getGenre()));
    }
}
