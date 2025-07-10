public class Jogador {
    private String nome;
    private String posicao;
    private double valorMercado;
    private Time timeAtual;
    private Agente agente;
    private Contrato contrato;

    public Jogador(String nome, String posicao, double valorMercado, Time timeAtual, Agente agente, Contrato contrato) {
        this.nome = nome;
        this.posicao = posicao;
        this.valorMercado = valorMercado;
        this.timeAtual = timeAtual;
        this.agente = agente;
        this.contrato = contrato;
    }
    // Construtor, getters e setters
}