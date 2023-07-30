package ru.otus.spring.dao.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.h2.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByName(String name);
}
