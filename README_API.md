# API REST - Sistema de Gerenciamento de Elencos

Este documento descreve a API REST do sistema de gerenciamento de elencos e transferências de jogadores.

## Base URL
```
http://localhost:8080
```

## Endpoints Disponíveis

### Jogadores

#### Listar Jogadores
```http
GET /jogadores
```
**Resposta:**
```json
[
  {
    "nomeJogador": "string",
    "posicao": "string",
    "valorMercado": number,
    "timeAtual": {
      "nomeTime": "string",
      "saldoCaixa": number,
      "jogadores": []
    },
    "agente": {
      "nomeAgente": "string",
      "jogadoresAgenciados": []
    },
    "contrato": {
      "inicio": "yyyy-MM-dd",
      "fim": "yyyy-MM-dd",
      "multaRescisoria": number,
      "clausulas": "string"
    }
  }
]
```

### Times

#### Listar Times
```http
GET /times
```
**Resposta:**
```json
[
  {
    "nomeTime": "string",
    "saldoCaixa": number,
    "jogadores": []
  }
]
```

#### Cadastrar Time
```http
POST /times
Content-Type: application/json

{
  "nomeTime": "Nome do Time",
  "saldoCaixa": 50000000.0
}
```

### Agentes

#### Listar Agentes
```http
GET /agentes
```
**Resposta:**
```json
[
  {
    "nomeAgente": "string",
    "jogadoresAgenciados": []
  }
]
```

#### Cadastrar Agente
```http
POST /agentes
Content-Type: application/json

{
  "nomeAgente": "Nome do Agente"
}
```

### Transferências

#### Listar Transferências
```http
GET /transferencias
```
**Resposta:**
```json
[
  {
    "jogador": {},
    "timeOrigem": {},
    "timeDestino": {},
    "multaRescisoria": number,
    "luvas": number,
    "valor": number,
    "data": "yyyy-MM-dd",
    "comissaoAgente": number
  }
]
```

## Exemplos de Uso

### Cadastrar um Time
```bash
curl -X POST http://localhost:8080/times \
  -H "Content-Type: application/json" \
  -d '{"nomeTime":"Flamengo","saldoCaixa":50000000.0}'
```

### Cadastrar um Agente
```bash
curl -X POST http://localhost:8080/agentes \
  -H "Content-Type: application/json" \
  -d '{"nomeAgente":"Jorge Mendes"}'
```

### Listar Todos os Times
```bash
curl -X GET http://localhost:8080/times
```

### Listar Todos os Agentes
```bash
curl -X GET http://localhost:8080/agentes
```

### Listar Todas as Transferências
```bash
curl -X GET http://localhost:8080/transferencias
```

## Como Executar

1. Certifique-se de ter Java 11+ e Maven instalados
2. Clone o repositório
3. Execute: `mvn spring-boot:run`
4. A API estará disponível em `http://localhost:8080`

## Status da Implementação

✅ **Fase 1: Backend (API REST com Java) - CONCLUÍDA**
- ✅ Estruturar projeto com Spring Boot
- ✅ Migrar lógica de negócio para um `@Service`
- ✅ Criar `Controllers` para expor a lógica como API REST

📋 **Próximos Passos:**
- **Fase 2: Frontend (Interface Web com Next.js)**
- **Fase 3: Hospedagem e Deploy**