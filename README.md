# Trabalho-LP2

Trabalho final de LP2

Este é o trabalho final de LP2, o tema será um sistema gerenciador de elencos com transferências de jogadores do plantel de diversos times.

## Diagrama de Classes

::: mermaid
    classDiagram
class Jogador {
-String nomeJogador
-String posicao
-double valorMercado
-Time timeAtual
-Agente agente
-Contrato contrato
+removerContrato()
+removerAgente()
+removerTimeAtual()
}

class Time {
-String nomeTime
-double saldoCaixa
-List~Jogador~ jogadores
+removerJogadorDoTime()
}

class Transferencia {
-Jogador jogador
-Time timeOrigem
-Time timeDestino
-double multaRecisoria
-double luvas
-double valor
-Date data
-double comissaoAgente
}

class BID {
-List~Transferencia~ transferencias
+registrarTransferencia()
+removerTransferencia()
}

class Agente {
-String nomeAgente
-List~Jogador~ jogadoresAgenciados
+calcularComissao()
+removerJogadorAgenciado()
}

class Contrato {
-Jogador jogador
-Time time
-Date inicio
-Date fim
-double multaRescisoria
-String clausulas
}

class Campeonato {
-String nomeCampeonato
-List~Time~ times
+removerTime()
}

class Sistema {
+main()
+cadastrarTime()
+cadastrarJogador()
+registrarTransferencia()
+removerJogador()
+removerTime()
+removerTransferencia()
+listarJogadores()
+listarTimes()
+listarTransferencias()
+atualizarJogador()
+atualizarTime()
+atualizarTransferencia()
+exibirRelatorios()
}

Jogador --> Time : jogaPor
Jogador --> Agente : representadoPor
Jogador --> Contrato : possui
Transferencia --> Jogador : envolve
Transferencia --> Time : origem/destino
Transferencia --> Agente : pagaComissao
Transferencia --> Contrato : calculaMulta
BID --> Transferencia : registra
Campeonato --> Time : participa
Sistema --> Time : gerencia
Sistema --> Jogador : gerencia
Sistema --> Transferencia : gerencia
Sistema --> BID : gerencia
:::

## Roadmap

- [x] Modelagem das classes principais (Jogador, Time, Agente, Contrato, Transferencia, BID, Campeonato)
- [x] Implementação dos métodos CRUD nas entidades
- [x] Centralização das operações na classe Sistema
- [x] Atualização do diagrama de classes
- [ ] Implementação de interface de usuário (linha de comando)
- [ ] Persistência dos dados em arquivo .csv
- [ ] Validações e tratamento de erros
- [ ] Testes automatizados
- [ ] Interface gráfica (opcional)
