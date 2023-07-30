package ru.otus.spring.dto.converter;

import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;


@Component
public class AuthorDtoConverter implements DtoConverter<Author, AuthorDto> {

    @Override
    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName());
    }

    @Override
    public Author fromDto(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getName());
    }
}
