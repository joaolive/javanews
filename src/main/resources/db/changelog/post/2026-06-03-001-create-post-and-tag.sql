--liquibase formatted sql

--changeset joaolive:post-2026-06-03-001-create-post-and-tag
CREATE TABLE tb_post (
	id UUID NOT NULL,
	title VARCHAR(255) NOT NULL,
	slug VARCHAR(255) NOT NULL,
	body TEXT NOT NULL,
	author_id UUID NOT NULL,
	created_at TIMESTAMP(6),
	updated_at TIMESTAMP(6),

	CONSTRAINT pk_tb_post PRIMARY KEY (id),
	CONSTRAINT uk_tb_post_slug UNIQUE (slug)
);

CREATE TABLE tb_tag (
	id UUID NOT NULL,
	name VARCHAR(32) NOT NULL,
	
	CONSTRAINT pk_tb_tag PRIMARY KEY (id),
	CONSTRAINT uk_tb_tag_name UNIQUE (name)
);

CREATE TABLE tb_post_tag (
	post_id UUID NOT NULL,
	tag_id UUID NOT NULL,

	CONSTRAINT pk_post_tag PRIMARY KEY (post_id, tag_id),
	CONSTRAINT fk_post_tag_post_id FOREIGN KEY (post_id) REFERENCES tb_post(id) ON DELETE CASCADE,
	CONSTRAINT fk_post_tag_tag_id FOREIGN KEY (tag_id) REFERENCES tb_tag(id) ON DELETE CASCADE
);