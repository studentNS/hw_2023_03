package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Caterpillar;

@Service
public class BirdServiceImpl implements BirdService {

    @Override
    public void attack(Caterpillar caterpillar) {
        System.out.println("Bird attack caterpillar name: " + caterpillar.getName());;
    }
}
