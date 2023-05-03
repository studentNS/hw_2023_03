package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionConverterImpl implements QuestionConverter {

    @Override
    public List<Question> getQuestionsFromCVS(List<String[]> data) {
        List<Question> questions = new ArrayList<>();
        for (String[] row : data) {
            List<String> answerList = Arrays.stream(Arrays.copyOfRange(row, 1, row.length - 1))
                    .collect(Collectors.toList());
            questions.add(new Question(row[0], answerList, Integer.parseInt(row[row.length - 1])));

        }
        return questions;
    }
}
