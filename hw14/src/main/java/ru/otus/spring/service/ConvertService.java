package ru.otus.spring.service;

import ru.otus.spring.domain.h2.Author;
import ru.otus.spring.domain.h2.Book;
import ru.otus.spring.domain.h2.Comment;
import ru.otus.spring.domain.h2.Genre;
import ru.otus.spring.domain.mongo.AuthorMongo;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.domain.mongo.CommentMongo;
import ru.otus.spring.domain.mongo.GenreMongo;

public interface ConvertService {

    Author convertAuthor(AuthorMongo authorMongo);

    Genre convertGenre(GenreMongo genreMongo);

    Book convertBook(BookMongo bookMongo);

    Comment convertComment(CommentMongo commentMongo);
}
