# Trabalho-LP2

Trabalho final de LP2


Este é o trabalho final de LP2, o tema será um sistema gerenciador de elencos com transferências de jogadores do plantel de diversos times.

o diagrama das classes atual está da seguinte maneira:

::: mermaid
    classDiagram
class Jogador {
-String nome
-String posicao
-double valorMercado
-Time timeAtual
-Agente agente
-Contrato contrato
}

class Time {
-String nome
-double saldoCaixa
-List~Jogador~ jogadores
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
}

class Agente {
-String nome
-List~Jogador~ jogadoresAgenciados
+calcularComissao()
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
-String nome
-List~Time~ times
}

class Sistema {
+main()
+cadastrarTime()
+cadastrarJogador()
+registrarTransferencia()
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
