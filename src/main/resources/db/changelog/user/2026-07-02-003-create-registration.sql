--liquibase formatted sql

--changeset joaolive:user-2026-07-02-003-create-registration
CREATE TABLE tb_registration (
	id UUID NOT NULL,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	verification_code VARCHAR(6) NOT NULL,
	status VARCHAR(20) NOT NULL,
	created_at TIMESTAMP(6) NOT NULL,

	CONSTRAINT pk_tb_registration PRIMARY KEY (id),
	CONSTRAINT uk_tb_registration_email UNIQUE (email),
	CONSTRAINT uk_tb_registration_username UNIQUE (username)
);