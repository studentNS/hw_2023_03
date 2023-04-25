package ru.otus.spring.service;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    private final IOService ioService;

    private final QuestionConverter questionConverter;

    public QuestionServiceImpl(QuestionDao dao, IOService ioService, QuestionConverter questionConverter) {
        this.dao = dao;
        this.ioService = ioService;
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

    @Override
    public void showQuestion(Question question) {
        ioService.outputString("Question: " + question.getQuestion());
        question.getAnswers().forEach(answer ->
                ioService.outputString("Answer: " + answer));
    }

}
