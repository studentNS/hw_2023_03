package ru.otus.spring.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Book> getBookById(long bookId) {
        return Optional.ofNullable(em.find(Book.class, bookId));
    }

    @Override
    public Book insert(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public List<Book> findAllBooks() {
        EntityGraph<?> entityGraph = em.getEntityGraph("books-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book updateBook(Book book) {
        return em.merge(book);
    }

    @Override
    public void deleteBookById(long bookId) {
        Optional<Book> book = getBookById(bookId);
        em.remove(book.get());
    }
}
