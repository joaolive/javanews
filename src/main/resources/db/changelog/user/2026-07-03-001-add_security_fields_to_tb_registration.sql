--liquibase formatted sql

--changeset joaolive:user-2026-07-03-001-add_security_fields_to_tb_registration
ALTER TABLE tb_registration 
ADD COLUMN expires_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
ADD COLUMN failed_attempts INT DEFAULT 0 NOT NULL;

ALTER TABLE tb_registration ALTER COLUMN expires_at DROP DEFAULT;
ALTER TABLE tb_registration ALTER COLUMN failed_attempts DROP DEFAULT;