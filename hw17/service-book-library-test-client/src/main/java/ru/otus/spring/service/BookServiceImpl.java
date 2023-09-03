package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.feign.BookLibraryServiceClient;
import ru.otus.spring.dto.BookDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookLibraryServiceClient bookLibraryServiceClient;

    @Override
    public List<BookDto> findAllBooks() {
        return bookLibraryServiceClient.allBooks();
    }


}
