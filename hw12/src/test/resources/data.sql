insert into genres (name) values ('Роман');

insert into authors (name) values ('Александр Пушкин');

insert into books (name, author_id, genre_id) values ('Евгений Онегин', 1, 1);

insert into comments (text, book_id) values ('Интересно', 1);
insert into comments (text, book_id) values ('Почитать можно', 1);
