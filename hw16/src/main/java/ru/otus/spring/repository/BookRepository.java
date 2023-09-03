package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.domain.Book;

import java.util.List;

@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "books-entity-graph")
    List<Book> findAll();

    @RestResource(path = "name", rel = "name")
    List<Book> findBookByName(String name);
}
