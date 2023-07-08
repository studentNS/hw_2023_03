package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Table("books")
public class Book {

    @Id
    private long id;

    @Column("name")
    private String name;

    @Column("author_id")
    private Author author;

    @Column("genre_id")
    private Genre genre;
}
