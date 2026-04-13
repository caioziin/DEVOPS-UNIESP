# 🚀 Desafio de Engenharia de Software: O Pipeline Inquebrável

Bem-vindos ao desafio prático de **CI/CD e DevOps**. Este repositório serve como base para a implementação de um fluxo de entrega contínua que segue os padrões mais rigorosos de automação, imutabilidade e resiliência.

## 🎯 O Objetivo
Sua equipe deve configurar um pipeline automatizado que garanta que o código só chegue ao cliente final (Produção) se for aprovado em todas as etapas de segurança e qualidade.

---

## 🛠️ Requisitos do Pipeline

O pipeline deve ser dividido em **três etapas visíveis e sequenciais**:

### 1. Etapa de Commit (O Guardião)
* **Gatilho:** Automático a cada `push`.
* **Ações:** * Executar testes unitários básicos.
    * **Build de Imagem Docker:** Criar a imagem da aplicação.
    * **Armazenamento:** Enviar a imagem para um registro (GHCR ou Docker Hub).
* **Regra:** Se o teste falhar ou o build quebrar, o pipeline deve **falhar rápido** e bloquear as próximas etapas.

### 2. Etapa de Homologação (Staging)
* **Dependência:** Sucesso total da Etapa 1.
* **Ações:** * **Imutabilidade:** Puxar a imagem Docker criada na etapa anterior (proibido fazer novo build).
    * **Banco de Dados:** Executar automaticamente scripts de **Migration** para criar/alterar tabelas (proibido alteração manual via SGDB).
    * Subir o ambiente para testes de aceitação.

### 3. Etapa de Produção
* **Dependência:** Sucesso total da Etapa 2.
* **Ações:** * Deploy da mesma imagem validada em Staging.
    * **Smoke Test (Teste de Fumaça):** O pipeline deve validar se a URL de produção está respondendo com sucesso (HTTP 200).

---

## ⚠️ O Fator Caos (Commit do Professor)
Durante o período da atividade, farei um **commit surpresa** propositalmente quebrado no repositório. 
* **Sua missão:** Monitorar as Actions, identificar por que o pipeline travou e realizar o "Hotfix" (correção) para restaurar o fluxo de entrega.

---

## 📋 Critérios de Avaliação

| Item | Descrição | Peso |
| :--- | :--- | :--- |
| **Imutabilidade** | A imagem Docker foi construída apenas uma vez? | 25% |
| **Automação de DB** | As tabelas foram criadas via Migration no pipeline? | 25% |
| **Resiliência** | O grupo corrigiu o commit surpresa do professor? | 20% |
| **Qualidade** | O Smoke Test valida a saúde da aplicação? | 15% |
| **Segurança** | Uso correto de GitHub Secrets para senhas e tokens. | 15% |

---

## 📖 Como Entregar
1. Realize o fork/clone deste repositório.
2. Implemente o arquivo `.github/workflows/pipeline.yml`.
3. Certifique-se de que o README do seu grupo contenha os nomes dos integrantes e o link para a aplicação rodando (se aplicável).

> "Software funcionando é a medida primária de progresso." — Manifesto Ágil
