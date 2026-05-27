--liquibase formatted sql

--changeset joaolive:user-2026-05-27-002-populate-user context:test
INSERT INTO tb_user (id, avatar_key, first_name, last_name, username, email, password, bio, created_at, updated_at) VALUES ('557ddcda-395e-4e40-8855-8edff0ae5627', 'joao.png', 'João', 'White', 'joaolive', 'joao@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'Oi eu sou o João', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO tb_user_role (user_id, role) VALUES ('557ddcda-395e-4e40-8855-8edff0ae5627', 'USER');

INSERT INTO tb_user (id, avatar_key, first_name, last_name, username, email, password, created_at, updated_at) VALUES ('bdd61f29-2cad-4377-b3e3-2e959dc88f6c', 'renata.png', 'Renata', 'Yellow', 'rellow', 'renata@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO tb_user_role (user_id, role) VALUES ('bdd61f29-2cad-4377-b3e3-2e959dc88f6c', 'USER');

INSERT INTO tb_user (id, avatar_key, first_name, last_name, username, email, password, bio, created_at, updated_at) VALUES ('1e893828-b3a0-4de1-9dfc-77a685ed9383', 'jessica.png', 'Jessica', 'Dark', 'jedark', 'jessica@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'Oi eu sou a Jessica', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO tb_user_role (user_id, role) VALUES ('1e893828-b3a0-4de1-9dfc-77a685ed9383', 'USER');
