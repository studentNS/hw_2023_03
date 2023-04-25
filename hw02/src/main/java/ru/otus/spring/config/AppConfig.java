package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final String csvFileName;

    private final int minCorrectAnswers;

    public AppConfig(@Value("${csv.file.name}") String csvFileName,
                     @Value("${min.correct.answers}") int minCorrectAnswers) {
        this.csvFileName = csvFileName;
        this.minCorrectAnswers = minCorrectAnswers;
    }

    public int getMinCorrectAnswers() {
        return minCorrectAnswers;
    }

    public String getCsvFileName() {
        return csvFileName;
    }
}
