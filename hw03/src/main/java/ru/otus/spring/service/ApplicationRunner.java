package ru.otus.spring.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private final TestService testService;

    public ApplicationRunner(TestService testService) {
        this.testService = testService;
    }

    @Override
    public void run(String... args) {
        testService.startTest();
    }
}
