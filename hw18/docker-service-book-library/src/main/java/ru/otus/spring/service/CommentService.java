package ru.otus.spring.service;

import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDto> getCommentById(long commentId);

    List<CommentDto> getCommentByBookId(long bookId);

    void insert(CommentDto commentDto);

    void updateComment(CommentDto commentDto);

    void deleteCommentById(long commentId);

    void updateBook(BookDto bookDto);
}
