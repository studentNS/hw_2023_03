insert into genres (name) values ('Манга');
insert into genres (name) values ('Роман');
insert into genres (name) values ('Литературный вестерн');

insert into authors (name) values ('Александр Пушкин');
insert into authors (name) values ('Лев Толстой');
insert into authors (name) values ('Стивен Кинг');
insert into authors (name) values ('Дмитрий Глуховский');
insert into authors (name) values ('Масамунэ Сиро');

insert into books (name, author_id, genre_id) values ('Евгениcй Онегин', 1, 2);
insert into books (name, author_id, genre_id) values ('Война и мир', 2, 2);
insert into books (name, author_id, genre_id) values ('Тёмная башня', 3, 3);
insert into books (name, author_id, genre_id) values ('Метро 2033', 4, 2);
insert into books (name, author_id, genre_id) values ('Призрак в доспехах', 5, 1);
