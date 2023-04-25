package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.config.AppConfig;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class QuestionDaoTest {

    private AppConfig appConfig;
    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        appConfig = mock(AppConfig.class);
        questionDao = new QuestionDaoImpl(appConfig);
    }

    @Test
    public void findQuestion() throws IOException{

        when(appConfig.getCsvFileName()).thenReturn("questions.csv");
        CSVReader reader = questionDao.findQuestion();
        assertThat(reader).isNotNull();

    }

}
