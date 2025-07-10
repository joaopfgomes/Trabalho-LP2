import java.util.List;

public class Time {
    private String nome;
    private double saldoCaixa;
    private List<Jogador> jogadores;


    public Time(String nome, double saldoCaixa, List<Jogador> jogadores) {
        this.nome = nome;
        this.saldoCaixa = saldoCaixa;
        this.jogadores = jogadores;
    }
    // getters e setters
}