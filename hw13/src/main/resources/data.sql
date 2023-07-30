insert into genres (name) values ('Манга');
insert into genres (name) values ('Роман');
insert into genres (name) values ('Литературный вестерн');

insert into authors (name) values ('Александр Пушкин');
insert into authors (name) values ('Лев Толстой');
insert into authors (name) values ('Стивен Кинг');
insert into authors (name) values ('Дмитрий Глуховский');
insert into authors (name) values ('Масамунэ Сиро');

insert into books (name, author_id, genre_id) values ('Евгений Онегин', 1, 2);
insert into books (name, author_id, genre_id) values ('Война и мир', 2, 2);
insert into books (name, author_id, genre_id) values ('Тёмная башня', 3, 3);
insert into books (name, author_id, genre_id) values ('Метро 2033', 4, 2);
insert into books (name, author_id, genre_id) values ('Призрак в доспехах', 5, 1);

insert into comments (text, book_id) values ('Интересно', 1);
insert into comments (text, book_id) values ('Почитать можно', 1);
insert into comments (text, book_id) values ('Увлекательная книга', 2);
insert into comments (text, book_id) values ('Неплохо', 3);

insert into users (username, password, authority) values ('user',
'{bcrypt}$2a$12$biyP5dNn.y2zXv.tTGYp2ecFCnb4aoHXXQjdXMviidIcnfVuitZni', 'ROLE_USER');
insert into users (username, password, authority) values ('admin',
'{bcrypt}$2a$12$biyP5dNn.y2zXv.tTGYp2ecFCnb4aoHXXQjdXMviidIcnfVuitZni', 'ROLE_ADMIN');

-- ACL

INSERT INTO acl_sid (principal, sid) VALUES
(1, 'user'), (1, 'admin');

INSERT INTO acl_class (class) VALUES
('ru.otus.spring.dto.BookDto');

insert into acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
values (1, 1, null, 2, 0),
       (1, 2, null, 2, 0),
       (1, 3, null, 2, 0),
       (1, 4, null, 2, 0),
       (1, 5, null, 2, 0);

insert into acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)
values (1, 2, 1, 1, 1, 1, 1),
       (2, 2, 1, 1, 1, 1, 1),
       (3, 2, 1, 1, 1, 1, 1),
       (1, 1, 2, 1, 1, 1, 1),
       (2, 1, 2, 1, 1, 1, 1),
       (3, 1, 2, 1, 1, 1, 1),
       (4, 1, 2, 1, 1, 1, 1),
       (5, 1, 2, 1, 1, 1, 1);