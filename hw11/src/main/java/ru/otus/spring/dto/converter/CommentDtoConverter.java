package ru.otus.spring.dto.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;

@RequiredArgsConstructor
@Component
public class CommentDtoConverter implements DtoConverter<Comment, CommentDto> {

 /*   @Override
    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(),
                comment.getBookId());
    }

    @Override
    public Comment fromDto(CommentDto commentDto) {
        return new Comment(commentDto.getId(), commentDto.getText(),
        commentDto.getBookId());
    }*/

    private final DtoConverter<Book, BookDto> bookConverter;

    @Override
    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(),
                bookConverter.toDto(comment.getBook()));
    }

    @Override
    public Comment fromDto(CommentDto commentDto) {
        return new Comment(commentDto.getId(), commentDto.getText(),
                bookConverter.fromDto(commentDto.getBook()));
    }
}
