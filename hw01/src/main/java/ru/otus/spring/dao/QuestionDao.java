package ru.otus.spring.dao;

import com.opencsv.CSVReader;

import java.io.IOException;

public interface QuestionDao{

    CSVReader findQuestion() throws IOException;

}
