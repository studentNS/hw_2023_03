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
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "")
    public void initAuthors(AuthorMongoRepository authorRepository) {
        authors.add(authorRepository.save(new AuthorMongo("1", "Александр Пушкин")));
        authors.add(authorRepository.save(new AuthorMongo("2", "Лев Толстой")));
        authors.add(authorRepository.save(new AuthorMongo("3", "Стивен Кинг")));
        authors.add(authorRepository.save(new AuthorMongo("4", "Дмитрий Глуховский")));
        authors.add(authorRepository.save(new AuthorMongo("5", "Масамунэ Сиро")));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "")
    public void initGenres(GenreMongoRepository genreRepository) {
        genres.add(genreRepository.save(new GenreMongo("1", "Манга")));
        genres.add(genreRepository.save(new GenreMongo("2", "Роман")));
        genres.add(genreRepository.save(new GenreMongo("3", "Литературный вестерн")));
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "")
    public void initBooks(BookMongoRepository bookRepository) {
        bookRepository.save(new BookMongo("1", "Евгений Онегин", authors.get(0), genres.get(1)));
        bookRepository.save(new BookMongo("2", "Война и мир", authors.get(1), genres.get(1)));
        bookRepository.save(new BookMongo("3", "Тёмная башня", authors.get(2), genres.get(2)));
        bookRepository.save(new BookMongo("4", "Метро 2033", authors.get(3), genres.get(1)));
        bookRepository.save(new BookMongo("5", "Призрак в доспехах", authors.get(4), genres.get(0)));
    }

    @ChangeSet(order = "004", id = "initComments", author = "")
    public void initComments(CommentMongoRepository commentRepository, BookMongoRepository bookRepository) {
        comments.add(commentRepository.save(new CommentMongo("1", "Интересно",
                bookRepository.findById("1").get())));
        comments.add(commentRepository.save(new CommentMongo("2", "Почитать можно",
                bookRepository.findById("1").get())));
        comments.add(commentRepository.save(new CommentMongo("3", "Увлекательная книга",
                bookRepository.findById("2").get())));
        comments.add(commentRepository.save(new CommentMongo("4", "Неплохо",
                bookRepository.findById("3").get())));
    }
}
