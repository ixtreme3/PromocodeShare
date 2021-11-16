CREATE TABLE shop
(
    id              bigint NOT NULL,
    name            text NOT NULL,
    title           text,
    href            text NOT NULL,
    category        text NOT NULL,

    CONSTRAINT PK_shop PRIMARY KEY ( id )
);