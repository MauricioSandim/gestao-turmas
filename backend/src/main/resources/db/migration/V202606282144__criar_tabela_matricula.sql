CREATE TABLE matricula
(
    id               BIGSERIAL PRIMARY KEY,
    version          BIGINT    NOT NULL DEFAULT 0,
    created_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    last_modified_at TIMESTAMP,
    aluno_id         BIGINT    NOT NULL,
    turma_id         BIGINT    NOT NULL,

    CONSTRAINT fk_matricula_aluno FOREIGN KEY (aluno_id) REFERENCES usuario (id),
    CONSTRAINT fk_matricula_turma FOREIGN KEY (turma_id) REFERENCES turma (id),
    CONSTRAINT unique_aluno_turma UNIQUE (aluno_id, turma_id)
);

CREATE INDEX idx_matricula_aluno_id ON matricula (aluno_id);
CREATE INDEX idx_matricula_turma_id ON matricula (turma_id);
CREATE INDEX idx_matricula_aluno_id_turma_id ON matricula (aluno_id, turma_id);

