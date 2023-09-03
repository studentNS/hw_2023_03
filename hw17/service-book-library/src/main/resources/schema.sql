drop table if exists authors;

create table authors
(
    id   bigint primary key auto_increment,
    name varchar(255)
);

drop table if exists genres;

create table genres
(
    id   bigint primary key auto_increment,
    name varchar(255)
);

drop table if exists books;

create table books
(
    id        bigint primary key auto_increment,
    name      varchar(255),
    author_id bigint references authors (id),
    genre_id  bigint references genres (id)
);

drop table if exists comments;

create table comments
(
    id      bigint primary key auto_increment,
    text    varchar2(255),
    book_id bigint
);