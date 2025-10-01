package br.com.lp2.trabalho;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Agente extends Pessoa {
    private List<Jogador> jogadoresAgenciados;

    public Agente(String nomeAgente, List<Jogador> jogadoresAgenciados) {
        super(nomeAgente);
        if (nomeAgente == null || nomeAgente.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do agente não pode ser nulo ou vazio.");
        }
        this.jogadoresAgenciados = Objects.requireNonNullElseGet(jogadoresAgenciados, ArrayList::new);
    }

    public double calcularComissao(double valorTransferencia) {
        // Exemplo de cálculo
        return valorTransferencia * 0.1;
    }

    public String getNomeAgente() {
        return getNome();
    }

    public void setNomeAgente(String nomeAgente) {
        setNome(nomeAgente);
    }

    public List<Jogador> getJogadoresAgenciados() {
        return jogadoresAgenciados;
    }

    public void setJogadoresAgenciados(List<Jogador> jogadoresAgenciados) {
        this.jogadoresAgenciados = jogadoresAgenciados;
    }

    public void removerJogadorAgenciado(Jogador jogador) {
        jogadoresAgenciados.remove(jogador);
    }

    public void adicionarJogadorAgenciado(Jogador jogador) {
        if (!jogadoresAgenciados.contains(jogador)) {
            jogadoresAgenciados.add(jogador);
        }
    }

    public boolean possuiJogador(String nomeJogador) {
        for (Jogador j : jogadoresAgenciados) {
            if (j.getNomeJogador().equalsIgnoreCase(nomeJogador)) {
                return true;
            }
        }
        return false;
    }

    public boolean temJogadores() {
        return !jogadoresAgenciados.isEmpty();
    }
}
