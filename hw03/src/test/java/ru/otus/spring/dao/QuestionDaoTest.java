package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.AppProps;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionDaoTest {

    @Mock
    private AppProps appProps;

    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl(appProps);
    }

    @Test
    public void findQuestion() {
        Locale l =new Locale("en");
        when(appProps.getLocale()).thenReturn(l);
        when(appProps.getCsvFileName()).thenReturn("questions");
        List<String[]> data = questionDao.findQuestion();
        assertThat(data).isNotNull();
    }
}
