package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Egg;
import ru.otus.spring.integration.ButterflyGateway;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EggGeneratorImpl implements EggGenerator {

    public static final int COUNT_EGGS = 5;

    private final ButterflyGateway butterflyGateway;

    public List<Egg> generate() {
        List<Egg> eggList = new ArrayList<>();
        System.out.println("All eggs: " + COUNT_EGGS);
        for (int i = 0; i < COUNT_EGGS; i++) {
            eggList.add(new Egg("'Butterfly " + i + "'", i));
        }
        return eggList;
    }

    @Override
    public void run() {
        List<Butterfly> butterFlies = butterflyGateway.process(generate());
    }
}
