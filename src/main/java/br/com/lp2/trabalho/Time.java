package br.com.lp2.trabalho;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nomeTime;
    private double saldoCaixa;

    @JsonIgnore
    @OneToMany(mappedBy = "timeAtual")
    private List<Jogador> jogadores = new ArrayList<>();

    public Time() {}

    public Time(String nomeTime, double saldoCaixa, List<Jogador> jogadores) {
        if (nomeTime == null || nomeTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do time não pode ser nulo ou vazio.");
        }
        this.nomeTime = nomeTime;
        this.saldoCaixa = saldoCaixa;
        if (jogadores != null) this.jogadores = jogadores;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeTime() { return nomeTime; }
    public void setNomeTime(String nomeTime) { this.nomeTime = nomeTime; }
    public double getSaldoCaixa() { return saldoCaixa; }
    public void setSaldoCaixa(double saldoCaixa) { this.saldoCaixa = saldoCaixa; }
    public List<Jogador> getJogadores() { return jogadores; }
    public void setJogadores(List<Jogador> jogadores) { this.jogadores = jogadores; }

    public void removerJogadorDoTime(Jogador jogador) {
        jogadores.remove(jogador);
        if (jogador.getTimeAtual() == this) {
            jogador.setTimeAtual(null);
        }
    }

    public void adicionarJogador(Jogador jogador) {
        if (!jogadores.contains(jogador)) {
            jogadores.add(jogador);
            jogador.setTimeAtual(this);
        }
    }

    public boolean possuiJogador(String nomeJogador) {
        for (Jogador j : jogadores) {
            if (j.getNomeJogador().equalsIgnoreCase(nomeJogador)) return true;
        }
        return false;
    }
}
