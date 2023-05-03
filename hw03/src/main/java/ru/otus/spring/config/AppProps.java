package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProps {

    private Locale locale;

    private int minCorrectAnswers;

    private String csvFileName;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public int getMinCorrectAnswers() {
        return minCorrectAnswers;
    }

    public void setMinCorrectAnswers(int minCorrectAnswers) {
        this.minCorrectAnswers = minCorrectAnswers;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }
}
