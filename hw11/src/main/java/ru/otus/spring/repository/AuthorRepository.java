package ru.otus.spring.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.domain.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {

    Flux<Author> findAll();
}
