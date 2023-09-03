package ru.otus.spring.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.otus.spring.repository.BookRepository;

@RequiredArgsConstructor
@Component
public class BooksHealthIndicator  implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Health health() {
        try {
            jdbcTemplate.execute("select 1");
            return Health.up()
                    .withDetail("message", "Status connection to database UP")
                    .build();
        } catch (Throwable t) {
            return Health.down()
                    .withDetail("message", "Status connection to database DOWN")
                    .build();
        }
    }
}
