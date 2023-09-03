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
import ru.otus.spring.dao.h2.BookRepository;
import ru.otus.spring.domain.h2.Book;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.service.ConvertService;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfigBook {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final ConvertService convertService;

    private final BookRepository bookRepository;

    @Bean
    public MongoItemReader<BookMongo> bookReader() {
        return new MongoItemReaderBuilder<BookMongo>()
                .template(mongoTemplate)
                .name("books")
                .jsonQuery("{ }")
                .targetType(BookMongo.class)
                .sorts(new HashMap<>())
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Book> bookWriter() {
        return new RepositoryItemWriterBuilder<Book>()
                .repository(bookRepository)
                .build();
    }

    @Bean
    ItemProcessor<BookMongo, Book> bookConverter() {
        return convertService::convertBook;
    }

    @Bean
    public Step booksStep(MongoItemReader<BookMongo> reader, ItemWriter<Book> writer,
                          ItemProcessor<BookMongo, Book> itemProcessor) {
        return new StepBuilder("booksStep", jobRepository)
                .allowStartIfComplete(true)
                .<BookMongo, Book>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}