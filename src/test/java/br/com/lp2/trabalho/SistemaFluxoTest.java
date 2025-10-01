package br.com.lp2.trabalho;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import br.com.lp2.trabalho.Sistema;
import br.com.lp2.trabalho.Time;
import br.com.lp2.trabalho.Agente;
import br.com.lp2.trabalho.Contrato;
import br.com.lp2.trabalho.Jogador;


public class SistemaFluxoTest {
    @Test
    public void testFluxoCompletoTransferencia() {
        Sistema sistema = new Sistema();
        // Cadastro de times
        sistema.cadastrarTime("Time A", 10000.0);
        sistema.cadastrarTime("Time B", 20000.0);
        Time timeA = sistema.listarTimes().get(0);
        Time timeB = sistema.listarTimes().get(1);
        // Cadastro de agente
        sistema.cadastrarAgente("Agente X");
        Agente agente = sistema.listarAgentes().get(0);
        // Cadastro de jogador (vinculado ao timeA)
        Contrato contrato = new Contrato(null, timeA, LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), 5000.0, "Cláusula X");
        sistema.cadastrarJogador("João", "Atacante", 1500.0, timeA, agente, contrato);
        Jogador jogador = sistema.listarJogadores().get(0);
        assertEquals(timeA, jogador.getTimeAtual());
        assertEquals(agente, jogador.getAgente());
        // Registrar transferência do jogador para timeB
        Contrato novoContrato = new Contrato(jogador, timeB, LocalDate.of(2025, 7, 15), LocalDate.of(2027, 7, 15), 7000.0, "Cláusula Y");
        sistema.registrarTransferencia(jogador, timeB, 5000.0, 1000.0, 5000.0, 500.0, novoContrato);
        // Verificações pós-transferência
        assertEquals(timeB, jogador.getTimeAtual());
        assertEquals(novoContrato, jogador.getContrato());
        assertTrue(timeB.getJogadores().contains(jogador));
        assertFalse(timeA.getJogadores().contains(jogador));
        assertEquals(1, sistema.listarTransferencias().size());
        // Remover jogador
        assertTrue(sistema.removerJogador(jogador));
        assertEquals(0, sistema.listarJogadores().size());
        // Remover time
        assertTrue(sistema.removerTime(timeA));
        assertEquals(1, sistema.listarTimes().size());
        // Remover agente
        assertTrue(sistema.removerAgente(agente, null));
        assertEquals(0, sistema.listarAgentes().size());
    }
}
