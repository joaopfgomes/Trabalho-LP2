import java.util.Date;

public class Transferencia {
    private Jogador jogador;
    private Time timeOrigem;
    private Time timeDestino;
    private double multaRecisoria;
    private double luvas;
    private double valor;
    private Date data;
    private double comissaoAgente;

    public Transferencia(Jogador jogador, Time timeOrigem, Time timeDestino, double multaRecisoria, double luvas, double valor, Date data, double comissaoAgente) {
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
        return multaRecisoria;
    }

    public void setMultaRescisoria(double multaRescisoria) {
        this.multaRecisoria = multaRescisoria;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getComissaoAgente() {
        return comissaoAgente;
    }

    public void setComissaoAgente(double comissaoAgente) {
        this.comissaoAgente = comissaoAgente;
    }}