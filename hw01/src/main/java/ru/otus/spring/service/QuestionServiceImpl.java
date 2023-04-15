package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    private final OutputService outputService;

    public QuestionServiceImpl(QuestionDao dao, OutputService outputService) {
        this.dao = dao;
        this.outputService = outputService;
    }

    @Override
    public List<Question> getQuestion() throws IOException {
        return dao.findQuestion();
    }

    @Override
    public void showQuestionsAll() throws IOException {
        getQuestion().forEach(this::showQuestion);
    }

    private void showQuestion(Question question) {
        outputService.outputString("Question: " + question.getQuestion());
        question.getAnswers().forEach(answer ->
                outputService.outputString("Answer: " + answer));
    }

}
