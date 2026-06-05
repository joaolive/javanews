--liquibase formatted sql

--changeset joaolive:post-2026-06-05-001-add-comments-support
ALTER TABLE tb_post ADD COLUMN parent_id UUID;

ALTER TABLE tb_post ADD CONSTRAINT fk_tb_post_parent_id FOREIGN KEY (parent_id) REFERENCES tb_post(id) ON DELETE CASCADE;

ALTER TABLE tb_post ADD COLUMN type VARCHAR(20) NOT NULL DEFAULT 'ARTICLE';

ALTER TABLE tb_post ALTER COLUMN title DROP NOT NULL;
ALTER TABLE tb_post ALTER COLUMN slug DROP NOT NULL;