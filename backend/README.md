# Gestão Turmar - Backend API

## Sobre o projeto

Este repositorio contem a API backend do Gestão de Turmar.

## Tecnologias

- Java 21
- Spring Boot 4.0.4
- Maven
- PostgreSQL
- Flyway
- Spring Security + JWT
- SpringDoc OpenAPI (Swagger)
- Docker e Docker Compose
- Lombok e MapStruct

## Pre-requisitos

- Docker e Docker Compose
- Git
- Opcional: Java 21 e Maven para rodar fora do Docker

## Configuracao

1. Copie o arquivo de exemplo:

```bash
cp env.example .env
```

2. Ajuste as variaveis no `.env` conforme ambiente local.

Variaveis principais:

- `SERVER_PORT`
- `SPRING_DATASOURCE_HOST`
- `SPRING_DATASOURCE_PORT`
- `SPRING_DATASOURCE_DATABASE`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `API_SECURITY_JWT_SECRET`
- `API_FRONTEND_URL`

## Executando com Docker

```bash
docker compose up -d --build
```

Servicos esperados:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## Executando localmente (sem Docker para a API)

Suba apenas os servicos de suporte:

```bash
docker compose up -d db
```

Depois execute a aplicacao pela IDE ou Maven.

## Estrutura base

```text
src/
  main/
    java/
      br/ufla/gedai/backend/
        configuration/
        controller/
        exception/
        filter/
        mapper/
        model/
        repository/
        service/
        GestaoTurmasApplication.java
    resources/
      application.yml
      db/migration/
```

## Seguranca

- Rotas administrativas protegidas por JWT
- Controle de perfis via roles (`ADMIN`, `PROFESSOR`, `ALUNO`)
- Recuperacao de senha por token

## Documentacao da API

- Swagger UI: `http://localhost:8080/swagger-ui.html`

## Observacoes

- O projeto segue arquitetura em camadas (controller, service, repository).
