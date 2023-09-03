package ru.otus.spring.dao.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.h2.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByName(String name);
}
