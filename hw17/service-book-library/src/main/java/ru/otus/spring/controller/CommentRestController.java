package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/book/comment/{bookId}")
    public List<CommentDto> commentBook(@PathVariable long bookId) {
        return commentService.getCommentByBookId(bookId);
    }

    @GetMapping("/book/comment/one/{id}")
    public CommentDto getCommentById(@PathVariable long id) {
        return commentService.getCommentById(id).get();
    }

    @PutMapping("/book/comment/edit/{id}")
    public void saveComment(@PathVariable("id") long id, @RequestBody CommentDto commentDto) {
        CommentDto comment = commentService.getCommentById(commentDto.getId()).get();
        comment.setText(commentDto.getText());
        commentService.updateComment(comment);
    }

    @DeleteMapping("/book/comment/delete/{id}")
    public void deleteComment(@PathVariable("id") long commentId) {
        commentService.deleteCommentById(commentId);
    }

    @PostMapping("/book/comment/create/{bookId}")
    public void createComment(@PathVariable("bookId") long bookId,
                              @RequestBody CommentDto commentDto) {
        BookDto book = bookService.getBookDtoById(bookId).get();
        CommentDto comment = new CommentDto(0L, commentDto.getText(), book);
        commentService.insert(comment);
    }
}
