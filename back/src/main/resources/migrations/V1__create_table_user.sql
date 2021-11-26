CREATE TABLE user
(
    id               bigint NOT NULL,
    name             text NOT NULL,
    vk_link          text NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY ( id )
);