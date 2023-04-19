package ru.otus.spring.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;

public class QuestionDaoImpl implements QuestionDao{

    private final String fileCsv;

    public QuestionDaoImpl(String fileCsv) {
        this.fileCsv = fileCsv;
    }

    @Override
    public CSVReader findQuestion(){
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
