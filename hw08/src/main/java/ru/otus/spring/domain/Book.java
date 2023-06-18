package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Field(name = "name")
    private String name;

    @DBRef
    @Field(name = "author")
    private Author author;

    @DBRef
    @Field(name = "genre_")
    private Genre genre;

    @DBRef(lazy = true)
    @Field(name = "comments")
    @ToString.Exclude
    private List<Comment> comments;
}
