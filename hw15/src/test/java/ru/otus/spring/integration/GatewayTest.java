package ru.otus.spring.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Egg;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GatewayTest {

    @Autowired
    private ButterflyGateway butterflyGateway;

    @Test
    @DisplayName("Инициализация контекста")
    void shouldLoadContext(ApplicationContext context) {
        assertThat(context).isNotNull();
    }

    @DisplayName("Из двух яиц должен вернуть только одну бабочку")
    @Test
    void butterfly() {
        List<Egg> eggList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            eggList.add(new Egg("'Butterfly " + i + "'", i));
        }
        List<Butterfly> result = butterflyGateway.process(eggList);
        assertThat(result.size()).isEqualTo(eggList.size() - 1);
    }
}
