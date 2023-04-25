package ru.otus.spring.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.config.AppConfig;

import java.io.InputStream;
import java.io.InputStreamReader;

@Repository
public class QuestionDaoImpl implements QuestionDao{

    private final AppConfig appConfig;

    public QuestionDaoImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public CSVReader findQuestion(){

        InputStream isStream = getClass().getClassLoader().getResourceAsStream(appConfig.getCsvFileName());
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
