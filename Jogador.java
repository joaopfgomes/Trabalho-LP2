public class Jogador {
    private String nomeJogador;
    private String posicao;
    private double valorMercado;
    private Time timeAtual;
    private Agente agente;
    private Contrato contrato;

    public Jogador(String nomeJogador, String posicao, double valorMercado, Time timeAtual, Agente agente, Contrato contrato) {
        this.nomeJogador = nomeJogador;
        this.posicao = posicao;
        this.valorMercado = valorMercado;
        this.timeAtual = timeAtual;
        this.agente = agente;
        this.contrato = contrato;
    }
    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
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
    }}