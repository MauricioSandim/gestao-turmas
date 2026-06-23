# 🚀 Frontend - Gestão de Turmas (React + Vite)

Este é o painel web do sistema de Gestão de Turmas, desenvolvido utilizando **React** com **Vite** para um ambiente de desenvolvimento rápido e otimizado. O projeto consome uma API REST desenvolvida em Spring Boot e gerencia a autenticação via tokens JWT.

## 🛠️ Tecnologias e Dependências Utilizadas

- **Node.js** (Ambiente de execução)
- **React** (Biblioteca para construção da interface)
- **Vite** (Ferramenta de build rápida)
- **React Router Dom** (Gerenciamento de rotas e navegação da SPA)
- **Axios** (Cliente HTTP para integração com o Backend)

---

## 📂 Estrutura de Pastas do Projeto

Abaixo está a organização dos arquivos no diretório `src/` após o refatoramento e isolamento de componentes reutilizáveis:

```text
├── public/                  # Arquivos estáticos (ícones, imagens)
├── src/
│   ├── components/          # Componentes globais e reutilizáveis
│   │   ├── Button.jsx       # Componente de botão estilizado
│   │   └── Input.jsx        # Componente de campo de texto configurável
│   ├── pages/               # Telas/Views principais da aplicação
│   │   ├── Login.jsx        # Tela de autenticação de usuários
│   │   ├── CriarConta.jsx   # Tela de cadastro de novos usuários (Alunos/Professores)
│   │   └── Turma.jsx        # Painel para o CRUD de gerenciamento de turmas
│   ├── services/            # Configurações de serviços externos
│   │   └── api.js           # Instância do Axios configurada com a URL do Backend e interceptor JWT
│   ├── Styles/              # Arquivos de estilização CSS global e local
│   │   └── Styles.css       # Folha de estilos central do app
│   ├── App.jsx              # Configuração das rotas (`<Routes>`) do aplicativo
│   └── main.jsx             # Ponto de entrada do React (renderiza o App no DOM)
├── .gitignore               # Arquivos ignorados pelo Git
├── package.json             # Manifesto do projeto e scripts de execução
├── vite.config.js           # Configuração interna do empacotador Vite
└── README.md                # Documentação do projeto

💻 Pré-requisitos

Antes de iniciar, certifique-se de ter instalado em sua máquina:

    Node.js (Versão 18 ou superior recomendada)

    NPM (Instalado automaticamente junto com o Node) ou Yarn

🔧 Passo a Passo para Execução

Siga os comandos abaixo no terminal dentro da pasta raiz do projeto frontend:
1. Instalar as Dependências

Baixe todos os pacotes necessários listados no package.json (incluindo React Router e Axios):
´´´Bash

npm install

´´´

2. Executar o Projeto em Modo de Desenvolvimento

Inicie o servidor local do Vite:
´´´Bash

npm run dev

´´´

🌐 Portas e Acesso Local

Após rodar o comando npm run dev, o Vite iniciará o servidor instantaneamente.

    Porta Padrão do Frontend (Vite): http://localhost:5173
    (Caso a porta 5173 já esteja ocupada por outro serviço, o Vite mudará automaticamente para 5174, 5175, etc., e informará diretamente no terminal).

    Comunicação com o Backend: O frontend está configurado no arquivo src/services/api.js para enviar todas as requisições HTTP para a porta padrão do Spring Boot:
    http://localhost:8080

    Nota: Certifique-se de que a API do backend (Java/Spring Boot) esteja rodando na porta 8080 para que o fluxo de Login, Registro e gerenciamento de Turmas funcione corretamente.