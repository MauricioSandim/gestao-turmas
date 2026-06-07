# Regras de Verificação e Análise de Requisitos

## 1. Objetivo

Este documento define os padrões adotados para elaboração e verificação dos requisitos do Sistema de Gerenciamento de Turmas.

Todos os requisitos deverão seguir as regras descritas neste documento antes de serem incluídos no Documento de Requisitos.

---

## Regra 1 – Padronização da Identificação

Todos os requisitos funcionais deverão utilizar o padrão:

RFXXX

Onde:

* RF = Requisito Funcional
* XXX = número sequencial de três dígitos

Exemplos:

* RF001 – Realizar Login
* RF002 – Cadastrar Turma
* RF003 – Consultar Turma

Todos os requisitos não funcionais deverão utilizar o padrão:

RNFXXX

Exemplos:

* RNF001 – Segurança
* RNF002 – Desempenho

---

## Regra 2 – Padronização da Escrita

Todo requisito funcional deverá:

* Iniciar com um verbo no infinitivo;
* Possuir pelo menos um ator associado.

Exemplos válidos:

* Cadastrar Turma
* Consultar Turma
* Registrar Falta

Exemplos inválidos:

* Turma
* Cadastro de Turma
* Faltas

---

## Regra 3 – Completude do Requisito

Todo requisito funcional deverá possuir obrigatoriamente os seguintes campos preenchidos:

* Identificador
* Nome
* Prioridade
* Atores
* Resumo
* Pré-condição
* Fluxo Principal
* Regras de Negócio

Caso algum desses campos esteja ausente, o requisito será considerado incompleto.

---

## Regra 4 – Ausência de Ambiguidade

Não serão permitidos termos subjetivos ou imprecisos nos requisitos.

Exemplos de palavras proibidas:

* rapidamente
* facilmente
* adequadamente
* geralmente
* normalmente

Os requisitos devem descrever comportamentos que possam ser verificados objetivamente durante os testes do sistema.
