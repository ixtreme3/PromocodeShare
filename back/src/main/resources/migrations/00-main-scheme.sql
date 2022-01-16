CREATE TABLE IF NOT EXISTS "user"
(
    id               serial NOT NULL,
    name             text NOT NULL,
    vk_link          text NOT NULL,
    CONSTRAINT PK_user PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS shop
(
    id              serial NOT NULL,
    name            text NOT NULL,
    title           text,
    href            text NOT NULL,
    category        text NOT NULL,
    imageURL        text,

    CONSTRAINT PK_shop PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS coupon
(
    id                  serial NOT NULL,
    shop_id             BIGINT NOT NULL,
    code                text NOT NULL,
    name                text NOT NULL,
    description         text NOT NULL,
    expiration_date     Date NOT NULL,
    user_id             BIGINT,
    is_deleted          BOOLEAN NOT NULL DEFAULT false ,
    is_archive          BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT PK_coupon PRIMARY KEY ( id ),
    CONSTRAINT FK_coupon_shop
        FOREIGN KEY (shop_id)  REFERENCES shop (id) ON DELETE CASCADE,
    CONSTRAINT FK_coupon_user
        FOREIGN KEY (user_id)  REFERENCES "user" (id)
);