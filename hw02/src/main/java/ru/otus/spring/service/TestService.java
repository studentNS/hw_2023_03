package ru.otus.spring.service;

import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.Test;

import java.util.List;

public interface TestService {

    Test setTest(Student student, List<Question> questions);

    void processingTest(Test test);
}
