package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sistema implements Relatorio {
    private List<Time> times = new ArrayList<>();
    private List<Jogador> jogadores = new ArrayList<>();
    private List<Agente> agentes = new ArrayList<>();
    private BID bid = new BID(new ArrayList<>());

    public void cadastrarTime(String nome, double saldoCaixa) {
        Time time = new Time(nome, saldoCaixa, new ArrayList<>());
        times.add(time);
    }

    public void cadastrarAgente(String nomeAgente) {
        Agente agente = new Agente(nomeAgente, new ArrayList<>());
        agentes.add(agente);
    }

    public void cadastrarJogador(String nome, String posicao, double valorMercado, Time timeAtual, Agente agente, Contrato contrato) {
        if (agente == null) throw new IllegalArgumentException("Todo jogador deve ter um agente.");
        Jogador jogador = new Jogador(nome, posicao, valorMercado, null, null, null);
        jogador.associarAgente(agente);
        if (timeAtual != null) {
            jogador.associarTime(timeAtual);
        }
        if (contrato != null) {
            jogador.associarContrato(contrato);
        }
        jogadores.add(jogador);
    }

    public void registrarTransferencia(Jogador jogador, Time timeDestino, double valor, double luvas, double multaRescisoria, double comissaoAgente, Contrato novoContrato) {
        Time timeOrigem = jogador.getTimeAtual();
        if (timeOrigem == null || jogador.getContrato() == null) {
            timeDestino.adicionarJogador(jogador);
            jogador.associarTime(timeDestino);
            jogador.associarContrato(novoContrato);
            timeDestino.setSaldoCaixa(timeDestino.getSaldoCaixa() - valor - luvas - comissaoAgente);
            Transferencia transferencia = new Transferencia(
                jogador,
                null,
                timeDestino,
                0.0,
                luvas,
                valor,
                LocalDate.now(),
                comissaoAgente
            );
            bid.registrarTransferencia(transferencia);
            return;
        }
        timeOrigem.removerJogadorDoTime(jogador);
        timeOrigem.setSaldoCaixa(timeOrigem.getSaldoCaixa() + valor + multaRescisoria);
        timeDestino.adicionarJogador(jogador);
        timeDestino.setSaldoCaixa(timeDestino.getSaldoCaixa() - valor - luvas - comissaoAgente - multaRescisoria);
        jogador.associarTime(timeDestino);
        jogador.associarContrato(novoContrato);
        Transferencia transferencia = new Transferencia(
                jogador,
                timeOrigem,
                timeDestino,
                multaRescisoria,
                luvas,
                valor,
                LocalDate.now(),
                comissaoAgente
        );
        bid.registrarTransferencia(transferencia);
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

    public boolean removerAgente(Agente agenteParaRemover, Agente novoAgente) {
        if (agenteParaRemover == null) {
            return false;
        }

        // Se o agente tem jogadores, eles devem ser movidos para um novo agente
        if (agenteParaRemover.temJogadores()) {
            if (novoAgente == null || novoAgente.equals(agenteParaRemover)) {
                // Não é possível mover jogadores para o nada ou para o mesmo agente
                return false;
            }
            // Move todos os jogadores para o novo agente
            for (Jogador jogador : new ArrayList<>(agenteParaRemover.getJogadoresAgenciados())) {
                jogador.associarAgente(novoAgente);
            }
        }

        // Remove o agente da lista do sistema
        return agentes.remove(agenteParaRemover);
    }

    public boolean removerAgenteComTransferencia(Agente agenteParaRemover, Agente novoAgente) {
        if (agenteParaRemover == null) {
            return false;
        }
        if (agenteParaRemover.temJogadores()) {
            if (novoAgente == null || novoAgente.equals(agenteParaRemover)) {
                return false;
            }
            for (Jogador jogador : new ArrayList<>(agenteParaRemover.getJogadoresAgenciados())) {
                jogador.associarAgente(novoAgente);
            }
        }
        return agentes.remove(agenteParaRemover);
    }

    public boolean removerTransferencia(Transferencia transferencia) {
        return bid.removerTransferencia(transferencia);
    }

    @Override
    public void gerarRelatorio() {
        bid.gerarRelatorio();
    }

    public List<Jogador> listarJogadores() {
        return new ArrayList<>(jogadores);
    }

    public List<Time> listarTimes() {
        return new ArrayList<>(times);
    }

    public List<Transferencia> listarTransferencias() {
        return bid.getTransferencias();
    }

    public List<Agente> listarAgentes() {
        return new ArrayList<>(agentes);
    }

    public List<Agente> listarAgentesValidosParaTransferencia(Agente agenteParaRemover) {
        List<Agente> validos = new ArrayList<>();
        for (Agente ag : agentes) {
            if (!ag.equals(agenteParaRemover)) {
                validos.add(ag);
            }
        }
        return validos;
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

    public boolean atualizarTransferencia(Transferencia transferencia, Jogador novoJogador, Time novoOrigem, Time novoDestino, double novaMulta, double novasLuvas, double novoValor, LocalDate novaData, double novaComissao) {
        if (bid.getTransferencias().contains(transferencia)) {
            transferencia.setJogador(novoJogador);
            transferencia.setTimeOrigem(novoOrigem);
            transferencia.setTimeDestino(novoDestino);
            transferencia.setMultaRescisoria(novaMulta);
            transferencia.setLuvas(novasLuvas);
            transferencia.setValor(novoValor);
            transferencia.setData(novaData);
            transferencia.setComissaoAgente(novaComissao);
            return true;
        }
        return false;
    }
}
