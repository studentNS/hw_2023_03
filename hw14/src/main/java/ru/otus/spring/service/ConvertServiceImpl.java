package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.h2.AuthorRepository;
import ru.otus.spring.dao.h2.BookRepository;
import ru.otus.spring.dao.h2.GenreRepository;
import ru.otus.spring.domain.h2.Author;
import ru.otus.spring.domain.h2.Book;
import ru.otus.spring.domain.h2.Comment;
import ru.otus.spring.domain.h2.Genre;
import ru.otus.spring.domain.mongo.AuthorMongo;
import ru.otus.spring.domain.mongo.BookMongo;
import ru.otus.spring.domain.mongo.CommentMongo;
import ru.otus.spring.domain.mongo.GenreMongo;

@Service
@RequiredArgsConstructor
public class ConvertServiceImpl implements ConvertService {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    public Author convertAuthor(AuthorMongo authorMongo) {
        Author author = new Author();
        author.setName(authorMongo.getName());
        return author;
    }

    public Genre convertGenre(GenreMongo genreMongo) {
        Genre genre = new Genre();
        genre.setName(genreMongo.getName());
        return genre;
    }

    public Book convertBook(BookMongo bookMongo) {
        Book book = new Book();
        book.setName(bookMongo.getName());
        book.setGenre(genreRepository.findByName(bookMongo.getGenre().getName()));
        book.setAuthor(authorRepository.findByName(bookMongo.getAuthor().getName()));
        return book;
    }

    public Comment convertComment(CommentMongo commentMongo) {
        Comment comment = new Comment();
        comment.setText(commentMongo.getText());
        comment.setBook(bookRepository.findByName(commentMongo.getBook().getName()));
        return comment;
    }
}
