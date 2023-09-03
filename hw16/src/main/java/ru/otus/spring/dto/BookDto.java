package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {

    private long id;

    private String name;

    private AuthorDto author;

    private GenreDto genre;

    //private List<CommentDto> comment;
}
