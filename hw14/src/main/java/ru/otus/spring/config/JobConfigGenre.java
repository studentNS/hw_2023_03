package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.otus.spring.dao.h2.BookRepository;
import ru.otus.spring.dao.h2.CommentRepository;
import ru.otus.spring.dao.h2.GenreRepository;
import ru.otus.spring.domain.h2.Genre;
import ru.otus.spring.domain.mongo.GenreMongo;
import ru.otus.spring.service.ConvertService;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfigGenre {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final ConvertService convertService;

    private final GenreRepository genreRepository;

    @Bean
    public MongoItemReader<GenreMongo> genreReader() {
        return new MongoItemReaderBuilder<GenreMongo>()
                .template(mongoTemplate)
                .name("genres")
                .jsonQuery("{ }")
                .targetType(GenreMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public RepositoryItemWriter<Genre> genreWriter() {
        return new RepositoryItemWriterBuilder<Genre>()
                .repository(genreRepository)
                .build();
    }

    @Bean
    ItemProcessor<GenreMongo, Genre> genreConverter() {
        return convertService::convertGenre;
    }

    @Bean
    public Step genresStep(MongoItemReader<GenreMongo> reader, ItemWriter<Genre> writer,
                           ItemProcessor<GenreMongo, Genre> itemProcessor) {
        return new StepBuilder("genresStep", jobRepository)
                .allowStartIfComplete(true)
                .<GenreMongo, Genre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}