import java.util.Date;

public class Contrato {
    private Jogador jogador;
    private Time time;
    private Date inicio;
    private Date fim;
    private double multaRescisoria;
    private String clausulas;

    public Contrato(Jogador jogador, Time time, Date inicio, Date fim, double multaRescisoria, String clausulas) {
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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
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
    }}