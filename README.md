# Trabalho-LP2

Trabalho final de LP2

Este é o trabalho final de LP2, o tema será um sistema gerenciador de elencos com transferências de jogadores do plantel de diversos times.

## Diagrama de Classes

::: mermaid

    classDiagram
    class Jogador {
        - String nomeJogador
        - String posicao
        - double valorMercado
        - Time timeAtual
        - Agente agente
        - Contrato contrato
        + String getNomeJogador()
        + void setNomeJogador(String)
        + String getPosicao()
        + void setPosicao(String)
        + double getValorMercado()
        + void setValorMercado(double)
        + Time getTimeAtual()
        + void setTimeAtual(Time)
        + Agente getAgente()
        + void setAgente(Agente)
        + Contrato getContrato()
        + void setContrato(Contrato)
        + void removerContrato()
        + void removerAgente()
        + void removerTimeAtual()
        + void associarTime(Time)
        + void associarAgente(Agente)
        + void associarContrato(Contrato)
        + boolean isFreeAgent()
    }
    
    class Time {
        - String nomeTime
        - double saldoCaixa
        - List~Jogador~ jogadores
        + String getNomeTime()
        + void setNomeTime(String)
        + double getSaldoCaixa()
        + void setSaldoCaixa(double)
        + List~Jogador~ getJogadores()
        + void setJogadores(List~Jogador~)
        + void removerJogadorDoTime(Jogador)
        + void adicionarJogador(Jogador)
        + boolean possuiJogador(String)
    }

    class Transferencia {
        - Jogador jogador
        - Time timeOrigem
        - Time timeDestino
        - double multaRescisoria
        - double luvas
        - double valor
        - LocalDate data
        - double comissaoAgente
        + Jogador getJogador()
        + void setJogador(Jogador)
        + Time getTimeOrigem()
        + void setTimeOrigem(Time)
        + Time getTimeDestino()
        + void setTimeDestino(Time)
        + double getMultaRescisoria()
        + void setMultaRescisoria(double)
        + double getLuvas()
        + void setLuvas(double)
        + double getValor()
        + void setValor(double)
        + LocalDate getData()
        + void setData(LocalDate)
        + double getComissaoAgente()
        + void setComissaoAgente(double)
    }

    class BID {
        - List~Transferencia~ transferencias
        + void registrarTransferencia(Transferencia)
        + void gerarRelatorio()
        + boolean removerTransferencia(Transferencia)
        + List~Transferencia~ getTransferencias()
        + void setTransferencias(List~Transferencia~)
    }

    class Agente {
        - String nomeAgente
        - List~Jogador~ jogadoresAgenciados
        + double calcularComissao(double)
        + String getNomeAgente()
        + void setNomeAgente(String)
        + List~Jogador~ getJogadoresAgenciados()
        + void setJogadoresAgenciados(List~Jogador~)
        + void removerJogadorAgenciado(Jogador)
        + void adicionarJogadorAgenciado(Jogador)
        + boolean possuiJogador(String)
        + boolean temJogadores()
    }

    class Contrato {
        - Jogador jogador
        - Time time
        - LocalDate inicio
        - LocalDate fim
        - double multaRescisoria
        - String clausulas
        + Jogador getJogador()
        + void setJogador(Jogador)
        + Time getTime()
        + void setTime(Time)
        + LocalDate getInicio()
        + void setInicio(LocalDate)
        + LocalDate getFim()
        + void setFim(LocalDate)
        + double getMultaRescisoria()
        + void setMultaRescisoria(double)
        + String getClausulas()
        + void setClausulas(String)
    }

    %% RELACIONAMENTOS ENTRE AS CLASSES
    Jogador "1" --> "0..1" Time : joga_em
    Jogador "1" --> "0..1" Agente : agenciado_por
    Jogador "1" --> "0..1" Contrato : assina
    Time "1" --> "0..*" Jogador : possui
    Agente "1" --> "0..*" Jogador : agencia
    Transferencia "1" --> "1" Jogador : envolve
    Transferencia "1" --> "0..1" Time : origem/destino
    BID "1" --> "0..*" Transferencia : reporta
    Contrato "1" --> "1" Jogador : envolve
    Contrato "1" --> "1" Time : envolve
:::

## Arquitetura da API REST (Spring Boot)

::: mermaid

    graph TB
        subgraph "Cliente"
            C[Frontend/Cliente HTTP]
        end
        
        subgraph "Spring Boot Application"
            subgraph "Controller Layer"
                TC[TimeController<br/>@RestController]
                AC[AgenteController<br/>@RestController]
                JC[JogadorController<br/>@RestController]
                TRC[TransferenciaController<br/>@RestController]
            end
            
            subgraph "Service Layer"
                S[Sistema<br/>@Service]
            end
            
            subgraph "Domain Layer"
                T[Time]
                A[Agente]
                J[Jogador]
                TR[Transferencia]
                CO[Contrato]
                B[BID]
            end
        end
        
        subgraph "HTTP Endpoints"
            E1[GET /times<br/>POST /times]
            E2[GET /agentes<br/>POST /agentes]
            E3[GET /jogadores]
            E4[GET /transferencias]
        end
        
        %% Connections
        C -->|HTTP Requests| E1
        C -->|HTTP Requests| E2
        C -->|HTTP Requests| E3
        C -->|HTTP Requests| E4
        
        E1 --> TC
        E2 --> AC
        E3 --> JC
        E4 --> TRC
        
        TC -->|@Autowired| S
        AC -->|@Autowired| S
        JC -->|@Autowired| S
        TRC -->|@Autowired| S
        
        S --> T
        S --> A
        S --> J
        S --> TR
        S --> CO
        S --> B
        
        %% Styling
        classDef controller fill:#e1f5fe,stroke:#01579b,stroke-width:2px
        classDef service fill:#f3e5f5,stroke:#4a148c,stroke-width:2px
        classDef domain fill:#e8f5e8,stroke:#1b5e20,stroke-width:2px
        classDef endpoint fill:#fff3e0,stroke:#e65100,stroke-width:2px
        
        class TC,AC,JC,TRC controller
        class S service
        class T,A,J,TR,CO,B domain
        class E1,E2,E3,E4 endpoint
:::

## Fluxo de uma Requisição

::: mermaid

    sequenceDiagram
        participant Client as Cliente
        participant Controller as Controller
        participant Service as Sistema (@Service)
        participant Domain as Entidades
        
        Client->>+Controller: HTTP Request (ex: POST /times)
        Note over Controller: @RestController<br/>@RequestMapping
        
        Controller->>+Service: Method Call (ex: cadastrarTime())
        Note over Service: @Service<br/>Business Logic
        
        Service->>+Domain: Create/Update Entity
        Note over Domain: Time, Agente, Jogador...
        
        Domain-->>-Service: Entity Created
        Service-->>-Controller: Operation Result
        
        Controller->>Controller: JSON Serialization
        Note over Controller: Jackson (Spring Boot)
        
        Controller-->>-Client: HTTP Response (JSON)
:::

## Roadmap

- [x] Modelagem das classes principais (Jogador, Time, Agente, Contrato, Transferencia, BID)
- [x] Implementação dos métodos CRUD nas entidades
- [x] Centralização das operações na classe Sistema
- [x] Atualização do diagrama de classes
- [x] Implementação de interface de usuário (no terminal)
- [x] Validações e tratamento de erros
- [x] Testes automatizados
- [x] **Fase 1: Backend (API REST com Java)**
    - [x] Estruturar projeto com **Spring Boot**
    - [x] Migrar lógica de negócio para um `@Service`
    - [x] Criar `Controllers` para expor a lógica como API REST
- [ ] **Fase 2: Frontend (Interface Web com Next.js)**
    - [ ] Iniciar projeto com **Next.js**
    - [ ] Gerar telas com **v0.dev**
    - [ ] Conectar frontend com a API usando **Copilot**
- [ ] **Fase 3: Hospedagem e Deploy**
    - [ ] Fazer deploy do Backend Java no **Render**
    - [ ] Fazer deploy do Frontend Next.js na **Vercel**
    - [ ] Configurar conexão entre frontend e backend na nuvem
- [ ] Persistencia de dados em .csv (não implementado)

## 📊 Visualização dos Diagramas

### No GitHub/GitLab
Os diagramas Mermaid são renderizados automaticamente quando você visualiza este README.

### No VS Code
1. Instale a extensão "Markdown Preview Mermaid Support"
2. Abra este arquivo README.md
3. Use `Ctrl+Shift+V` para abrir o preview
4. Os diagramas serão renderizados automaticamente

### Online
Para editar ou criar novos diagramas, use o [Mermaid Live Editor](https://mermaid.live/)

### Arquivos de Diagramas
- **DIAGRAMAS.md** - Documentação completa sobre diagramas e ferramentas
- **README.md** - Contém os diagramas principais do projeto
