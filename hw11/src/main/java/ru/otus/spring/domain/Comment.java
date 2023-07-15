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
@Table("comments")
public class Comment {

    @Id
    private long id;

    private String text;

    @Column("book_id")
    private Book book;

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
