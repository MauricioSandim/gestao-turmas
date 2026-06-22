CREATE TABLE turma
(
    id               BIGSERIAL PRIMARY KEY,
    version          BIGINT       NOT NULL DEFAULT 0,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    last_modified_at TIMESTAMP,
    nome             VARCHAR(100) NOT NULL,
    usuario_id       BIGINT       NOT NULL,

    CONSTRAINT fk_turma_usuario FOREIGN KEY (usuario_id) REFERENCES usuario (id),
    CONSTRAINT unique_usuario_nome UNIQUE (usuario_id, nome)
);

CREATE INDEX idx_turma_usuario_id ON turma (usuario_id);
CREATE INDEX idx_turma_usuario_id_id ON turma (usuario_id, id);
CREATE INDEX idx_turma_usuario_id_nome ON turma (usuario_id, nome);
