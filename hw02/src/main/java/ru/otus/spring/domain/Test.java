package ru.otus.spring.domain;

import java.util.List;

public class Test {

    private Student student;

    private int correctAnswersCount;

    private List<Question> questionsList;

    public Test(Student student, List<Question> questions) {
        this.student = student;
        this.questionsList = questions;
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setCorrectAnswersCount(int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public Student getStudent() {
        return student;
    }
}
