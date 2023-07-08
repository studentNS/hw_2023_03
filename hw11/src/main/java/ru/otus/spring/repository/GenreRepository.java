package ru.otus.spring.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.domain.Genre;

public interface GenreRepository extends ReactiveCrudRepository<Genre, Long> {

    Flux<Genre> findAll();
}
