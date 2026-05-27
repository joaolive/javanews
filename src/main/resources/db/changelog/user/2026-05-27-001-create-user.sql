--liquibase formatted sql

--changeset joaolive:user-2026-05-27-001-create-user
CREATE TABLE tb_user (
	id UUID NOT NULL,
	email VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	bio TEXT,
	avatar_key VARCHAR(255),
	created_at TIMESTAMP(6),
	updated_at TIMESTAMP(6),

	CONSTRAINT pk_tb_user PRIMARY KEY (id),
	CONSTRAINT uk_tb_user_email UNIQUE (email),
	CONSTRAINT uk_tb_user_username UNIQUE (username)
);

CREATE TABLE tb_user_role (
	user_id UUID NOT NULL,
	role VARCHAR (255) NOT NULL,

	CONSTRAINT fk_tb_user_role_user_id FOREIGN KEY (user_id) REFERENCES tb_user(id)
);