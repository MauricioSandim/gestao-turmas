CREATE TABLE atividade_avaliativa
(
    id               BIGSERIAL PRIMARY KEY,
    version          BIGINT       NOT NULL DEFAULT 0,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    last_modified_at TIMESTAMP,
    deleted_at       TIMESTAMP,

    nome             VARCHAR(255) NOT NULL,
    nota             NUMERIC(5, 2),
    turma_id         BIGINT       NOT NULL,
    position         INTEGER,

    CONSTRAINT fk_atividade_avaliativa_turma
        FOREIGN KEY (turma_id)
            REFERENCES turma (id)
);

CREATE INDEX idx_atividade_avaliativa_turma_id
    ON atividade_avaliativa (turma_id);

CREATE INDEX idx_atividade_avaliativa_position
    ON atividade_avaliativa (position);