CREATE TABLE t_phones
(
    id               bigint
        CONSTRAINT t_phones_key PRIMARY KEY,
    address_book_id  bigint       NOT NULL
        CONSTRAINT fk_t_address_books_t_phones REFERENCES t_address_books,
    phone_type       varchar(60),
    number           varchar(60),
    created_by       varchar(255) NOT NULL,
    created_date     timestamp    NOT NULL,
    modified_by      varchar(255),
    modified_date    timestamp,
    version          int4         NOT NULL
);