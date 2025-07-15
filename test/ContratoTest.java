package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.Contrato;
import src.Jogador;
import src.Time;
import java.time.LocalDate;

public class ContratoTest {
    @Test
    public void testCriacaoContrato() {
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        Time time = new Time("Time A", 1000.0, null);
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2026, 1, 1);
        Contrato contrato = new Contrato(jogador, time, inicio, fim, 2000.0, "Cláusula X");
        assertEquals(jogador, contrato.getJogador());
        assertEquals(time, contrato.getTime());
        assertEquals(inicio, contrato.getInicio());
        assertEquals(fim, contrato.getFim());
        assertEquals(2000.0, contrato.getMultaRescisoria());
        assertEquals("Cláusula X", contrato.getClausulas());
    }

    @Test
    public void testSettersContrato() {
        Contrato contrato = new Contrato(null, null, null, null, 0, "");
        Jogador jogador = new Jogador("Maria", "Meio-campo", 800.0, null, null, null);
        Time time = new Time("Time B", 2000.0, null);
        LocalDate inicio = LocalDate.of(2025, 2, 1);
        LocalDate fim = LocalDate.of(2027, 2, 1);
        contrato.setJogador(jogador);
        contrato.setTime(time);
        contrato.setInicio(inicio);
        contrato.setFim(fim);
        contrato.setMultaRescisoria(3000.0);
        contrato.setClausulas("Cláusula Y");
        assertEquals(jogador, contrato.getJogador());
        assertEquals(time, contrato.getTime());
        assertEquals(inicio, contrato.getInicio());
        assertEquals(fim, contrato.getFim());
        assertEquals(3000.0, contrato.getMultaRescisoria());
        assertEquals("Cláusula Y", contrato.getClausulas());
    }
}
