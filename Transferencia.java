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
        this.multaRecisoria = multaRecisoria;
        this.luvas = luvas;
        this.valor = valor;
        this.data = data;
        this.comissaoAgente = comissaoAgente;
    }
    // getters e setters
}