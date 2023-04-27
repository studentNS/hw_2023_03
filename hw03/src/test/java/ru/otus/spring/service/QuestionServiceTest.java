package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionDao dao;

    @Mock
    private IOService ioService;

    @Mock
    private QuestionConverter questionConverter;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(dao, ioService, questionConverter);
    }

    @Test
    public void showQuestionsAll() {
        List<String> answersList = Arrays.asList("Edinburgh", "London", "Madrid");
        Question question = new Question("What is the capital of Great Britain?", answersList, 2);
        List<Question> expectedListQuestions = new ArrayList<>();
        expectedListQuestions.add(question);
        when(questionService.getQuestion()).thenReturn(expectedListQuestions);
        List<Question> actualListQuestions = questionService.getQuestion();
        assertThat(expectedListQuestions.size()).isEqualTo(actualListQuestions.size());
    }
}
