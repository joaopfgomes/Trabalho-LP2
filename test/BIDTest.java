package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import src.BID;
import src.Jogador;
import src.Time;
import src.Transferencia;

public class BIDTest {
    @Test
    public void testRegistrarEListarTransferencia() {
        BID bid = new BID(new ArrayList<>());
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        Time origem = new Time("Time A", 1000.0, null);
        Time destino = new Time("Time B", 2000.0, null);
        LocalDate data = LocalDate.of(2025, 7, 15);
        Transferencia t = new Transferencia(jogador, origem, destino, 1500.0, 200.0, 1700.0, data, 170.0);
        bid.registrarTransferencia(t);
        List<Transferencia> transferencias = bid.getTransferencias();
        assertEquals(1, transferencias.size());
        assertEquals(t, transferencias.get(0));
    }

    @Test
    public void testRemoverTransferencia() {
        BID bid = new BID(new ArrayList<>());
        Jogador jogador = new Jogador("Maria", "Meio-campo", 800.0, null, null, null);
        Time origem = new Time("Time X", 500.0, null);
        Time destino = new Time("Time Y", 800.0, null);
        LocalDate data = LocalDate.of(2026, 1, 1);
        Transferencia t = new Transferencia(jogador, origem, destino, 1000.0, 100.0, 1100.0, data, 110.0);
        bid.registrarTransferencia(t);
        assertTrue(bid.removerTransferencia(t));
        assertTrue(bid.getTransferencias().isEmpty());
    }

    @Test
    public void testSetTransferencias() {
        BID bid = new BID(null);
        List<Transferencia> lista = new ArrayList<>();
        bid.setTransferencias(lista);
        assertSame(lista, bid.getTransferencias());
    }
}
