import java.util.ArrayList;
import java.util.List;

public class Time {
    private String nomeTime;
    private double saldoCaixa;
    private List<Jogador> jogadores;


    public Time(String nomeTime, double saldoCaixa, List<Jogador> jogadores) {
        this.nomeTime = nomeTime;
        this.saldoCaixa = saldoCaixa;
        if (jogadores == null) {
            this.jogadores = new ArrayList<>();
        } else {
            this.jogadores = jogadores;
        }
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
    }}