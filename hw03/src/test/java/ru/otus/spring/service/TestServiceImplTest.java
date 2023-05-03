package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.AppProps;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.Testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {

    @Mock
    private IOService ioService;

    @Mock
    private QuestionService questionService;

    @Mock
    private LocalizeService localizeService;

    @Mock
    private AppProps appProps;

    @Mock
    private StudentService studentService;

    private TestService testService;

    private Testing test;

    private Question question;

    private final int CORRECT_NUMBER_ANSWER = 2;

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(ioService, questionService, localizeService,
                appProps, studentService);

        List<String> answersList = Arrays.asList("Edinburgh", "London", "Madrid");
        question = new Question("What is the capital of Great Britain?",
                answersList, CORRECT_NUMBER_ANSWER);
        Student student = new Student("Ivan", "Ivanov");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        test = new Testing(student, questions);
    }

    @Test
    void startTest() {
        assertThat(test).isNotNull();
    }

    @Test
    void processingTest() {
        when(ioService.readInt()).thenReturn(CORRECT_NUMBER_ANSWER);
        testService.processingTest(test);
        verify(questionService, times(1))
                .showQuestion(question);
    }

    @Test
    void processingStudentAnswer() {
        int studentAnswer = 4;
        assertThat(Arrays.asList(1, 2, 3).contains(studentAnswer))
                .isEqualTo(false);
    }

    @Test
    void results() {
        when(ioService.readInt()).thenReturn(CORRECT_NUMBER_ANSWER);
        when(appProps.getMinCorrectAnswers()).thenReturn(3);
        int minCorrectAnswer = appProps.getMinCorrectAnswers();
        testService.processingTest(test);
        int studentCorrectAnswer = test.getCorrectAnswersCount();
        assertThat(minCorrectAnswer).isGreaterThan(studentCorrectAnswer);
    }
}
