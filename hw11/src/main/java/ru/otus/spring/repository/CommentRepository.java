package ru.otus.spring.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Comment;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {

    @Query("""
            select c.id, c.text, c.book_id from comments c
            where c.book_id = :bookId
            order by c.id
            """)
    Flux<Comment> findByBookId(long bookId);

    Mono<Comment> findById(long id);

    @Query("update comments set text = :text where id = :commentId")
    Mono update(long commentId, String text);

    @Query("insert into comments (text, book_id) values (:text, :bookId)")
    Mono save(String text, long bookId);

    @Query("delete from comments where book_id = :book_id")
    void deleteByBookId(long bookId);
}
