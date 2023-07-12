package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.converter.BookDtoConverter;
import ru.otus.spring.dto.converter.CommentDtoConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookService bookService;

    private final CommentDtoConverter commentDtoConverter;

    private final BookDtoConverter bookDtoConverter;

    @Override
    public Optional<CommentDto> getCommentById(long commentId) {
        return commentRepository.findById(commentId).map(commentDtoConverter::toDto);
    }

    @Transactional
    @Override
    public List<CommentDto> getCommentByBookId(long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        return book.get().getComments().stream()
                .map(commentDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void insert(CommentDto commentDto) {
        commentRepository.save(commentDtoConverter.fromDto(commentDto));
    }

    @Override
    public void updateComment(CommentDto commentDto) {
        commentRepository.save(commentDtoConverter.fromDto(commentDto));
    }

    @Override
    public void deleteCommentById(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    @Override
    public void updateBook(BookDto bookDto) {
        List<CommentDto> commentDto = getCommentByBookId(bookDto.getId());
        Book book = bookDtoConverter.fromDto(bookDto);
        book.setComments(commentDto.stream()
                .map(commentDtoConverter::fromDto)
                .collect(Collectors.toList()));
        bookService.update(book);
    }
}
