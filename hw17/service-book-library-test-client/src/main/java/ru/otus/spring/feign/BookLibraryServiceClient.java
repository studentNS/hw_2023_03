package ru.otus.spring.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.dto.BookDto;

import java.util.List;

@FeignClient("service-book-library")
public interface BookLibraryServiceClient {

    @GetMapping("/books")
    List<BookDto> allBooks();
}
