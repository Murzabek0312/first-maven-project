--liquibase-formatted sql

--changeset murzabek:1
ALTER TABLE users
    ADD COLUMN image VARCHAR(64);
--rollback ALTER TABLE users DROP COLUMN image