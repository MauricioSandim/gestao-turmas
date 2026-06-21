-- Tabela de roles
CREATE TABLE role
(
    id               BIGSERIAL PRIMARY KEY,
    version          BIGINT       NOT NULL DEFAULT 0,
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    last_modified_at TIMESTAMP,
    nome             VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela de usuários
CREATE TABLE usuario
(
    id                      BIGSERIAL PRIMARY KEY,
    version                 BIGINT       NOT NULL DEFAULT 0,
    created_at              TIMESTAMP    NOT NULL DEFAULT NOW(),
    last_modified_at        TIMESTAMP,
    nome                    VARCHAR(100) NOT NULL,
    email                   VARCHAR(255) NOT NULL UNIQUE,
    senha                   VARCHAR(255) NOT NULL,
    role_id                 BIGINT       NOT NULL,

    CONSTRAINT fk_usuario_role FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE INDEX idx_usuario_role_id ON usuario (role_id);

-- Inserção de roles padrão
INSERT INTO role (nome)
VALUES ('ADMIN') ON CONFLICT (nome) DO NOTHING;
INSERT INTO role (nome)
VALUES ('PROFESSOR') ON CONFLICT (nome) DO NOTHING;
INSERT INTO role (nome)
VALUES ('ALUNO') ON CONFLICT (nome) DO NOTHING;

-- Inserção de usuário admin padrão
INSERT INTO usuario (nome, email, senha, role_id)
SELECT '${defaultAdminName}',
       '${defaultAdminEmail}',
       '${adminPasswordHash}',
       r.id
FROM role r
WHERE r.nome = 'ADMIN' ON CONFLICT (email) DO NOTHING;

