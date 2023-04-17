package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionDao{

    CSVReader findQuestion() throws IOException;

}
