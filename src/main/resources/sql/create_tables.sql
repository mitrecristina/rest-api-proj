CREATE TABLE token (
    id      BIGSERIAL   NOT NULL PRIMARY KEY,
    token   CHARACTER   VARYING(50) NOT NULL
);

ALTER TABLE users
ADD CONSTRAINT users_fk01
FOREIGN KEY(id_token) REFERENCES token(id);

alter table token add column expiration_date timestamp without time zone not null;