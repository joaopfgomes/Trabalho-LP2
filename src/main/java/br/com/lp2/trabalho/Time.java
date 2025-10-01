package br.com.lp2.trabalho;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Time {
    private String nomeTime;
    private double saldoCaixa;
    private List<Jogador> jogadores;

    public Time(String nomeTime, double saldoCaixa, List<Jogador> jogadores) {
        if (nomeTime == null || nomeTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do time não pode ser nulo ou vazio.");
        }
        this.nomeTime = nomeTime;
        this.saldoCaixa = saldoCaixa;
        this.jogadores = Objects.requireNonNullElseGet(jogadores, ArrayList::new);
    }
    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    public double getSaldoCaixa() {
        return saldoCaixa;
    }

    public void setSaldoCaixa(double saldoCaixa) {
        this.saldoCaixa = saldoCaixa;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
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
            if (j.getNomeJogador().equalsIgnoreCase(nomeJogador)) {
                return true;
            }
        }
        return false;
    }}
