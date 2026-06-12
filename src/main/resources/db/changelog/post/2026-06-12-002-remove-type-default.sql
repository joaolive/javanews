--liquibase formatted sql

--changeset joaolive:post-2026-06-12-002-remove-type-default
ALTER TABLE tb_post
ALTER COLUMN type DROP DEFAULT;
