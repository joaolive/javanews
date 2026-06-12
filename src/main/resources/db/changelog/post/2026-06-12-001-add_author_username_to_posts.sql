--liquibase formatted sql

--changeset joaolive:post-2026-06-12-001-add_author_username_to_posts
ALTER TABLE tb_post ADD COLUMN author VARCHAR(255);

UPDATE tb_post p 
SET author = u.username
FROM tb_user u
WHERE p.author_id = u.id;

ALTER TABLE tb_post
ALTER COLUMN author SET NOT NULL;