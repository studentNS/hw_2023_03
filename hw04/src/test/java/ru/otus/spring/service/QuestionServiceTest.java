package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с вопросами должен")
@SpringBootTest(classes = QuestionServiceImpl.class)
public class QuestionServiceTest {

    @MockBean
    private QuestionDao dao;

    @MockBean
    private IOService ioService;

    @MockBean
    private QuestionConverter questionConverter;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new QuestionServiceImpl(dao, ioService, questionConverter);
    }

    @DisplayName("возвращать список вопросов")
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
