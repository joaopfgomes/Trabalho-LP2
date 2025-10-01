package br.com.lp2.trabalho;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.com.lp2.trabalho.Time;
import br.com.lp2.trabalho.Jogador;

public class TimeTest {
    @Test
    public void testCriacaoTime() {
        Time time = new Time("Time A", 1000.0, null);
        assertEquals("Time A", time.getNomeTime());
        assertEquals(1000.0, time.getSaldoCaixa());
        assertNotNull(time.getJogadores());
        assertTrue(time.getJogadores().isEmpty());
    }

    @Test
    public void testAdicionarRemoverJogador() {
        Time time = new Time("Time A", 1000.0, null);
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        time.adicionarJogador(jogador);
        assertTrue(time.getJogadores().contains(jogador));
        time.removerJogadorDoTime(jogador);
        assertFalse(time.getJogadores().contains(jogador));
    }

    @Test
    public void testPossuiJogador() {
        Time time = new Time("Time A", 1000.0, null);
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        time.adicionarJogador(jogador);
        assertTrue(time.possuiJogador("João"));
        assertFalse(time.possuiJogador("Maria"));
    }
}
