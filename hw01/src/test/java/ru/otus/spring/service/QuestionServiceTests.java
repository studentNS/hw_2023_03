package ru.otus.spring.service;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionServiceTests {

    @Test
    public void showQuestionsAll() throws IOException {

        List<String> answersList = Arrays.asList("Edinburgh", "London", "Madrid");
        Question question = new Question("What is the capital of Great Britain?", answersList);
        List<Question> actualListQuestions = new ArrayList<>();
        actualListQuestions.add(question);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);

        List<Question> expectedListQuestions = service.getQuestion();
        assertThat(expectedListQuestions.size()).isGreaterThan(actualListQuestions.size());

        context.close();

    }

}
