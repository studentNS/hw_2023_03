package ru.otus.spring.service;

import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;
import ru.otus.spring.domain.Chrysalis;
import ru.otus.spring.domain.Egg;

public interface TransformService {

    Caterpillar transformToCaterpillar(Egg egg) throws InterruptedException;

    Chrysalis transformToChrysalis(Caterpillar caterpillar) throws InterruptedException;

    Butterfly transformToButterfly(Chrysalis chrysalis) throws InterruptedException;
}
