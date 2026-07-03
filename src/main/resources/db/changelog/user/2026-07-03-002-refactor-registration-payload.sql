--liquibase formatted sql

--changeset joaolive:user-2026-07-03-002-refactor-registration-payload
ALTER TABLE tb_registration DROP COLUMN password;
ALTER TABLE tb_registration DROP COLUMN username;
ALTER TABLE tb_registration DROP COLUMN first_name;
ALTER TABLE tb_registration DROP COLUMN last_name;

ALTER TABLE tb_registration ADD COLUMN payload JSONB DEFAULT '{}' NOT NULL;
ALTER TABLE tb_registration ALTER COLUMN payload DROP DEFAULT;