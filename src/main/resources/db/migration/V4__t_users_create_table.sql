CREATE TABLE t_users
(
    username varchar(255)
        CONSTRAINT t_users_key PRIMARY KEY,
    password varchar(255) NOT NULL,
    role     varchar(100) NOT NULL
);