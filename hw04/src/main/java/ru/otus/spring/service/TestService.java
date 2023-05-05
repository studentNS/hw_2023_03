package ru.otus.spring.service;

import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.Testing;

public interface TestService {

    void startTest(Student student);

    void processingTest(Testing test);
}
