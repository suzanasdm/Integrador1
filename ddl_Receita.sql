CREATE TABLE receita
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    descricao    VARCHAR(255)          NOT NULL,
    valor        DOUBLE                NOT NULL,
    data         datetime              NOT NULL,
    usuario_id   BIGINT                NOT NULL,
    categoria_id BIGINT                NOT NULL,
    CONSTRAINT pk_receita PRIMARY KEY (id)
);

ALTER TABLE receita
    ADD CONSTRAINT FK_RECEITA_ON_CATEGORIA FOREIGN KEY (categoria_id) REFERENCES categoria (id);

ALTER TABLE receita
    ADD CONSTRAINT FK_RECEITA_ON_USUARIO FOREIGN KEY (usuario_id) REFERENCES usuario (id);