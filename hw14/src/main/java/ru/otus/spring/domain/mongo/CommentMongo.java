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
@Document(collection = "comments")
public class CommentMongo {

    @Id
    private String id;

    @Field(name = "text")
    private String text;

    @DBRef
    private BookMongo book;
}
