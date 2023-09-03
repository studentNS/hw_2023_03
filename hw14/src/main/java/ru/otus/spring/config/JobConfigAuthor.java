package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.spring.dao.h2.AuthorRepository;
import ru.otus.spring.domain.h2.Author;
import ru.otus.spring.domain.mongo.AuthorMongo;
import ru.otus.spring.service.ConvertService;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfigAuthor {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final ConvertService convertService;

    private final AuthorRepository authorRepository;

    @Bean
    public MongoItemReader<AuthorMongo> authorReader() {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .template(mongoTemplate)
                .name("authors")
                .jsonQuery("{ }")
                .targetType(AuthorMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public RepositoryItemWriter<Author> authorWriter() {
        return new RepositoryItemWriterBuilder<Author>()
                .repository(authorRepository)
                .build();
    }

    @Bean
    ItemProcessor<AuthorMongo, Author> authorConverter() {
        return convertService::convertAuthor;
    }

    @Bean
    public Step authorsStep(MongoItemReader<AuthorMongo> reader, ItemWriter<Author> writer,
                            ItemProcessor<AuthorMongo, Author> itemProcessor) {
        return new StepBuilder("authorsStep", jobRepository)
                .allowStartIfComplete(true)
                .<AuthorMongo, Author>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}