package ru.otus.spring.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.mongo.AuthorMongo;

public interface AuthorMongoRepository extends MongoRepository<AuthorMongo, String> {
}
