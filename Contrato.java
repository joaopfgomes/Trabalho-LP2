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

    // Getters e setters
}