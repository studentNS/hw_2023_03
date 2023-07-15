package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.converter.BookDtoConverter;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookRepository bookRepository;

    private final BookDtoConverter bookDtoConverter;

    private final CommentRepository commentRepository;

    @GetMapping("/books")
    public Flux<BookDto> allBooks() {
        return bookRepository.findAll()
                .map(bookDtoConverter::toDto);
    }

    @GetMapping("/book/{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable("id") long id) {
        return bookRepository.findById(id)
                .map(bookDtoConverter::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PutMapping("/book/edit/{id}")
    public Mono<BookDto> updateBook(@PathVariable("id") long id, @RequestBody BookDto bookDto) {
        Book book = bookDtoConverter.fromDto(bookDto);
        return bookRepository.update(id, book.getName(), book.getAuthor().getId(), book.getGenre().getId());
    }

    @PostMapping("/book/create")
    public Mono<BookDto> insertBook(@RequestBody BookDto bookDto) {
        Book book = bookDtoConverter.fromDto(bookDto);
        return bookRepository.save(book.getName(), book.getAuthor().getId(), book.getGenre().getId());
    }

    @DeleteMapping("/book/delete/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") long bookId) {
        commentRepository.deleteByBookId(bookId);
        return bookRepository.deleteById(bookId);
    }
}