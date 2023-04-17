package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionConverterImpl implements QuestionConverter {

    @Override
    public List<Question> getQuestionsFromCVSReader(CSVReader reader) {
        List<Question> questions = new ArrayList<>();

        try {
            List<String[]> data = reader.readAll();
            for (String[] row : data) {
                List<String> answerList = Arrays.stream(Arrays.copyOfRange(row, 1, row.length))
                        .collect(Collectors.toList());
                questions.add(new Question(row[0], answerList));

            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
