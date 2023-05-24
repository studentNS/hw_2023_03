package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorRepository;
import ru.otus.spring.domain.Author;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorDao;

    public AuthorServiceImpl(AuthorRepository authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Optional<Author> getAuthorById(long authorId) {
        return authorDao.getAuthorById(authorId);
    }
}
