package br.com.lp2.trabalho;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SistemaFluxoTest {

    @Autowired
    private Sistema sistema;

    @Test
    public void testFluxoCompletoTransferencia() {
        sistema.cadastrarTime("Time A", 10000.0);
        sistema.cadastrarTime("Time B", 20000.0);
        Time timeA = sistema.buscarTime("Time A");
        Time timeB = sistema.buscarTime("Time B");
        
        sistema.cadastrarAgente("Agente X");
        Agente agente = sistema.buscarAgente("Agente X");
        
        Contrato contrato = new Contrato(null, timeA, LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), 5000.0, "Cláusula X");
        sistema.cadastrarJogador("João", "Atacante", 1500.0, timeA, agente, contrato);
        
        Jogador jogador = sistema.buscarJogador("João");
        assertNotNull(jogador.getTimeAtual());
        assertEquals("Time A", jogador.getTimeAtual().getNomeTime());
        
        Contrato novoContrato = new Contrato(jogador, timeB, LocalDate.of(2025, 7, 15), LocalDate.of(2027, 7, 15), 7000.0, "Cláusula Y");
        sistema.registrarTransferencia(jogador, timeB, 5000.0, 1000.0, 5000.0, 500.0, novoContrato);
        
        jogador = sistema.buscarJogador("João");
        assertEquals("Time B", jogador.getTimeAtual().getNomeTime());
        
        assertTrue(sistema.removerJogador(jogador));
        assertNull(sistema.buscarJogador("João"));
    }
}
