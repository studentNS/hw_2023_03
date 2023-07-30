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
import ru.otus.spring.domain.h2.Author;
import ru.otus.spring.domain.h2.Book;
import ru.otus.spring.domain.h2.Comment;
import ru.otus.spring.domain.h2.Genre;
import ru.otus.spring.domain.mongo.AuthorMongo;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.domain.mongo.CommentMongo;
import ru.otus.spring.domain.mongo.GenreMongo;
import ru.otus.spring.service.ConvertService;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    public static final String MIGRATE_JOB_NAME = "migrateBooks";

    private static final int CHUNK_SIZE = 3;

    private final Logger logger = LoggerFactory.getLogger("Batch");

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final ConvertService convertService;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

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
    public MongoItemReader<CommentMongo> commentReader() {
        return new MongoItemReaderBuilder<CommentMongo>()
                .template(mongoTemplate)
                .name("comments")
                .jsonQuery("{ }")
                .targetType(CommentMongo.class)
                .sorts(new HashMap<>())
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Author> authorWriter() {
        return new RepositoryItemWriterBuilder<Author>()
                .repository(authorRepository)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Genre> genreWriter() {
        return new RepositoryItemWriterBuilder<Genre>()
                .repository(genreRepository)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Book> bookWriter() {
        return new RepositoryItemWriterBuilder<Book>()
                .repository(bookRepository)
                .build();
    }

    @Bean
    public RepositoryItemWriter<Comment> commentWriter() {
        return new RepositoryItemWriterBuilder<Comment>()
                .repository(commentRepository)
                .build();
    }

    @Bean
    ItemProcessor<AuthorMongo, Author> authorConverter() {
        return convertService::convertAuthor;
    }

    @Bean
    ItemProcessor<GenreMongo, Genre> genreConverter() {
        return convertService::convertGenre;
    }

    @Bean
    ItemProcessor<BookMongo, Book> bookConverter() {
        return convertService::convertBook;
    }

    @Bean
    ItemProcessor<CommentMongo, Comment> commentConverter() {
        return convertService::convertComment;
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

    @Bean
    public Step commentsStep(MongoItemReader<CommentMongo> reader, ItemWriter<Comment> writer,
                                 ItemProcessor<CommentMongo, Comment> itemProcessor) {
        return new StepBuilder("commentsStep", jobRepository)
                .allowStartIfComplete(true)
                .<CommentMongo, Comment>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

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