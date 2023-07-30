package ru.otus.spring.dao.h2;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.h2.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "books-entity-graph")
    List<Book> findAll();

    Book findByName(String name);
}
