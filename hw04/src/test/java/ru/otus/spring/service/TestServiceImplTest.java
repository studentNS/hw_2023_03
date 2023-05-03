package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.AppProps;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.Testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с тестированием должен")
@SpringBootTest(classes = QuestionServiceImpl.class)
public class TestServiceImplTest {

    @MockBean
    private IOService ioService;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private LocalizeService localizeService;

    @MockBean
    private AppProps appProps;

    private TestService testService;

    private Testing test;

    private Question question;

    private final int CORRECT_NUMBER_ANSWER = 2;

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(ioService, questionService,
                localizeService, appProps);

        List<String> answersList = Arrays.asList("Edinburgh", "London", "Madrid");
        question = new Question("What is the capital of Great Britain?",
                answersList, CORRECT_NUMBER_ANSWER);
        Student student = new Student("Ivan", "Ivanov");
        List<Question> questions = new ArrayList<>();
        questions.add(question);
        test = new Testing(student, questions);
    }

    @DisplayName("запускать тест")
    @Test
    void startTest() {
        assertThat(test).isNotNull();
    }

    @DisplayName("проверять вызов метода отображения вопроса")
    @Test
    void processingTest() {
        when(ioService.readInt()).thenReturn(CORRECT_NUMBER_ANSWER);
        testService.processingTest(test);
        verify(questionService, times(1))
                .showQuestion(question);
    }

    @DisplayName("проверять ввод ответа из консоли")
    @Test
    void processingStudentAnswer() {
        int studentAnswer = 4;
        assertThat(Arrays.asList(1, 2, 3).contains(studentAnswer))
                .isEqualTo(false);
    }

    @DisplayName("выполнять проверку результата тестирования")
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
