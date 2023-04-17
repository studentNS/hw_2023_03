package ru.otus.spring.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import ru.otus.spring.domain.Question;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao{

    private final String fileCsv;

    public QuestionDaoImpl(String fileCsv) {
        this.fileCsv = fileCsv;
    }

    @Override
    public CSVReader findQuestion(){
        List<Question> questions = new ArrayList<>();
        InputStream isStream = getClass().getClassLoader().getResourceAsStream(fileCsv);
        CSVReader reader = null;
        if (isStream != null) {
            InputStreamReader isr = new InputStreamReader(isStream);
            reader = new CSVReaderBuilder(isr)
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(';')
                            .build())
                    .build();
        }
        return reader;
    }

}
