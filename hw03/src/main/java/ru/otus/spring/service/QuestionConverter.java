package ru.otus.spring.service;

import com.opencsv.CSVReader;
import ru.otus.spring.domain.Question;

import java.util.List;

public interface QuestionConverter {

    List<Question> getQuestionsFromCVSReader(CSVReader csvReader);
}
