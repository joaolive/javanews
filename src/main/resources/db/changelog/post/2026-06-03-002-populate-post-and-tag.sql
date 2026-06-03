--liquibase formatted sql

--changeset joaolive:post-2026-06-03-002-populate-post-and-user context:test
INSERT INTO tb_tag(id, name) VALUES('9b0d318f-2efd-45ca-a088-3ebde901054d', 'java');
INSERT INTO tb_tag(id, name) VALUES('d2c99f4c-7ab4-40d3-b7cf-19d1a1867762', 'spring-boot');
INSERT INTO tb_tag(id, name) VALUES('1abf3ab7-d798-4596-8141-31e2f5045b92', 'c');

INSERT INTO tb_post (
    id,
    title,
    slug,
    body,
    author_id,
    created_at,
    updated_at
) VALUES (
    '7a9c42d9-bec9-4a4e-8285-2cd87fc9ad2a',
    'Javanews - O coisa ruim voltou',
    'javanews-o-coisa-ruim-voltou',
    '![o coisa ruim](https://i.imgur.com/5sTMHQK.jpeg)

Salve galera!

Depois de passar um tempo estudando padrões de projeto, arquiteturas, SOLID, DDD, KISS e mais um monte de siglas que os desenvolvedores gostam de inventar, comecei a coringar e tomei uma decisão questionável:

**Recriar o backend do TabNews em Java.**

Sim.

Isso mesmo.

Java.

Achei que seria só mais uma daquelas vontades repentinas de criar uma APIzinha num fim de semana para abandonar depois de três commits e uma documentação pela metade.

Mas aí o trem saiu do controle.

Quando percebi, já estava desenhando entidades, pensando em módulos, discutindo comigo mesmo se aquilo era um Aggregate ou não, e gastando mais tempo organizando pastas do que escrevendo regra de negócio.

Foi nesse momento que nasceu o JavaNews.',
    '557ddcda-395e-4e40-8855-8edff0ae5627',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO tb_post_tag(post_id, tag_id) VALUES('7a9c42d9-bec9-4a4e-8285-2cd87fc9ad2a', '9b0d318f-2efd-45ca-a088-3ebde901054d');
INSERT INTO tb_post_tag(post_id, tag_id) VALUES('7a9c42d9-bec9-4a4e-8285-2cd87fc9ad2a', 'd2c99f4c-7ab4-40d3-b7cf-19d1a1867762');