# Sistema de Gestão Acadêmica de Turmas

## Sobre o Projeto

### Contexto do Problema

Embora a transformação digital tenha avançado significativamente em diversos setores da sociedade, muitas instituições de ensino ainda enfrentam desafios relacionados à gestão acadêmica de turmas. Em diversos contextos educacionais, informações importantes continuam sendo registradas por meio de planilhas dispersas, documentos físicos ou ferramentas genéricas que não foram desenvolvidas especificamente para atender às necessidades do ambiente escolar.

Essa realidade gera uma série de dificuldades, como:

* Retrabalho na gestão de informações acadêmicas;
* Dificuldade no acompanhamento do desempenho dos alunos;
* Controle ineficiente de faltas e frequência;
* Centralização inadequada de atividades e avaliações;
* Risco de perda ou inconsistência de dados;
* Maior tempo gasto pelos professores em tarefas administrativas.

Além disso, a utilização de múltiplas ferramentas desconectadas compromete a produtividade dos professores e dificulta o acesso dos alunos às informações relacionadas ao seu desempenho acadêmico.

### Solução Proposta

O Sistema de Gestão Acadêmica de Turmas foi concebido para centralizar e simplificar os processos de acompanhamento acadêmico em um único ambiente digital.

A plataforma permitirá que professores realizem o gerenciamento completo de suas turmas, incluindo:

* Cadastro e gerenciamento de turmas;
* Cadastro e acompanhamento de alunos;
* Registro de frequência e controle de faltas;
* Cadastro e gerenciamento de atividades avaliativas;
* Lançamento e consulta de notas;
* Acompanhamento do desempenho acadêmico;
* Consulta de informações acadêmicas pelos alunos;
* Organização das informações em um ambiente único e seguro.

Com essa solução, espera-se reduzir o tempo gasto com atividades administrativas, aumentar a confiabilidade das informações acadêmicas e proporcionar uma experiência mais eficiente para professores e alunos.

---

# Objetivos do Sistema

* Centralizar as informações acadêmicas de uma turma.
* Facilitar o trabalho administrativo dos professores.
* Melhorar o acompanhamento do desempenho dos alunos.
* Reduzir erros e inconsistências em registros acadêmicos.
* Disponibilizar informações de forma rápida e organizada.
* Promover maior transparência entre professores e alunos.

---

# Instruções para Uso

> **Preencher durante o desenvolvimento**

---

# Instruções para Desenvolvedores

> **Preencher durante o desenvolvimento**

---

# Padrão de Uso do Git

## Commits

Utilizar obrigatoriamente um dos prefixos abaixo:

```text
feat:     Nova funcionalidade
fix:      Correção de erro
docs:     Documentação
refactor: Refatoração sem alteração funcional
tests:    Criação ou ajuste de testes
```

### Exemplos

```text
feat: adicionar filtro por município
fix: corrigir validação de data
refactor: otimizar consulta SQL
```
---

## Branches

### Funcionalidades

```text
feat/backend/<nome-funcionalidade>
feat/frontend/<nome-funcionalidade>
```

### Correções

```text
fix/backend/<nome-correcao>
fix/frontend/<nome-correcao>
```

### Exemplos

```text
feat/backend/consulta-gta
feat/frontend/dashboard
fix/backend/erro-data
```

---

## Pull Requests

### Regras

* Todo merge deve ser realizado via Pull Request.
* Não realizar merge direto na branch principal.

### Estrutura

**Título**

```text
<tipo>: <descrição>
```

Exemplo:

```text
feat: adicionar consulta por município
```

**Conteúdo**

```text
Regra de Negócio:
<identificador ou Não se aplica>

Tipo:
feat | fix | refactor

Descrição:
- Alteração 1
- Alteração 2
- Alteração 3
```

---

# Tecnologias Utilizadas

## Backend

| Tecnologia                  | Versão                            |
| --------------------------- | --------------------------------- |
| Java                        | 21                                |
| Spring Boot                 | 4.0.6                             |
| Maven                       | Última compatível                 |
| PostgreSQL                  | 16                                |
| Flyway                      | Controle de migrações             |
| Spring Security             | Autenticação e autorização        |
| JWT                         | Autenticação baseada em tokens    |
| SpringDoc OpenAPI (Swagger) | Documentação da API               |
| Docker                      | Containerização                   |
| Docker Compose              | Orquestração de containers        |
| Lombok                      | Redução de código boilerplate     |
| MapStruct                   | Mapeamento entre DTOs e entidades |

### Principais Responsabilidades do Backend

* Disponibilização da API REST;
* Gerenciamento de autenticação e autorização;
* Regras de negócio do sistema;
* Persistência de dados;
* Controle de migrações do banco de dados;
* Documentação automática da API.

---

## Frontend

| Tecnologia | Versão           |
| ---------- | ---------------- |
| React      | 19.2.6           |
| React-Dom  | 19.2.6           |
| Vite       | 8.0.12           |
| Node.js    | 22.13.0          |

### Principais Responsabilidades do Frontend

* Interface do usuário;
* Comunicação com a API;
* Gerenciamento de estados da aplicação;
* Exibição e manipulação de dados acadêmicos;
* Experiência de uso para professores e alunos.

---

# Organização do Projeto

## Estrutura de Pastas

> **Preencher durante o desenvolvimento**

---

# Segurança

O sistema utilizará mecanismos modernos de autenticação e autorização para proteger os dados acadêmicos dos usuários.

Principais recursos previstos:

* Autenticação via JWT;
* Controle de acesso baseado em perfis;
* Criptografia de senhas;
* Proteção de endpoints da API;
* Validação de dados de entrada.

---

# Documentação da API

A API REST será documentada automaticamente através do Swagger/OpenAPI.

> **URL a ser preenchida durante o desenvolvimento**

---

# Qualidade de Software

O projeto seguirá boas práticas de desenvolvimento, incluindo:

* Arquitetura em camadas;
* Controle de versão com Git;
* Versionamento de banco de dados com Flyway;
* Padronização de código;
* Documentação técnica;
* Testes automatizados;
* Revisão de código.

---

# Status do Projeto

Em desenvolvimento.

---

# Equipe de Desenvolvimento

* Erick David Forzan Pereira
* Livia Maria Almeida Silva
* Mauricio Vicente Sandim

---
