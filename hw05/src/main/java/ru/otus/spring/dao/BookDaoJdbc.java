package ru.otus.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public Book getBookById(long bookId) {
        Map<String, Object> params = Collections.singletonMap("id", bookId);
        return namedParameterJdbcOperations.queryForObject("""
                select b.id, b.name, b.author_id, a.name author_name,
                b.genre_id, g.name genre_name
                from (books b left join authors a on b.author_id = a.id)
                left join genres g on g.id = b.genre_id
                where b.id = :id""", params, new BookMapper());
    }

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of(
                "name", book.getName(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()));
        namedParameterJdbcOperations.update("insert into books (name, author_id, genre_id) " +
                "values (:name, :author_id, :genre_id)", params, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Book> findAllBooks() {
        return namedParameterJdbcOperations.query("""
                select b.id, b.name, b.author_id, a.name author_name,
                b.genre_id, g.name genre_name
                from (books b left join authors a on b.author_id = a.id)
                left join genres g on g.id = b.genre_id""", new BookMapper());
    }

    @Override
    public void updateBook(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValues(Map.of(
                "id", book.getId(),
                "name", book.getName(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()));
        namedParameterJdbcOperations.update(
                "update books set name = :name, author_id = :author_id, genre_id = :genre_id " +
                        "where id = :id", params);
    }

    @Override
    public void deleteBookById(long bookId) {
        Map<String, Object> params = Collections.singletonMap("id", bookId);
        namedParameterJdbcOperations.update(
                "delete from books where id = :id", params
        );
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            long bookId = resultSet.getLong("id");
            String bookName = resultSet.getString("name");
            long authorId = resultSet.getLong("author_id");
            String authorName = resultSet.getString("author_name");
            long genreId = resultSet.getLong("genre_id");
            String genreName = resultSet.getString("genre_name");

            Genre genre = new Genre(genreId, genreName);
            Author author = new Author(authorId, authorName);
            return new Book(bookId, bookName, author, genre);
        }
    }
}
