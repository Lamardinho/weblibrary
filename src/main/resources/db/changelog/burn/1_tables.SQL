create table t_private_books
(
    id           serial
        constraint t_private_books_pkey
            primary key,
    author       varchar(100) not null,
    date_release date,
    title        varchar(100) not null
);

alter table t_private_books
    owner to postgres;
create unique index t_private_books_title_uindex
    on t_private_books (title);

comment on table t_private_books is 'закрытый каталог';
comment on column t_private_books.author is 'автор';
comment on column t_private_books.title is 'название книги';
comment on column t_private_books.date_release is 'дата выхода';
-------------------------------------------------
create table t_public_books
(
    id           serial
        constraint t_public_books_pkey
            primary key,
    author       varchar(100) not null,
    date_release date,
    title        varchar(100) not null
);

alter table t_public_books
    owner to postgres;
create unique index t_public_books_title_uindex
    on t_public_books (title);

comment on table t_public_books is 'закрытый каталог';
comment on column t_public_books.author is 'автор';
comment on column t_public_books.title is 'название книги';
comment on column t_public_books.date_release is 'дата выхода';
-------------------------------------------------
