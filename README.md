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

1. Acesse a aplicação pelo endereço disponibilizado pela equipe responsável.
2. Faça login utilizando suas credenciais.
3. Caso ainda não possua uma conta, realize o cadastro pela tela inicial.
4. Após a autenticação, utilize as funcionalidades disponíveis de acordo com seu perfil (Administrador, Professor ou Aluno).

Em caso de problemas de acesso, entre em contato com o administrador do sistema.


---

# Instruções para Desenvolvedores

☕ 1. Configuração do BackEnd (Spring Boot + Docker)

O backend utiliza Docker para orquestrar a API e o banco de dados de forma automatizada.

Passo 1: Configurar as Variáveis de Ambiente

Na pasta raiz do BackEnd, crie o arquivo .env a partir do modelo existente:
```Bash
cp env.example .env
```

Abra o arquivo .env recém-criado e ajuste os valores conforme as necessidades do seu ambiente local. As principais variáveis são:

    SERVER_PORT: Porta onde a API vai rodar (padrão: 8080).

    SPRING_DATASOURCE_HOST: Host do banco de dados.

    SPRING_DATASOURCE_PORT: Porta do banco de dados.

    SPRING_DATASOURCE_DATABASE: Nome do banco de dados.

    SPRING_DATASOURCE_USERNAME: Usuário do banco.

    SPRING_DATASOURCE_PASSWORD: Senha do banco.

    API_SECURITY_JWT_SECRET: Chave secreta para assinatura dos tokens JWT.

    API_FRONTEND_URL: URL correspondente ao FrontEnd (ex: http://localhost:5173).

Passo 2: Inicializar os Serviços via Docker

Com o arquivo .env configurado, execute o comando abaixo para buildar e iniciar os containers em segundo plano (-d):
```Bash
docker compose up -d --build
```

* Serviços e Acessos do BackEnd

Após a inicialização do Docker, você poderá acessar:

    API Principal: http://localhost:8080

    Documentação (Swagger UI): http://localhost:8080/swagger-ui.html

2. Configuração do FrontEnd (React + Vite)

O frontend é construído com React, utilizando Vite como bundler, além de React Router para rotas e Axios para consumo de APIs.

Passo 1: Instalar as Dependências

Abra o terminal dentro da pasta raiz do FrontEnd e baixe todos os pacotes necessários listados no package.json:
```Bash
npm install
```

Passo 2: Executar em Modo de Desenvolvimento

Inicie o servidor local do Vite com o comando:
```Bash
npm run dev
```

* Serviços e Acessos do BackEnd

Após a inicialização o projeto, você poderá acessar:

    localhost Principal: http://localhost:5173

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
| React      | 19.2.7           |
| Vite       | 8.0.4            |
| Node.js    | 22.12.0          |

### Principais Responsabilidades do Frontend

* Interface do usuário;
* Comunicação com a API;
* Gerenciamento de estados da aplicação;
* Exibição e manipulação de dados acadêmicos;
* Experiência de uso para professores e alunos.

---

# Organização do Projeto

## Estrutura de Pastas

```
.
├── backend
│   ├── *
│   └── README.md
├── docs
│   ├── diagramas
│   │   ├── diagrama-casos-de-uso.svg
│   │   ├── diagrama-classes.svg
│   │   ├── Diagrama_de_Implantacao.svg
│   │   ├── diagrama-pacotes-backend.svg
│   │   ├── diagrama-pacotes-frontend.svg
│   │   ├── DS_Atualizar_Turma.svg
│   │   ├── DS_Consultar_Nota.svg
│   │   ├── DS_Consultar_Turma.svg
│   │   ├── DS-Criar_Nota.svg
│   │   ├── DS_Criar_Turma.svg
│   │   ├── DS_Deletar_Nota1.svg
│   │   ├── DS_Deletar_Nota.svg
│   │   ├── DS_Deletar_Turma.svg
│   │   └── editaveis
│   │       ├── diagrama-casos-de-uso.drawio
│   │       ├── diagrama-classes.drawio
│   │       ├── Diagrama_de_Implantacao.drawio
│   │       ├── diagrama-pacotes-backend.drawio
│   │       ├── diagrama-pacotes-frontend.drawio
│   │       └── diagramasDeSequencia.drawio
│   ├── documento_requisitos
│   │   ├── documento_requisitos.docx
│   │   └── documento_requisitos.pdf
│   ├── padroes_adotados
│   │   ├── boas_praticas.md
│   │   └── regras_de_verificacao_e_analise_de_requisitos.md
│   └── README.md
├── frontend
|   ├── *
│   └── README.md
└── README.md
```

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
