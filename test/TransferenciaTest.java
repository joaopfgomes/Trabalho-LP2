package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import src.Time;
import src.Jogador;
import src.Transferencia;

public class TransferenciaTest {
    @Test
    public void testCriacaoTransferencia() {
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        Time origem = new Time("Time A", 1000.0, null);
        Time destino = new Time("Time B", 2000.0, null);
        LocalDate data = LocalDate.of(2025, 7, 15);
        Transferencia t = new Transferencia(jogador, origem, destino, 1500.0, 200.0, 1700.0, data, 170.0);
        assertEquals(jogador, t.getJogador());
        assertEquals(origem, t.getTimeOrigem());
        assertEquals(destino, t.getTimeDestino());
        assertEquals(1500.0, t.getMultaRescisoria());
        assertEquals(200.0, t.getLuvas());
        assertEquals(1700.0, t.getValor());
        assertEquals(data, t.getData());
        assertEquals(170.0, t.getComissaoAgente());
    }

    @Test
    public void testSettersTransferencia() {
        Jogador jogadorTemp = new Jogador("Temp", "Zagueiro", 0.0, null, null, null);
        Time origemTemp = new Time("OrigemTemp", 0.0, null);
        Time destinoTemp = new Time("DestinoTemp", 0.0, null);
        LocalDate dataTemp = LocalDate.of(2025, 1, 1);
        Transferencia t = new Transferencia(jogadorTemp, origemTemp, destinoTemp, 0, 0, 0, dataTemp, 0);
        Jogador jogador = new Jogador("Junior", "Meio-campo", 800.0, null, null, null);
        Time origem = new Time("Time A", 500.0, null);
        Time destino = new Time("Time B", 800.0, null);
        LocalDate data = LocalDate.of(2026, 1, 1);
        t.setJogador(jogador);
        t.setTimeOrigem(origem);
        t.setTimeDestino(destino);
        t.setMultaRescisoria(1000.0);
        t.setLuvas(100.0);
        t.setValor(1100.0);
        t.setData(data);
        t.setComissaoAgente(110.0);
        assertEquals(jogador, t.getJogador());
        assertEquals(origem, t.getTimeOrigem());
        assertEquals(destino, t.getTimeDestino());
        assertEquals(1000.0, t.getMultaRescisoria());
        assertEquals(100.0, t.getLuvas());
        assertEquals(1100.0, t.getValor());
        assertEquals(data, t.getData());
        assertEquals(110.0, t.getComissaoAgente());
    }
}
