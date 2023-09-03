package ru.otus.spring.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String MIGRATE_JOB_NAME = "migrateBooks";

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private final JobRepository jobRepository;
    @Bean
    public Job migrateJob(Step authorsStep, Step genresStep, Step booksStep, Step commentsStep) {
        return new JobBuilder(MIGRATE_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(authorsStep)
                .next(genresStep)
                .next(booksStep)
                .next(commentsStep)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }
}