package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.converter.CommentDtoConverter;
import ru.otus.spring.repository.CommentRepository;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentRepository commentRepository;

    private final CommentDtoConverter commentDtoConverter;

    @GetMapping("/book/comment/{bookId}")
    public Flux<CommentDto> commentBook(@PathVariable long bookId) {
        return commentRepository.findByBookId(bookId)
                .map(commentDtoConverter::toDto);
    }

    @GetMapping("/book/comment/one/{id}")
    public Mono<CommentDto> getCommentById(@PathVariable long id) {
        return commentRepository.findById(id)
                .map(commentDtoConverter::toDto);
    }

    @PutMapping("/book/comment/edit/{id}")
    public Mono<CommentDto> saveComment(@PathVariable("id") long id,
                                        @RequestBody CommentDto commentDto) {
        return commentRepository.update(commentDto.getId(), commentDto.getText());
    }

    @DeleteMapping("/book/comment/delete/{id}")
    public Mono<Void> deleteComment(@PathVariable("id") long commentId) {
        return commentRepository.deleteById(commentId);
    }

    @PostMapping("/book/comment/create/{bookId}")
    public Mono<CommentDto> createComment(@PathVariable("bookId") long bookId,
                              @RequestBody CommentDto commentDto) {
        return commentRepository.save(commentDto.getText(), bookId);
    }
}
