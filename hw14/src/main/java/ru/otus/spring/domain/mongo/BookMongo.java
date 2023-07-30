package ru.otus.spring.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "books")
public class BookMongo {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @DBRef
    @Field(name = "author")
    private AuthorMongo author;

    @DBRef
    @Field(name = "genre")
    private GenreMongo genre;
}
