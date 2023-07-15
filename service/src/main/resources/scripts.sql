CREATE TABLE movie
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(128) NOT NULL,
    director        BIGINT       NOT NULL references actors (id),
    release_date    DATE         NOT NULL,
    country         VARCHAR(128) NOT NULL,
    genre           VARCHAR(128) NOT NULL,
    subscription_id BIGINT       NOT NULL references subscriptions (id)
);

CREATE TABLE actor
(
    id         BIGSERIAL PRIMARY KEY,
    firstname  VARCHAR(128) NOT NULL,
    secondname VARCHAR(128),
    birth_date DATE         NOT NULL,
    biography  VARCHAR(128) NOT NULL
);


CREATE TABLE movie_actor
(
    id       BIGSERIAL PRIMARY KEY,
    movie_id BIGINT NOT NULL REFERENCES movies (id) ON DELETE CASCADE,
    actor_id BIGINT NOT NULL REFERENCES actors (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(128) NOT NULL,
    firstname       VARCHAR(128) NOT NULL,
    secondname      VARCHAR(128),
    password        VARCHAR(128) NOT NULL,
    email           VARCHAR(128) NOT NULL UNIQUE,
    subscription_id BIGINT REFERENCES subscriptions (id)
);

CREATE TABLE subscription
(
    id     BIGSERIAL PRIMARY KEY,
    type   VARCHAR(128) NOT NULL,
    status VARCHAR(128) NOT NULL
);

CREATE TABLE feedback
(
    id       BIGSERIAL PRIMARY KEY,
    movie_id BIGINT      NOT NULL REFERENCES movies (id),
    user_id  BIGINT      NOT NULL REFERENCES users (id),
    comment  VARCHAR(512),
    rating   VARCHAR(10) NOT NULL
);