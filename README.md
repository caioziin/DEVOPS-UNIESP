# 🚀 Desafio DevOps: O Pipeline Inquebrável (Edição Java + SOLID)

## 📌 Contexto
Este projeto inicia como uma aplicação **Go** mal estruturada, com bugs e sem testes adequados. Sua missão é transformar este "legado" em uma solução profissional seguindo os mais altos padrões de engenharia de software.

## 🎯 O Grande Desafio
O objetivo é realizar a migração e implementação completa do pipeline de CI/CD, seguindo as regras abaixo:

### 1. Reescrita para Java
* **Linguagem:** O projeto deve ser totalmente portado para **Java** (sugestão: Spring Boot).
* **Arquitetura:** Deve seguir a **Arquitetura Hexagonal (Ports & Adapters)**.
* **Princípios:** Aplicação rigorosa de **SOLID**.

### 2. Desenvolvimento com TDD
* **Testes Primeiro:** Você deve corrigir os bugs atuais (como o da função `Soma`) e implementar novas funcionalidades utilizando a metodologia **TDD (Test Driven Development)**.
* **Cobertura:** O pipeline só deve avançar se todos os testes passarem.

### 3. Pipeline de CI/CD (GitHub Actions)
Baseado no README original, seu pipeline deve ter:
1. **Etapa de Commit:** Build da imagem Docker e execução de testes unitários.
2. **Etapa de Homologação (Staging):** 
   - Puxar imagem imutável.
   - **Automação de DB:** Executar as migrations (Flyway/Liquibase) automaticamente. (Atenção: A migration inicial está quebrada, corrija-a!).
3. **Etapa de Produção:** Deploy e Smoke Test (validando se o endpoint `/health` retorna HTTP 200).

---

## 📋 Critérios de Avaliação (Atualizado)

| Item | Descrição | Peso |
| :--- | :--- | :--- |
| **Java & Hexagonal** | A migração para Java seguiu a Arquitetura Hexagonal? | 20% |
| **SOLID & TDD** | Os princípios SOLID foram aplicados e os testes guiaram o desenvolvimento? | 20% |
| **Imutabilidade** | A imagem Docker foi construída apenas uma vez no pipeline? | 15% |
| **Automação de DB** | As tabelas foram criadas via Migration corrigidas no pipeline? | 15% |
| **Pipeline & Resiliência** | O pipeline trava em falhas e o grupo corrige o "Commit do Professor"? | 20% |
| **Smoke Test** | Validação real da saúde da aplicação em Produção. | 10% |

---

## 🛠️ Como começar
1. **Explore o código atual (Go):** Identifique os erros de lógica, a falta de padrões e a migração quebrada.
2. **Inicie o projeto Java:** Configure sua estrutura hexagonal.
3. **Configure o Pipeline:** Crie o `.github/workflows/pipeline.yml`.
4. **Resiliência:** Esteja atento ao "Commit Surpresa" que o professor fará para testar sua capacidade de resposta.

> "A qualidade não é um ato, é um hábito."

---
## 👥 Integrantes
- Caio Sousa
- Pedro Davi
- Pedro Henrique
- Chrystian Soares
...
