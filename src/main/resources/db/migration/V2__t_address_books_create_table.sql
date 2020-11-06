CREATE TABLE t_address_books
(
    id            bigint
        CONSTRAINT t_address_books_pkey PRIMARY KEY,
    first_name    varchar(255),
    last_name     varchar(255),
    email         varchar(255),
    photo_id      bigint
        CONSTRAINT fk_t_photos_t_address_books REFERENCES t_photos,
    created_by    varchar(255) NOT NULL,
    created_date  timestamp    NOT NULL,
    modified_by   varchar(255),
    modified_date timestamp,
    version       int4         NOT NULL
);