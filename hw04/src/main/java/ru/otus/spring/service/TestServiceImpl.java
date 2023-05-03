package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.Testing;

import java.util.Arrays;
import java.util.List;

@Service
public class TestServiceImpl implements TestService{

    private final IOService ioService;

    private final QuestionService questionService;

    private final LocalizeService localizeService;

    private final AppProps appProps;

    public TestServiceImpl(IOService ioService, QuestionService questionService,
                           LocalizeService localizeService, AppProps appProps) {
        this.ioService = ioService;
        this.questionService = questionService;
        this.localizeService = localizeService;
        this.appProps = appProps;
    }

    @Override
    public void startTest(Student student) {
        List<Question> questions = questionService.getQuestion();
        if(questions.size() > 0) {
            Testing test = new Testing(student, questions);
            processingTest(test);
        }
      }

    @Override
    public void processingTest(Testing test) {

        int correctAnswersCount = 0;
        for (Question question: test.getQuestionsList()) {
            ioService.outputString(localizeService.getMessage("question"));
            questionService.showQuestion(question);
            correctAnswersCount = processingStudentAnswer(correctAnswersCount, question);
        }
        test.setCorrectAnswersCount(correctAnswersCount);
        results(test);
    }

    private int processingStudentAnswer(int correctAnswersCount, Question question) {

        boolean correctInputNumber = false;

        while(!correctInputNumber) {
            String correctAnswerLocale = localizeService.getMessage("correct-answer");
            ioService.outputString(correctAnswerLocale + question.getAnswers().size());
            try {
                int studentAnswer = ioService.readInt();

                if (studentAnswer == question.getCorrectAnswer()) {
                    correctAnswersCount++;
                    correctInputNumber = true;
                }else if(!Arrays.asList(1, 2, 3).contains(studentAnswer)) {
                    ioService.outputString(localizeService.getMessage("wrong-number"));
                } else{
                    correctInputNumber = true;
                }
            } catch (NumberFormatException e) {
                ioService.outputString(localizeService.getMessage("wrong-number"));
            }
        }
        return correctAnswersCount;
    }

    private void results(Testing test) {
        ioService.outputString(localizeService.getMessage("results"));
        ioService.outputString(test.getStudent().getFirstName() + " " + test.getStudent().getLastName());
        if(test.getCorrectAnswersCount() > appProps.getMinCorrectAnswers()) {
            ioService.outputString(localizeService.getMessage("test_passed"));
        }else{
            ioService.outputString(localizeService.getMessage("test_failed"));
        }
    }
}
