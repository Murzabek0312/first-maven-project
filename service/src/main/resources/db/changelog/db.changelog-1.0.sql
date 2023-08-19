--liquibase-formatted sql

--changeset murzabek:1
CREATE TABLE IF NOT EXISTS actor
(
    id         BIGSERIAL PRIMARY KEY,
    firstname  VARCHAR(128) NOT NULL,
    secondname VARCHAR(128),
    birth_date DATE         NOT NULL,
    biography  VARCHAR(128) NOT NULL
);
--rollback DROP TABLE actor

--changeset murzabek:2
CREATE TABLE IF NOT EXISTS subscription
(
    id     BIGSERIAL PRIMARY KEY,
    type   VARCHAR(128) NOT NULL,
    status VARCHAR(128) NOT NULL
);
--rollback DROP TABLE subscription

--changeset murzabek:3
CREATE TABLE IF NOT EXISTS movie
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(128) NOT NULL,
    director_id     BIGINT       NOT NULL references actor (id),
    release_date    DATE         NOT NULL,
    country         VARCHAR(128) NOT NULL,
    genre           VARCHAR(128) NOT NULL,
    subscription_id BIGINT       NOT NULL references subscription (id)
);
--rollback DROP TABLE movie

--changeset murzabek:4
CREATE TABLE IF NOT EXISTS users
(
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(128) NOT NULL,
    first_name      VARCHAR(128) NOT NULL,
    second_name     VARCHAR(128),
    password        VARCHAR(128) NOT NULL,
    email           VARCHAR(128) NOT NULL UNIQUE,
    subscription_id BIGINT REFERENCES subscription (id)
);
--rollback DROP TABLE users

--changeset murzabek:5
CREATE TABLE IF NOT EXISTS movie_actor
(
    id       BIGSERIAL PRIMARY KEY,
    movie_id BIGINT NOT NULL REFERENCES movie (id) ON DELETE CASCADE,
    actor_id BIGINT NOT NULL REFERENCES actor (id) ON DELETE CASCADE
);
--rollback DROP TABLE movie_actor

--changeset murzabek:6
CREATE TABLE IF NOT EXISTS feedback
(
    id       BIGSERIAL PRIMARY KEY,
    movie_id BIGINT      NOT NULL REFERENCES movie (id),
    user_id  BIGINT      NOT NULL REFERENCES users (id),
    comment  VARCHAR(512),
    rating   VARCHAR(10) NOT NULL
);
--rollback DROP TABLE feedback