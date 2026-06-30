CREATE TABLE falta
(
    id               BIGSERIAL PRIMARY KEY,
    version          BIGINT    NOT NULL DEFAULT 0,
    created_at       TIMESTAMP NOT NULL DEFAULT NOW(),
    last_modified_at TIMESTAMP,
    deleted_at       TIMESTAMP,

    data             DATE      NOT NULL,
    matricula_id     BIGINT    NOT NULL,
    horario_aula_id  BIGINT    NOT NULL,
    position         INTEGER,

    CONSTRAINT fk_falta_matricula
        FOREIGN KEY (matricula_id)
            REFERENCES matricula (id),

    CONSTRAINT fk_falta_horario_aula
        FOREIGN KEY (horario_aula_id)
            REFERENCES horario_aula (id),

    CONSTRAINT unique_falta
        UNIQUE (data, matricula_id, horario_aula_id)
);

CREATE INDEX idx_falta_matricula_id
    ON falta (matricula_id);

CREATE INDEX idx_falta_horario_aula_id
    ON falta (horario_aula_id);

CREATE INDEX idx_falta_data
    ON falta (data);