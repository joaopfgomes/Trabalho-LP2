package src;

import java.time.LocalDate;

public class Contrato {
    private Jogador jogador;
    private Time time;
    private LocalDate inicio;
    private LocalDate fim;
    private double multaRescisoria;
    private String clausulas;

    public Contrato(Jogador jogador, Time time, LocalDate inicio, LocalDate fim, double multaRescisoria, String clausulas) {
        this.jogador = jogador;
        this.time = time;
        this.inicio = inicio;
        this.fim = fim;
        this.multaRescisoria = multaRescisoria;
        this.clausulas = clausulas;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public double getMultaRescisoria() {
        return multaRescisoria;
    }

    public void setMultaRescisoria(double multaRescisoria) {
        this.multaRescisoria = multaRescisoria;
    }

    public String getClausulas() {
        return clausulas;
    }

    public void setClausulas(String clausulas) {
        this.clausulas = clausulas;
    }
}

