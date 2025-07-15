package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sistema implements Relatorio {
    private final List<Time> times = new ArrayList<>();
    private final List<Jogador> jogadores = new ArrayList<>();
    private final List<Agente> agentes = new ArrayList<>();
    private final BID bid = new BID(new ArrayList<>());

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

    /**
     * Cadastro completo de jogador, com validações de negócio centralizadas.
     */
    public void cadastrarJogadorCompleto(String nome, String posicao, double valorMercado, Agente agente, Time time, LocalDate inicioContrato, LocalDate fimContrato, double multaRescisoria, String clausulas) {
        if (agente == null) throw new IllegalArgumentException("Todo jogador deve ter um agente.");
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome do jogador não pode ser nulo ou vazio.");
        if (valorMercado < 0) throw new IllegalArgumentException("Valor de mercado não pode ser negativo.");
        Contrato contrato = null;
        if (time != null) {
            if (inicioContrato == null || fimContrato == null) throw new IllegalArgumentException("Contrato exige datas de início e fim.");
            if (multaRescisoria < 0) throw new IllegalArgumentException("Multa rescisória não pode ser negativa.");
            contrato = new Contrato(null, time, inicioContrato, fimContrato, multaRescisoria, clausulas);
        }
        cadastrarJogador(nome, posicao, valorMercado, time, agente, contrato);
    }

    public void registrarTransferencia(Jogador jogador, Time timeDestino, double valor, double luvas, double multaRescisoria, double comissaoAgente, Contrato novoContrato) {
        Time timeOrigem = jogador.getTimeAtual();
        if (timeDestino == null) {
            throw new IllegalArgumentException("Time de destino não pode ser nulo.");
        }
        if (timeOrigem != null && timeOrigem.equals(timeDestino)) {
            throw new IllegalArgumentException("Transferência para o mesmo time não é permitida.");
        }
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
}
