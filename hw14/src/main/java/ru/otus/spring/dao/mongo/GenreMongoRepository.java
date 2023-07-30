package ru.otus.spring.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.mongo.GenreMongo;

public interface GenreMongoRepository extends MongoRepository<GenreMongo, String> {
}
