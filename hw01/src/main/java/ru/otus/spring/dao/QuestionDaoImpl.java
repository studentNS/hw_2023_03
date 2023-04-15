package ru.otus.spring.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDaoImpl implements QuestionDao{

    private final String fileCsv;

    public QuestionDaoImpl(String fileCsv) {
        this.fileCsv = fileCsv;
    }

    @Override
    public List<Question> findQuestion(){
        List<Question> questions = new ArrayList<>();
        InputStream isStream = getClass().getClassLoader().getResourceAsStream(fileCsv);

        if (isStream != null) {
            InputStreamReader isr = new InputStreamReader(isStream);
            CSVReader reader = new CSVReaderBuilder(isr)
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(';')
                            .build())
                    .build();
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
        }
        return questions;
    }

}
