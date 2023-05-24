package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Genre> getGenreById(long genreId) {
        return Optional.ofNullable(em.find(Genre.class, genreId));
    }

    @Override
    public Genre insert(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public List<Genre> findAllGenres() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }
}
