--liquibase formatted sql

--changeset joaolive:user-2026-07-02-001-remove-profile-fields
DELETE FROM tb_user_role;
DELETE FROM tb_user;
ALTER TABLE tb_user DROP CONSTRAINT uk_tb_user_username;

ALTER TABLE tb_user
	DROP COLUMN username,
	DROP COLUMN first_name,
	DROP COLUMN last_name,
	DROP COLUMN bio,
	DROP COLUMN avatar_key;
