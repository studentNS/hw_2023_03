package ru.otus.spring.domain;

import java.util.List;

public class Question {

    private String question;

    private List<String> answers;

    private int correctAnswer;

    public Question(String question, List<String> answerList, int correctAnswer) {
        this.question = question;
        this.answers = answerList;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
