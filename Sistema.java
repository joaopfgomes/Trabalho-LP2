import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Time> times = new ArrayList<>();
    private List<Jogador> jogadores = new ArrayList<>();
    private List<Transferencia> transferencias = new ArrayList<>();

    public void cadastrarTime(String nome, double saldoCaixa) {
        Time time = new Time(nome, saldoCaixa, new ArrayList<>());
        times.add(time);
    }

    public void cadastrarJogador(String nome, String posicao, double valorMercado, Time timeAtual, Agente agente, Contrato contrato) {
        Jogador jogador = new Jogador(nome, posicao, valorMercado, timeAtual, agente, contrato);
        jogadores.add(jogador);
        if (timeAtual != null) {
            timeAtual.getJogadores().add(jogador);
        }
    }

    public void registrarTransferencia(Jogador jogador, Time timeDestino, double valor, double luvas, double multaRescisoria, double comissaoAgente) {
        Time timeOrigem = jogador.getTimeAtual();

        if (timeOrigem != null) {
            timeOrigem.getJogadores().remove(jogador);
            timeOrigem.setSaldoCaixa(timeOrigem.getSaldoCaixa() + valor + multaRescisoria);
        }

        timeDestino.getJogadores().add(jogador);
        timeDestino.setSaldoCaixa(timeDestino.getSaldoCaixa() - valor - luvas - comissaoAgente);

        jogador.setTimeAtual(timeDestino);

        Transferencia transferencia = new Transferencia(
                jogador,
                timeOrigem,
                timeDestino,
                multaRescisoria,
                luvas,
                valor,
                new java.util.Date(),
                comissaoAgente
        );

        transferencias.add(transferencia);
    }

    public boolean removerJogador(Jogador jogador) {
        if (jogadores.remove(jogador)) {
            if (jogador.getTimeAtual() != null) {
                jogador.getTimeAtual().removerJogadorDoTime(jogador);
            }
            if (jogador.getAgente() != null) {
                jogador.getAgente().removerJogadorAgenciado(jogador);
            }
            return true;
        }
        return false;
    }

    public boolean removerTime(Time time) {
        if (times.remove(time)) {
            for (Jogador jogador : new ArrayList<>(time.getJogadores())) {
                removerJogador(jogador);
            }
            return true;
        }
        return false;
    }

    public boolean removerTransferencia(Transferencia transferencia) {
        return transferencias.remove(transferencia);
    }

    public void exibirRelatorios() {
        System.out.println("Total de transferências realizadas: " + transferencias.size());

        for (Time time : times) {
            System.out.println("Time: " + time.getNome());
            System.out.println("  Jogadores: " + time.getJogadores().size());

            long transferenciasFeitas = transferencias.stream()
                    .filter(t -> t.getTimeOrigem() != null && t.getTimeOrigem().equals(time))
                    .count();
            System.out.println("  Transferências feitas: " + transferenciasFeitas);
        }
    }

    public List<Jogador> listarJogadores() {
        return new ArrayList<>(jogadores);
    }

    public List<Time> listarTimes() {
        return new ArrayList<>(times);
    }

    public List<Transferencia> listarTransferencias() {
        return new ArrayList<>(transferencias);
    }

    public boolean atualizarJogador(Jogador jogador, String novoNome, String novaPosicao, double novoValorMercado, Time novoTime, Agente novoAgente, Contrato novoContrato) {
        if (jogadores.contains(jogador)) {
            jogador.setNomeJogador(novoNome);
            jogador.setPosicao(novaPosicao);
            jogador.setValorMercado(novoValorMercado);
            if (jogador.getTimeAtual() != null) {
                jogador.getTimeAtual().removerJogadorDoTime(jogador);
            }
            jogador.setTimeAtual(novoTime);
            if (novoTime != null) {
                novoTime.getJogadores().add(jogador);
            }
            jogador.setAgente(novoAgente);
            jogador.setContrato(novoContrato);
            return true;
        }
        return false;
    }

    public boolean atualizarTime(Time time, String novoNome, double novoSaldoCaixa) {
        if (times.contains(time)) {
            time.setNomeTime(novoNome);
            time.setSaldoCaixa(novoSaldoCaixa);
            return true;
        }
        return false;
    }

    public boolean atualizarTransferencia(Transferencia transferencia, Jogador novoJogador, Time novoOrigem, Time novoDestino, double novaMulta, double novasLuvas, double novoValor, java.util.Date novaData, double novaComissao) {
        if (transferencias.contains(transferencia)) {
            transferencia.setJogador(novoJogador);
            transferencia.setTimeOrigem(novoOrigem);
            transferencia.setTimeDestino(novoDestino);
            transferencia.setMultaRecisoria(novaMulta);
            transferencia.setLuvas(novasLuvas);
            transferencia.setValor(novoValor);
            transferencia.setData(novaData);
            transferencia.setComissaoAgente(novaComissao);
            return true;
        }
        return false;
    }
}