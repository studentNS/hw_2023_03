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
    text    varchar(255),
    book_id bigint
);

drop table if exists users;

create table users (
   id          bigint primary key auto_increment,
   username    varchar(255) unique,
   password    varchar(255),
   authority   varchar(255)
);

-- ACL

create sequence acl_sid_seq as bigint;
create table acl_sid (
    id bigint not null default next value for acl_sid_seq primary key,
    principal boolean not null,
    sid varchar(100) not null,
    constraint unique_uk_1 unique(sid,principal)
);

create sequence acl_class_seq as bigint;
create table acl_class (
    id bigint not null default next value for acl_class_seq primary key,
    class varchar(100) not null,
    constraint unique_uk_2 unique(class)
);

create sequence acl_object_identity_seq as bigint;
create table acl_object_identity (
    id bigint not null default next value for acl_object_identity_seq primary key,
    object_id_class bigint not null,
    object_id_identity bigint not null,
    parent_object bigint,
    owner_sid bigint,
    entries_inheriting boolean not null,
    constraint unique_uk_3 unique(object_id_class,object_id_identity),
    constraint foreign_fk_1 foreign key(parent_object)references acl_object_identity(id),
    constraint foreign_fk_2 foreign key(object_id_class)references acl_class(id),
    constraint foreign_fk_3 foreign key(owner_sid)references acl_sid(id)
);

create sequence acl_entry_seq as bigint;
create table acl_entry (
    id bigint not null default next value for acl_entry_seq primary key,
    acl_object_identity bigint not null,
    ace_order int not null,
    sid bigint not null,
    mask integer not null,
    granting boolean not null,
    audit_success boolean not null,
    audit_failure boolean not null,
    constraint unique_uk_4 unique(acl_object_identity,ace_order),
    constraint foreign_fk_4 foreign key(acl_object_identity) references acl_object_identity(id),
    constraint foreign_fk_5 foreign key(sid) references acl_sid(id)
);