package ru.otus.spring.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.h2.BookRepository;
import ru.otus.spring.domain.h2.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.spring.config.JobConfig.MIGRATE_JOB_NAME;

@SpringBootTest
@SpringBatchTest
public class JobMigrateFomMongoTest {

    private static final int EXPECTED_BOOK_COUNT = 1;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @DisplayName("Проверка статуса миграции (COMPLETED)")
    @Test
    public void MigrateFomMongoStatusTest() throws Exception {

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(MIGRATE_JOB_NAME);
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        List<Book> bookList = bookRepository.findAll();
        assertEquals(EXPECTED_BOOK_COUNT, bookList.size());
    }
}
