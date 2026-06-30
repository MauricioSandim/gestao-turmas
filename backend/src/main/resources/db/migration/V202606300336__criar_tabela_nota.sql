CREATE TABLE nota
(
    id                      BIGSERIAL PRIMARY KEY,
    version                 BIGINT    NOT NULL DEFAULT 0,
    created_at              TIMESTAMP NOT NULL DEFAULT NOW(),
    last_modified_at        TIMESTAMP,
    deleted_at              TIMESTAMP,

    matricula_id            BIGINT    NOT NULL,
    atividade_avaliativa_id BIGINT    NOT NULL,
    nota_final              NUMERIC(5, 2),
    position         INTEGER,

    CONSTRAINT fk_nota_matricula
        FOREIGN KEY (matricula_id)
            REFERENCES matricula (id),

    CONSTRAINT fk_nota_atividade_avaliativa
        FOREIGN KEY (atividade_avaliativa_id)
            REFERENCES atividade_avaliativa (id),

    CONSTRAINT unique_nota
        UNIQUE (matricula_id, atividade_avaliativa_id)
);

CREATE INDEX idx_nota_matricula_id
    ON nota (matricula_id);

CREATE INDEX idx_nota_atividade_avaliativa_id
    ON nota (atividade_avaliativa_id);