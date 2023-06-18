package ru.otus.spring.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
