package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionService {

    List<Question> getQuestion() throws IOException;

    void showQuestionsAll() throws IOException;

    void showQuestion(Question question);

}
