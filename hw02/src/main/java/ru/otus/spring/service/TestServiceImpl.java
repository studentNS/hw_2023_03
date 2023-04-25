package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.Test;

import java.util.Arrays;
import java.util.List;

@Service
public class TestServiceImpl implements TestService{

    private final IOService ioService;

    private final QuestionService questionService;

    private final AppConfig appConfig;

    public TestServiceImpl(IOService ioService, QuestionService questionService, AppConfig appConfig) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.appConfig = appConfig;
    }

    @Override
    public Test setTest(Student student, List<Question> questions) {
        return new Test(student, questions);
      }

    @Override
    public void processingTest(Test test) {

        int correctAnswersCount = 0;
        for (Question question: test.getQuestionsList()) {
            questionService.showQuestion(question);
            correctAnswersCount = processingStudentAnswer(correctAnswersCount, question);
        }
        test.setCorrectAnswersCount(correctAnswersCount);
        results(test);
    }

    private int processingStudentAnswer(int correctAnswersCount, Question question) {

        boolean correctInputNumber = false;

        while(!correctInputNumber) {
            ioService.outputString("Entry number answer 1-" + question.getAnswers().size());
            try {
                int studentAnswer = ioService.readInt();

                if (studentAnswer == question.getCorrectAnswer()) {
                    correctAnswersCount++;
                    correctInputNumber = true;
                }else if(!Arrays.asList(1, 2, 3).contains(studentAnswer)) {
                    ioService.outputString("Input error, try again");
                } else{
                    correctInputNumber = true;
                }
            } catch (NumberFormatException e) {
                ioService.outputString("Input error, try again");
            }
        }
        return correctAnswersCount;
    }

    private void results(Test test) {
        ioService.outputString("Results: ");
        ioService.outputString(test.getStudent().getFirstName() + " " + test.getStudent().getLastName());
        if(test.getCorrectAnswersCount() > appConfig.getMinCorrectAnswers()) {
            ioService.outputString("Test passed");
        }else{
            ioService.outputString("Test failed");
        }
    }

}
