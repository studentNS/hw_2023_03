package ru.otus.spring.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.repository.BookRepository;

@RequiredArgsConstructor
@Component
public class BooksHealthIndicator  implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        if (bookRepository.findAll().isEmpty()) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "No books")
                    .build();
        } else {
            return Health.up().build();
        }
    }
}
