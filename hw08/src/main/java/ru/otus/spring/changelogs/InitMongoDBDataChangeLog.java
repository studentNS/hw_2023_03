package ru.otus.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.dao.AuthorRepository;
import ru.otus.spring.dao.BookRepository;
import ru.otus.spring.dao.CommentRepository;
import ru.otus.spring.dao.GenreRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private List<Author> authors = new ArrayList<>();

    private List<Genre> genres = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "")
    public void initAuthors(AuthorRepository authorRepository) {
        authors.add(authorRepository.save(new Author("1", "Александр Пушкин")));
        authors.add(authorRepository.save(new Author("2", "Лев Толстой")));
        authors.add(authorRepository.save(new Author("3", "Стивен Кинг")));
        authors.add(authorRepository.save(new Author("4", "Дмитрий Глуховский")));
        authors.add(authorRepository.save(new Author("5", "Масамунэ Сиро")));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "")
    public void initGenres(GenreRepository genreRepository) {
        genres.add(genreRepository.save(new Genre("1", "Манга")));
        genres.add(genreRepository.save(new Genre("2", "Роман")));
        genres.add(genreRepository.save(new Genre("3", "Литературный вестерн")));
    }

    @ChangeSet(order = "003", id = "initComments", author = "")
    public void initComments(CommentRepository commentRepository) {
        comments.add(commentRepository.save(new Comment("1", "Интересно")));
        comments.add(commentRepository.save(new Comment("2", "Почитать можно")));
        comments.add(commentRepository.save(new Comment("3", "Увлекательная книга")));
        comments.add(commentRepository.save(new Comment("4", "Неплохо")));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "")
    public void initBooks(BookRepository bookRepository) {
        bookRepository.save(new Book("1", "Евгений Онегин", authors.get(0), genres.get(1),
                Arrays.asList(comments.get(0), comments.get(1))));
        bookRepository.save(new Book("2", "Война и мир", authors.get(1), genres.get(1),
                Arrays.asList(comments.get(2))));
        bookRepository.save(new Book("3", "Тёмная башня", authors.get(2), genres.get(2),
                Arrays.asList(comments.get(3))));
        bookRepository.save(new Book("4", "Метро 2033", authors.get(3), genres.get(1),
                null));
        bookRepository.save(new Book("5", "Призрак в доспехах", authors.get(4), genres.get(0),
                null));
    }
}
