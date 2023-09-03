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
import ru.otus.spring.dao.h2.CommentRepository;
import ru.otus.spring.domain.h2.Comment;
import ru.otus.spring.domain.mongo.CommentMongo;
import ru.otus.spring.service.ConvertService;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfigComment {

    private static final int CHUNK_SIZE = 3;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final MongoTemplate mongoTemplate;

    private final ConvertService convertService;

    private final CommentRepository commentRepository;

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
    public RepositoryItemWriter<Comment> commentWriter() {
        return new RepositoryItemWriterBuilder<Comment>()
                .repository(commentRepository)
                .build();
    }

    @Bean
    ItemProcessor<CommentMongo, Comment> commentConverter() {
        return convertService::convertComment;
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
}