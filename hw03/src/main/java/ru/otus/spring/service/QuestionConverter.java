package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

public interface QuestionConverter {

    List<Question> getQuestionsFromCVS(List<String[]> data);
}
