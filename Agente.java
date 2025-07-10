import java.util.List;

public class Agente {
    private String nome;
    private List<Jogador> jogadoresAgenciados;

    public Agente(string nome, List<Jogador> jogadoresAgenciados) {
        this.nome = nome;
        this.jogadoresAgenciados = jogadoresAgenciados;
    }

    public double calcularComissao(double valorTransferencia) {
        // Exemplo de cálculo
        return valorTransferencia * 0.1;
    }

    // getters e setters
}