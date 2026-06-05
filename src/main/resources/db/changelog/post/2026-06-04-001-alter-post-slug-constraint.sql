--liquibase formatted sql

--changeset joaolive:post-2026-06-04-001-alter-post-slug-constraint
ALTER TABLE tb_post DROP CONSTRAINT uk_tb_post_slug;

ALTER TABLE tb_post ADD CONSTRAINT uk_tb_post_author_slug UNIQUE (author_id, slug);
