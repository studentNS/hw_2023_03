package ru.otus.spring.dao;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Repository;
import ru.otus.spring.config.AppProps;
import ru.otus.spring.exception.FileCsvException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

@Repository
public class QuestionDaoImpl implements QuestionDao{

    private final AppProps appProps;

    public QuestionDaoImpl(AppProps appProps) {
        this.appProps = appProps;
    }

    @Override
    public List<String[]> findQuestion(){
        String csvFileNameLocale = csvFileNameLocale(appProps.getLocale(), appProps.getCsvFileName());
        InputStream isStream = getClass().getClassLoader().getResourceAsStream(csvFileNameLocale);
        CSVReader reader = null;
        if (isStream != null) {
            InputStreamReader isr = new InputStreamReader(isStream);
            reader = new CSVReaderBuilder(isr)
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(';')
                            .build())
                    .build();
        }
        try {
            return reader.readAll();
        } catch (IOException | CsvException | NumberFormatException | NullPointerException e) {
            throw new FileCsvException("Error csv file");
        }
    }

    public String csvFileNameLocale(Locale locale, String csvFileName){
        String localeString = locale.toString();
        return String.format("%s_%s.%s", csvFileName, localeString, "csv");
    }
}
