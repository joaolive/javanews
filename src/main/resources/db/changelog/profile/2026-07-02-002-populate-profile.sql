--liquibase formatted sql

--changeset joaolive:profile-2026-07-02-002-populate-profile context:test
-- profile 1
INSERT INTO tb_profile (user_id, first_name, last_name, username, bio, avatar_key, created_at, updated_at, version) 
VALUES ('557ddcda-395e-4e40-8855-8edff0ae5627', 'João', 'White', 'joaolive', 'Oi eu sou o João', 'joao.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

-- profile 2
INSERT INTO tb_profile (user_id, first_name, last_name, username, bio, avatar_key, created_at, updated_at, version) 
VALUES ('bdd61f29-2cad-4377-b3e3-2e959dc88f6c', 'Renata', 'Yellow', 'rellow', NULL, 'renata.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

-- profile 3
INSERT INTO tb_profile (user_id, first_name, last_name, username, bio, avatar_key, created_at, updated_at, version) 
VALUES ('1e893828-b3a0-4de1-9dfc-77a685ed9383', 'Jessica', 'Dark', 'jedark', 'Oi eu sou a Jessica', 'jessica.png', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);