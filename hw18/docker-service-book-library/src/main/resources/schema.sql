drop table if exists authors cascade;

create table authors
(
    id   bigserial primary key,
    name varchar(255)
);

drop table if exists genres cascade;

create table genres
(
    id   bigserial primary key,
    name varchar(255)
);

drop table if exists books cascade;

create table books
(
    id        bigserial primary key,
    name      varchar(255),
    author_id bigint references authors (id),
    genre_id  bigint references genres (id)
);

drop table if exists comments cascade;

create table comments
(
    id      bigserial primary key,
    text    varchar(255),
    book_id bigint
);