package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import src.Sistema;
import src.Time;
import src.Agente;
import src.Contrato;
import src.Jogador;
import src.Transferencia;

import java.util.ArrayList;
import java.util.List;

public class SistemaFluxoCompletoTest {
    @Test
    public void testFluxoCompletoExemplo() {
        Sistema sistema = new Sistema();
        // Cadastrar 20 times
        List<Time> times = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            sistema.cadastrarTime("Time " + i, 10000.0 + i * 1000);
            times.add(sistema.listarTimes().get(i - 1));
        }
        assertEquals(20, sistema.listarTimes().size());
        // Cadastrar 10 agentes
        List<Agente> agentes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            sistema.cadastrarAgente("Agente " + i);
            agentes.add(sistema.listarAgentes().get(i - 1));
        }
        assertEquals(10, sistema.listarAgentes().size());
        // Cadastrar 40 jogadores, distribuindo entre times e agentes
        for (int i = 1; i <= 40; i++) {
            Time time = times.get(i % times.size());
            Agente agente = agentes.get(i % agentes.size());
            Contrato contrato = new Contrato(null, time, LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), 1000.0 + i * 10, "Cláusula " + i);
            sistema.cadastrarJogador("Jogador " + i, "Posição " + (i % 11), 500.0 + i * 20, time, agente, contrato);
        }
        assertEquals(40, sistema.listarJogadores().size());
        // Realizar 10 transferências entre times (evitando transferências para o mesmo time)
        for (int i = 0; i < 10; i++) {
            Jogador jogador = sistema.listarJogadores().get(i);
            Time timeAtual = jogador.getTimeAtual();
            // Encontrar um novo time diferente do atual
            Time novoTime = null;
            for (Time t : times) {
                if (!t.equals(timeAtual)) {
                    novoTime = t;
                    break;
                }
            }
            Contrato novoContrato = new Contrato(jogador, novoTime, LocalDate.of(2025, 7, 15), LocalDate.of(2027, 7, 15), 2000.0 + i * 100, "Nova cláusula " + i);
            sistema.registrarTransferencia(jogador, novoTime, 1500.0 + i * 100, 200.0 + i * 10, 1000.0 + i * 50, 100.0 + i * 5, novoContrato);
        }
        assertEquals(10, sistema.listarTransferencias().size());
        // Verificações finais
        assertEquals(20, sistema.listarTimes().size());
        assertEquals(40, sistema.listarJogadores().size());
        assertEquals(10, sistema.listarAgentes().size());
        assertEquals(10, sistema.listarTransferencias().size());

        // Impressões dos dados cadastrados e transferências
        System.out.println("--- Times cadastrados ---");
        for (Time t : sistema.listarTimes()) {
            System.out.println("- " + t.getNomeTime() + " (Saldo: " + t.getSaldoCaixa() + ")");
        }
        System.out.println("--- Agentes cadastrados ---");
        for (Agente ag : sistema.listarAgentes()) {
            System.out.println("- " + ag.getNomeAgente() + " (Jogadores: " + ag.getJogadoresAgenciados().size() + ")");
        }
        System.out.println("--- Jogadores cadastrados ---");
        for (Jogador j : sistema.listarJogadores()) {
            String status = (j.getTimeAtual() == null) ? "Free-Agent" : j.getTimeAtual().getNomeTime();
            System.out.println("- " + j.getNomeJogador() + " (Posição: " + j.getPosicao() + ", Time: " + status + ")");
        }
        System.out.println("--- Transferências registradas ---");
        for (Transferencia t : sistema.listarTransferencias()) {
            System.out.println("- Jogador: " + t.getJogador().getNomeJogador() + " | Origem: " + (t.getTimeOrigem() != null ? t.getTimeOrigem().getNomeTime() : "Sem time") + " | Destino: " + t.getTimeDestino().getNomeTime() + " | Valor: " + t.getValor() + " | Data: " + t.getData());
        }
    }
}
