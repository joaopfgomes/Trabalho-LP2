package br.com.lp2.trabalho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class Sistema {
    
    @Autowired
    private TimeRepository timeRepository;
    @Autowired
    private JogadorRepository jogadorRepository;
    @Autowired
    private AgenteRepository agenteRepository;
    @Autowired
    private TransferenciaRepository transferenciaRepository;


    @Transactional
    public void cadastrarTime(String nome, double saldoCaixa) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome do time inválido.");
        if (buscarTime(nome) != null) throw new IllegalArgumentException("Já existe um time cadastrado com este nome!");
        Time time = new Time(nome, saldoCaixa, null);
        timeRepository.save(time);
    }

    @Transactional
    public void cadastrarAgente(String nomeAgente) {
        if (nomeAgente == null || nomeAgente.trim().isEmpty()) throw new IllegalArgumentException("Nome do agente inválido.");
        if (buscarAgente(nomeAgente) != null) throw new IllegalArgumentException("Já existe um agente cadastrado com este nome!");
        Agente agente = new Agente(nomeAgente, null);
        agenteRepository.save(agente);
    }

    @Transactional
    public void cadastrarJogador(String nome, String posicao, double valorMercado, Time timeAtual, Agente agente, Contrato contrato) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome do jogador não pode ser nulo ou vazio.");
        if (buscarJogador(nome) != null) throw new IllegalArgumentException("Já existe um jogador cadastrado com este nome!");
        if (agente == null) throw new IllegalArgumentException("Todo jogador deve ter um agente.");
        Jogador jogador = new Jogador(nome, posicao, valorMercado, null, null, null);
        jogador.associarAgente(agente);
        if (timeAtual != null) {
            jogador.associarTime(timeAtual);
        }
        if (contrato != null) {
            jogador.associarContrato(contrato);
            contrato.setJogador(jogador);
        }
        jogadorRepository.save(jogador);
    }

    @Transactional
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

    @Transactional
    public void registrarTransferencia(Jogador jogador, Time timeDestino, double valor, double luvas, double multaRescisoria, double comissaoAgente, Contrato novoContrato) {
        Time timeOrigem = jogador.getTimeAtual();
        if (timeDestino == null) {
            throw new IllegalArgumentException("Time de destino não pode ser nulo.");
        }
        if (timeOrigem != null && timeOrigem.getId() != null && timeOrigem.getId().equals(timeDestino.getId())) {
            throw new IllegalArgumentException("Transferência para o mesmo time não é permitida.");
        }
        
        // Remove contrato antigo se houver
        Contrato contratoAntigo = jogador.getContrato();
        if (contratoAntigo != null) {
            jogador.removerContrato();
            // Removido contratoRepository.delete() pois JPA lida com orphanRemoval=true
        }

        // Sem time origem
        if (timeOrigem == null) {
            timeDestino.setSaldoCaixa(timeDestino.getSaldoCaixa() - valor - luvas - comissaoAgente);
            jogador.associarTime(timeDestino);
            if (novoContrato != null) {
                novoContrato.setJogador(jogador);
                jogador.associarContrato(novoContrato);
            }
            Transferencia t = new Transferencia(jogador, null, timeDestino, 0.0, luvas, valor, LocalDate.now(), comissaoAgente);
            
            timeRepository.save(timeDestino);
            jogadorRepository.save(jogador);
            transferenciaRepository.save(t);
            return;
        }

        // Com time origem
        timeOrigem.removerJogadorDoTime(jogador);
        timeOrigem.setSaldoCaixa(timeOrigem.getSaldoCaixa() + valor + multaRescisoria);
        
        timeDestino.setSaldoCaixa(timeDestino.getSaldoCaixa() - valor - luvas - comissaoAgente - multaRescisoria);
        
        jogador.associarTime(timeDestino);
        if (novoContrato != null) {
            novoContrato.setJogador(jogador);
            jogador.associarContrato(novoContrato);
        }

        Transferencia t = new Transferencia(jogador, timeOrigem, timeDestino, multaRescisoria, luvas, valor, LocalDate.now(), comissaoAgente);
        
        timeRepository.save(timeOrigem);
        timeRepository.save(timeDestino);
        jogadorRepository.save(jogador);
        transferenciaRepository.save(t);
    }

    @Transactional
    public boolean removerJogador(Jogador jogador) {
        if (jogador == null) return false;
        jogadorRepository.delete(jogador);
        return true;
    }

    @Transactional
    public boolean removerTime(Time time) {
        if (time == null) return false;
        timeRepository.delete(time);
        return true;
    }

    @Transactional
    public boolean removerAgente(Agente agenteParaRemover, Agente novoAgente) {
        if (agenteParaRemover == null) return false;
        
        Long idAgenteParaRemover = agenteParaRemover.getId();
        if (idAgenteParaRemover == null) return false;

        if (agenteParaRemover.temJogadores()) {
            if (novoAgente == null) {
                return false;
            }
            Long idNovoAgente = novoAgente.getId();
            if (idNovoAgente != null && idNovoAgente.equals(idAgenteParaRemover)) {
                return false;
            }
            for (Jogador jogador : agenteRepository.findById(idAgenteParaRemover).get().getJogadoresAgenciados()) {
                jogador.associarAgente(novoAgente);
                jogadorRepository.save(jogador);
            }
        }
        agenteRepository.delete(agenteParaRemover);
        return true;
    }

    public List<Jogador> listarJogadores() {
        return jogadorRepository.findAll();
    }

    public List<Time> listarTimes() {
        return timeRepository.findAll();
    }

    public List<Transferencia> listarTransferencias() {
        return transferenciaRepository.findAll();
    }

    public List<Agente> listarAgentes() {
        return agenteRepository.findAll();
    }

    // Buscas para suporte aos Controllers REST
    public Time buscarTime(String nomeTime) {
        return timeRepository.findByNomeTimeIgnoreCase(nomeTime);
    }

    public Agente buscarAgente(String nomeAgente) {
        return agenteRepository.findByNomeIgnoreCase(nomeAgente);
    }

    public Jogador buscarJogador(String nomeJogador) {
        return jogadorRepository.findByNomeIgnoreCase(nomeJogador);
    }
}
