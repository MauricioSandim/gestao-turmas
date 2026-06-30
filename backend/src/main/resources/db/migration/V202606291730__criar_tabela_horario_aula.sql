CREATE TABLE horario_aula
(
    id               BIGSERIAL PRIMARY KEY,
    version          BIGINT      NOT NULL DEFAULT 0,
    created_at       TIMESTAMP   NOT NULL DEFAULT NOW(),
    last_modified_at TIMESTAMP,
    deleted_at       TIMESTAMP,

    hora             TIME        NOT NULL,
    turma_id         BIGINT      NOT NULL,
    dia_semana       VARCHAR(25) NOT NULL,
    position         INTEGER,

    CONSTRAINT chk_horario_aula_dia_semana
        CHECK (dia_semana IN (
                              'SEGUNDA',
                              'TERCA',
                              'QUARTA',
                              'QUINTA',
                              'SEXTA',
                              'SABADO',
                              'DOMINGO'
            )),

    CONSTRAINT fk_horario_aula_turma
        FOREIGN KEY (turma_id)
            REFERENCES turma (id),

    CONSTRAINT unique_horario_turma
        UNIQUE (turma_id, hora, dia_semana)
);

CREATE INDEX idx_horario_aula_turma_id
    ON horario_aula (turma_id);