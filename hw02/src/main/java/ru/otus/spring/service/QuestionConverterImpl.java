package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionConverterImpl implements QuestionConverter {

    private final IOService ioService;

    public QuestionConverterImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public List<Question> getQuestionsFromCVSReader(CSVReader reader) {
        List<Question> questions = new ArrayList<>();

        try {
            List<String[]> data = reader.readAll();
            for (String[] row : data) {
                List<String> answerList = Arrays.stream(Arrays.copyOfRange(row, 1, row.length - 1))
                        .collect(Collectors.toList());
                questions.add(new Question(row[0], answerList, Integer.parseInt(row[row.length - 1])));

            }
        } catch (IOException | CsvException | NumberFormatException e) {
            ioService.outputString("Error csv file");
        }

        return questions;
    }
}
