CREATE TABLE t_photos
(
    id               bigint
        CONSTRAINT t_photos_pkey PRIMARY KEY,
    photo_store_type varchar(255),
    content          oid,
    dir_path         varchar(255),
    file_size        varchar(255) NOT NULL,
    media_type       varchar(255),
    file_name        varchar(255),
    file_extension   varchar(100),
    created_by       varchar(255) NOT NULL,
    created_date     timestamp    NOT NULL,
    modified_by      varchar(255),
    modified_date    timestamp,
    version          int4         NOT NULL
);