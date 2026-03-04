# simu-invest-api

API REST desenvolvida em **Java + Quarkus** para simulação de investimentos e consulta de histórico de simulações.

Este projeto foi desenvolvido como **desafio técnico backend**, aplicando boas práticas de arquitetura, testes automatizados e cobertura de código.

---

# Tecnologias utilizadas

- Java 21
- Quarkus
- Hibernate ORM + Panache
- SQLite
- Flyway (migrations)
- JUnit 5
- Mockito
- JaCoCo (cobertura de testes)
- Swagger / OpenAPI

---

# Estrutura do projeto

```
src
 ├─ main
 │   ├─ java
 │   │   └─ br.gov.caixa.simuinvest
 │   │        ├─ dto
 │   │        ├─ repository
 │   │        ├─ service
 │   │        ├─ resource
 │   │        ├─ domain
 │   │        │    ├─ entity
 │   │        │    └─ enums
 │   │        └─ exception
 │   │
 │   └─ resources
 │        ├─ application.properties
 │        └─ db.migration
 │
 └─ test
     └─ java
```

### Camadas da aplicação

| Camada | Responsabilidade |
|------|------|
| resource | endpoints REST |
| service | regras de negócio |
| repository | acesso ao banco |
| dto | request / response |
| domain/entity | entidades JPA |

---

# Como executar o projeto

## Pré-requisitos

- Java 17+
- Maven 3.9+

---

# Clonar o projeto

```
git clone <URL_DO_REPOSITORIO>
cd simu-invest-api
```

---

# Rodar a aplicação

Modo desenvolvimento:

```
mvn quarkus:dev
```

A aplicação iniciará em:

```
http://localhost:8080
```

---

# Documentação da API

Swagger UI:

```
http://localhost:8080/q/swagger-ui
```

OpenAPI:

```
http://localhost:8080/q/openapi
```

---

# Banco de dados

O projeto utiliza **SQLite**.

As migrations estão em:

```
src/main/resources/db.migration
```

Exemplo:

```
V1__create_tables.sql
V2__seed_produtos.sql
```

---

# Executar testes

```
mvn test
```

---

# Build completo do projeto

```
mvn clean verify
```

### O que esse comando faz

- **clean** → remove a pasta `target` garantindo um build limpo
- **verify** → compila o projeto, executa todos os testes e valida a cobertura mínima configurada

Caso a cobertura mínima não seja atingida, o build falhará automaticamente.

---

# Relatório de cobertura de testes

Após executar os testes ou o build completo, o relatório JaCoCo será gerado em:

```
target/jacoco-report/index.html
```

Abra esse arquivo no navegador para visualizar a cobertura detalhada de testes.

---

# Autenticação

Alguns endpoints estão protegidos e exigem **token JWT**.

## 1 - Gerar token

Faça a requisição para o endpoint de autenticação (exemplo):

```
POST /auth/login
```

Exemplo de request:

```json
{
  "username": "admin",
  "password": "admin"
}
```

Resposta esperada:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

Copie o token retornado.

---

## 2 - Autorizar no Swagger

1. Acesse:

```
http://localhost:8080/q/swagger-ui
```

2. Clique em **Authorize**

3. Cole o token no formato:

```
Bearer SEU_TOKEN_AQUI
```

4. Clique em **Authorize**

Após isso será possível acessar os endpoints protegidos.

---

# Endpoints principais

## Criar simulação

```
POST secure/simulacoes
```

## Exemplos de criação de simulações

Os exemplos abaixo criam **4 simulações para o mesmo cliente (`clienteId = 101`)**, utilizando diferentes produtos disponíveis.

Endpoint:

```
POST secure/simulacoes
```

Header necessário:

```
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json
```

---

### Simulação 1 — CDB Caixa 2026

```json
{
  "clienteId": 101,
  "tipoProduto": "CDB",
  "valor": 10000,
  "prazoMeses": 12
}
```
---
### Simulação 2 — CDB Turbo 2026

```json
{
  "clienteId": 101,
  "tipoProduto": "CDB_TURBO",
  "valor": 20000,
  "prazoMeses": 18
}
```
---
### Simulação 3 — LCI Premium 2026
```json
{
  "clienteId": 101,
  "tipoProduto": "LCI",
  "valor": 50000,
  "prazoMeses": 24
}
```
---
### Simulação 4 — LCA Agro 2026
```json
{
  "clienteId": 101,
  "tipoProduto": "LCA",
  "valor": 25000,
  "prazoMeses": 18
}
```
### Exemplo de response

```json
{
  "produtoValidado": {
    "id": 4,
    "nome": "LCA Agro 2026",
    "tipoProduto": "LCA",
    "rentabilidadeAnual": 0.11,
    "risco": "BAIXO",
    "prazoMinMeses": 9,
    "prazoMaxMeses": 36,
    "valorMin": 1500,
    "valorMax": 150000
  },
  "valorFinal": 29462.67,
  "prazoMeses": 18,
  "dataSimulacao": "2026-03-04T15:03:38.704Z"
}
```
---
## Consultar histórico de simulações

```
GET secure/simulacoes?clienteId=101
```

Retorna todas as simulações do cliente ordenadas por data.
[
```json
{
"id": 4,
"clienteId": 101,
"produto": "LCA Agro 2026",
"tipoProduto": "LCA",
"valorInvestido": 25000,
"valorFinal": 29462.67,
"prazoMeses": 18,
"dataSimulacao": "2026-03-04T15:03:38.704Z"
},
{
"id": 3,
"clienteId": 101,
"produto": "CDB Turbo 2026",
"tipoProduto": "CDB_TURBO",
"valorInvestido": 20000,
"valorFinal": 24643.59,
"prazoMeses": 18,
"dataSimulacao": "2026-03-04T15:03:26.251Z"
},
{
"id": 2,
"clienteId": 101,
"produto": "CDB Caixa 2026",
"tipoProduto": "CDB",
"valorInvestido": 10000,
"valorFinal": 11268.25,
"prazoMeses": 12,
"dataSimulacao": "2026-03-04T15:03:14.942Z"
},
{
"id": 1,
"clienteId": 101,
"produto": "LCI Premium 2026",
"tipoProduto": "LCI",
"valorInvestido": 50000,
"valorFinal": 61019.55,
"prazoMeses": 24,
"dataSimulacao": "2026-03-04T15:03:00.126Z"
}
]
```
---

# Exemplos usando curl

## 1 - Login (gerar token)

```
curl -X 'GET' \
  'http://localhost:8080/auth/token' \
  -H 'accept: text/plain'
  
  fiz um jwt simples, sem a necessidade de criar usuario
```
---

## 2 - Criar simulação (endpoint protegido)

Substitua `SEU_TOKEN_AQUI` pelo token retornado no login.

```
curl -X POST http://localhost:8080/secure/simulacoes \
-H "Content-Type: application/json" \
-H "Authorization: Bearer SEU_TOKEN_AQUI" \
-d '{
  "clienteId": 101,
  "tipoProduto": "LCA",
  "valor": 25000,
  "prazoMeses": 18
}'
```
---

## 3 - Consultar histórico

```
curl http://localhost:8080/secure/simulacoes?clienteId=101 \
-H "Authorization: Bearer SEU_TOKEN_AQUI"
```

---

# Autor

Projeto do desafio técnico backend utilizando Java + Quarkus, implementando uma API que simula investimentos.