package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.Test;

import java.io.IOException;
import java.util.List;

@Service
public class ApplicationRunner {

    private final QuestionService questionService;

    private final TestService testService;

    private final StudentService studentService;

    public ApplicationRunner(QuestionService questionService, TestService testService, StudentService studentService) {
        this.questionService = questionService;
        this.testService = testService;
        this.studentService = studentService;
    }

    public void run() throws IOException {
        Student student = studentService.getStudentFromConsole();
        List<Question> questions = questionService.getQuestion();
        if(questions.size() > 0) {
            Test test = testService.setTest(student, questions);
            testService.processingTest(test);
        }
    }
}
