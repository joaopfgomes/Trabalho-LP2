´´´

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

´´´
