package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;
import ru.otus.spring.domain.Chrysalis;
import ru.otus.spring.domain.Egg;

@Service
public class TransformServiceImpl implements TransformService {

    @Override
    public Caterpillar transformToCaterpillar(Egg egg) throws InterruptedException {
        System.out.println("Transform egg to caterpillar name:" + egg.getName());
        Thread.sleep(3000);
        System.out.println("Transform egg to caterpillar name:" + egg.getName() + " done");
        return new Caterpillar(egg.getName(), egg.getEggOrder());
    }

    @Override
    public Chrysalis transformToChrysalis(Caterpillar caterpillar) throws InterruptedException {
        System.out.println("Transform caterpillar to chrysalis name " + caterpillar.getName());
        Thread.sleep(3000);
        System.out.println("Transform caterpillar to Chrysalis name " + caterpillar.getName() + " done");
        return new Chrysalis(caterpillar.getName(), caterpillar.getEggOrder());
    }

    @Override
    public Butterfly transformToButterfly(Chrysalis chrysalis) throws InterruptedException {
        System.out.println("Transform chrysalis to butterfly name:" + chrysalis.getName());
        Thread.sleep(3000);
        System.out.println("Transform chrysalis to butterfly name:" + chrysalis.getName() + " done");
        return new Butterfly(chrysalis.getName(), chrysalis.getEggOrder());
    }
}
