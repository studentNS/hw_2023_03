package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuestionServiceTest {

    private QuestionDao dao;
    private IOService ioService;
    private QuestionConverter questionConverter;
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        dao = mock(QuestionDao.class);
        ioService = mock(IOService.class);
        questionConverter = mock(QuestionConverter.class);
        questionService = new QuestionServiceImpl(dao, ioService, questionConverter);
    }

    @Test
    public void showQuestionsAll() throws IOException, CsvException {

        List<String> answersList = Arrays.asList("Edinburgh", "London", "Madrid");
        Question question = new Question("What is the capital of Great Britain?", answersList, 2);
        List<Question> expectedListQuestions = new ArrayList<>();
        expectedListQuestions.add(question);
        when(questionService.getQuestion()).thenReturn(expectedListQuestions);
        List<Question> actualListQuestions = questionService.getQuestion();
        assertThat(expectedListQuestions.size()).isEqualTo(actualListQuestions.size());

    }

}
