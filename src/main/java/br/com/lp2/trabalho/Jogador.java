package br.com.lp2.trabalho;


public class Jogador extends Pessoa {
    private String posicao;
    private double valorMercado;
    private Time timeAtual;
    private Agente agente;
    private Contrato contrato;

    public Jogador(String nomeJogador, String posicao, double valorMercado, Time timeAtual, Agente agente, Contrato contrato) {
        super(nomeJogador);
        if (nomeJogador == null || nomeJogador.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do jogador não pode ser nulo ou vazio.");
        }
        this.posicao = posicao;
        this.valorMercado = valorMercado;
        this.timeAtual = timeAtual;
        this.agente = agente;
        this.contrato = contrato;
    }

    public String getNomeJogador() {
        return getNome();
    }

    public void setNomeJogador(String nomeJogador) {
        setNome(nomeJogador);
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public double getValorMercado() {
        return valorMercado;
    }

    public void setValorMercado(double valorMercado) {
        this.valorMercado = valorMercado;
    }

    public Time getTimeAtual() {
        return timeAtual;
    }

    public void setTimeAtual(Time timeAtual) {
        this.timeAtual = timeAtual;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public void removerContrato() {
        this.contrato = null;
    }
    public void removerAgente() {
        this.agente = null;
    }
    public void removerTimeAtual() {
        this.timeAtual = null;
    }
    public void associarTime(Time time) {
        this.timeAtual = time;
        if (time != null && !time.getJogadores().contains(this)) {
            time.adicionarJogador(this);
        }
    }

    public void associarAgente(Agente agente) {
        this.agente = agente;
        if (agente != null && !agente.getJogadoresAgenciados().contains(this)) {
            agente.adicionarJogadorAgenciado(this);
        }
    }

    public void associarContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public boolean isFreeAgent() {
        return this.timeAtual == null && this.contrato == null;
    }
}
