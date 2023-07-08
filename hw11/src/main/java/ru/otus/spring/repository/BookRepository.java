package ru.otus.spring.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    @Query("""
            select b.id, b.name,
                   author_id, a.name as author_name,
                   genre_id, g.name as genre_name from books b
            inner join authors a on b.author_id = a.id
            inner join genres g on b.genre_id = g.id
            order by b.id
            """)
    Flux<Book> findAll();

    @Query("insert into books (name, author_id, genre_id) values (:name, :authorId, :genreId)")
    Mono save(String name, long authorId, long genreId);

    @Query("update books set name = :name, author_id = :authorId, genre_id = :genreId where id = :bookId")
    Mono update(long bookId, String name, long authorId, long genreId);
}
