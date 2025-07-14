import java.util.ArrayList;
import java.util.List;

public class Agente {
    private String nomeAgente;
    private List<Jogador> jogadoresAgenciados;

    public Agente(String nomeAgente, List<Jogador> jogadoresAgenciados) {
        this.nomeAgente = nomeAgente;
        if (jogadoresAgenciados == null) {
            this.jogadoresAgenciados = new ArrayList<>();
        } else {
            this.jogadoresAgenciados = jogadoresAgenciados;
        }
    }

    public double calcularComissao(double valorTransferencia) {
        // Exemplo de cálculo
        return valorTransferencia * 0.1;
    }

    public String getNomeAgente() {
        return nomeAgente;
    }

    public void setNomeAgente(String nomeAgente) {
        this.nomeAgente = nomeAgente;
    }

    public List<Jogador> getJogadoresAgenciados() {
        return jogadoresAgenciados;
    }

    public void setJogadoresAgenciados(List<Jogador> jogadoresAgenciados) {
        this.jogadoresAgenciados = jogadoresAgenciados;
    }

    public void removerJogadorAgenciado(Jogador jogador) {
        jogadoresAgenciados.remove(jogador);
    }
}