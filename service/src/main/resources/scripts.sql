CREATE TABLE movies
(
    id              BIGSERIAL PRIMARY KEY,
    name            varchar(128) NOT NULL,
    director        BIGINT       NOT NULL references actors (id),
    date_to_release DATE         NOT NULL,
    country         varchar(128) NOT NULL,
    genre           varchar(128) NOT NULL,
    subscription_id BIGINT       NOT NULL references subscriptions (id)
);

CREATE TABLE actors
(
    id         BIGSERIAL PRIMARY KEY,
    firstname  varchar(128) NOT NULL,
    secondname varchar(128),
    birth_date DATE         NOT NULL,
    biography  varchar(128) NOT NULL
);


CREATE TABLE movies_actors
(
    id       BIGSERIAL PRIMARY KEY,
    movie_id BIGINT NOT NULL references movies (id),
    actor_id BIGINT NOT NULL references actors (id)
);

CREATE TABLE users
(
    id              BIGSERIAL PRIMARY KEY,
    username        varchar(128) NOT NULL,
    firstname      varchar(128) NOT NULL,
    secondname     varchar(128),
    password        varchar(128) NOT NULL,
    email           varchar(128) NOT NULL,
    subscription_id BIGINT       NOT NULL references subscriptions (id)
);

CREATE TABLE subscriptions
(
    id     BIGSERIAL PRIMARY KEY,
    type   varchar(128) NOT NULL,
    status varchar(128) NOT NULL
);

CREATE TABLE feedbacks
(
    id       BIGSERIAL PRIMARY KEY,
    movie_id BIGINT      NOT NULL references movies (id),
    user_id  BIGINT      NOT NULL references users (id),
    comment  varchar(512),
    rating   varchar(10) NOT NULL
);