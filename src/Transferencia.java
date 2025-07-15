package src;

import java.time.LocalDate;

public class Transferencia {
    private Jogador jogador;
    private Time timeOrigem;
    private Time timeDestino;
    private double multaRescisoria;
    private double luvas;
    private double valor;
    private LocalDate data;
    private double comissaoAgente;

    public Transferencia(Jogador jogador, Time timeOrigem, Time timeDestino, double multaRescisoria, double luvas, double valor, LocalDate data, double comissaoAgente) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo na transferência.");
        }
        if (timeDestino == null) {
            throw new IllegalArgumentException("Time de destino não pode ser nulo na transferência.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data da transferência não pode ser nula.");
        }
        if (multaRescisoria < 0 || luvas < 0 || valor < 0 || comissaoAgente < 0) {
            throw new IllegalArgumentException("Valores financeiros não podem ser negativos.");
        }
        this.jogador = jogador;
        this.timeOrigem = timeOrigem;
        this.timeDestino = timeDestino;
        this.multaRescisoria = multaRescisoria;
        this.luvas = luvas;
        this.valor = valor;
        this.data = data;
        this.comissaoAgente = comissaoAgente;
    }
    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Time getTimeOrigem() {
        return timeOrigem;
    }

    public void setTimeOrigem(Time timeOrigem) {
        this.timeOrigem = timeOrigem;
    }

    public Time getTimeDestino() {
        return timeDestino;
    }

    public void setTimeDestino(Time timeDestino) {
        this.timeDestino = timeDestino;
    }

    public double getMultaRescisoria() {
        return multaRescisoria;
    }

    public void setMultaRescisoria(double multaRescisoria) {
        this.multaRescisoria = multaRescisoria;
    }

    public double getLuvas() {
        return luvas;
    }

    public void setLuvas(double luvas) {
        this.luvas = luvas;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getComissaoAgente() {
        return comissaoAgente;
    }

    public void setComissaoAgente(double comissaoAgente) {
        this.comissaoAgente = comissaoAgente;
    }}