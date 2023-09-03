package ru.otus.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.dao.mongo.AuthorMongoRepository;
import ru.otus.spring.dao.mongo.BookMongoRepository;
import ru.otus.spring.dao.mongo.CommentMongoRepository;
import ru.otus.spring.dao.mongo.GenreMongoRepository;
import ru.otus.spring.domain.mongo.AuthorMongo;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.domain.mongo.CommentMongo;
import ru.otus.spring.domain.mongo.GenreMongo;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private List<AuthorMongo> authors = new ArrayList<>();

    private List<GenreMongo> genres = new ArrayList<>();

    private List<CommentMongo> comments = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "")
    public void initAuthors(AuthorMongoRepository authorRepository) {
        authors.add(authorRepository.save(new AuthorMongo("1", "Александр Пушкин")));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "")
    public void initGenres(GenreMongoRepository genreRepository) {
        genres.add(genreRepository.save(new GenreMongo("1", "Роман")));
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "")
    public void initBooks(BookMongoRepository bookRepository) {
        bookRepository.save(new BookMongo("1", "Евгений Онегин", authors.get(0), genres.get(0)));
    }

    @ChangeSet(order = "004", id = "initComments", author = "")
    public void initComments(CommentMongoRepository commentRepository, BookMongoRepository bookRepository) {
        comments.add(commentRepository.save(new CommentMongo("1", "Интересно", bookRepository.findById("1").get())));
        comments.add(commentRepository.save(new CommentMongo("2", "Почитать можно", bookRepository.findById("1").get())));
    }
}
