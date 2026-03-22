package br.com.lp2.trabalho;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SistemaFluxoCompletoTest {

    @Autowired
    private Sistema sistema;

    @Test
    public void testFluxoCompletoExemplo() {
        List<Time> times = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            sistema.cadastrarTime("Time " + i, 10000.0 + i * 1000);
            times.add(sistema.buscarTime("Time " + i));
        }

        List<Agente> agentes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            sistema.cadastrarAgente("Agente " + i);
            agentes.add(sistema.buscarAgente("Agente " + i));
        }

        for (int i = 1; i <= 40; i++) {
            Time time = times.get(i % times.size());
            Agente agente = agentes.get(i % agentes.size());
            Contrato contrato = new Contrato(null, time, LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), 1000.0 + i * 10, "Cláusula " + i);
            sistema.cadastrarJogador("Jogador " + i, "Posição " + (i % 11), 500.0 + i * 20, time, agente, contrato);
        }

        assertTrue(sistema.listarJogadores().size() >= 40);

        for (int i = 1; i <= 10; i++) {
            Jogador jogador = sistema.buscarJogador("Jogador " + i);
            Time timeAtual = jogador.getTimeAtual();
            Time novoTime = null;
            for (Time t : times) {
                if (timeAtual == null || !t.getId().equals(timeAtual.getId())) {
                    novoTime = t;
                    break;
                }
            }
            Contrato novoContrato = new Contrato(jogador, novoTime, LocalDate.of(2025, 7, 15), LocalDate.of(2027, 7, 15), 2000.0, "Nova cláusula");
            sistema.registrarTransferencia(jogador, novoTime, 1500.0, 200.0, 1000.0, 100.0, novoContrato);
        }

        assertTrue(sistema.listarTransferencias().size() >= 10);
    }
}
