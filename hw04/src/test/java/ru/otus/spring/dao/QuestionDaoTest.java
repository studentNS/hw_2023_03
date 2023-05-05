package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.AppProps;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Dao для работы с csv файлом должно")
@SpringBootTest(classes = QuestionDaoImpl.class)
public class QuestionDaoTest {

    @MockBean
    private AppProps appProps;

    private QuestionDao questionDao;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoImpl(appProps);
    }

    @DisplayName("прочитать файл")
    @Test
    public void findQuestion() {
        Locale l =new Locale("en");
        when(appProps.getLocale()).thenReturn(l);
        when(appProps.getCsvFileName()).thenReturn("questions");
        List<String[]> data = questionDao.findQuestion();
        assertThat(data).isNotNull();
    }
}
