package ru.otus.spring.dao.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.h2.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
