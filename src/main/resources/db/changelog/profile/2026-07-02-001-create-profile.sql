--liquibase formatted sql

--changeset joaolive:profile-2026-07-02-001-create-profile
CREATE TABLE tb_profile (
	user_id UUID NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	bio TEXT,
	avatar_key VARCHAR(255),
	created_at TIMESTAMP(6) NOT NULL,
	updated_at TIMESTAMP(6) NOT NULL,
	version BIGINT NOT NULL DEFAULT 0,

	CONSTRAINT pk_tb_profile PRIMARY KEY (user_id),
	CONSTRAINT uk_tb_profile_username UNIQUE (username),
	CONSTRAINT fk_tb_profile_user_id FOREIGN KEY (user_id) REFERENCES tb_user(id) ON DELETE CASCADE
);