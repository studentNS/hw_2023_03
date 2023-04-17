package ru.otus.spring.service;

import com.opencsv.CSVReader;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    private final OutputService outputService;

    private final QuestionConverter questionConverter;

    public QuestionServiceImpl(QuestionDao dao, OutputService outputService, QuestionConverter questionConverter) {
        this.dao = dao;
        this.outputService = outputService;
        this.questionConverter = questionConverter;
    }

    @Override
    public List<Question> getQuestion() throws IOException {
        CSVReader reader = dao.findQuestion();
        return questionConverter.getQuestionsFromCVSReader(reader);
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
