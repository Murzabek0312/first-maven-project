--liquibase-formatted sql

--changeset murzabek:1
ALTER TABLE users
    ALTER COLUMN password SET DEFAULT '{noop}123';
--rollback ALTER TABLE users ALTER COLUMN password DROP DEFAULT

--changeset murzabek:2
ALTER TABLE users
    ADD COLUMN role VARCHAR(12) DEFAULT 'USER'
--rollback ALTER TABLE users DROP COLUMN role