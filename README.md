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

Roadmap de Desenvolvimento:
Finalizar os modelos das classes - Garantir que todas as classes (Jogador, Time, Transferencia, Agente, Contrato, BID, Campeonato, Sistema) tenham construtores, getters e setters. - Validar os relacionamentos entre as classes (ex: listas, referências).  
Implementar métodos principais - Métodos de cadastro (cadastrarTime, cadastrarJogador) na classe Sistema. - Método de registro de transferência (registrarTransferencia) e lógica de atualização dos objetos envolvidos. - Métodos de relatório (exibirRelatorios) para mostrar dados relevantes.  
Adicionar lógica de negócio - Regras para transferências (ex: cálculo de multa, luvas, comissão). - Atualização de saldo dos times e contratos dos jogadores. - Validação de datas e restrições contratuais.  
Interface de usuário no console - Criar um menu interativo para o usuário escolher as funções do sistema (cadastro, transferência, relatórios, etc). - Implementar leitura de dados via teclado e exibição de resultados no console.  
Persistência de dados (opcional) - Definir se os dados serão mantidos apenas em memória ou se haverá persistência (ex: arquivos, banco de dados).  
Testes - Escrever testes unitários para os métodos principais. - Validar cenários de transferência, cadastro e relatórios.  
Documentação - Comentar o código e atualizar o README.md com instruções de uso e exemplos.
