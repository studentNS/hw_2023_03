package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CommentDto {

    private Long id;

    private String text;

    private BookDto book;
}
