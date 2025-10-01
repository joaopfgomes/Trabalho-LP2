package br.com.lp2.trabalho;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.com.lp2.trabalho.Jogador;
import br.com.lp2.trabalho.Time;
import br.com.lp2.trabalho.Agente;

public class JogadorTest {
    @Test
    public void testCriacaoJogador() {
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        assertEquals("João", jogador.getNomeJogador());
        assertEquals("Atacante", jogador.getPosicao());
        assertEquals(500.0, jogador.getValorMercado());
        assertNull(jogador.getTimeAtual());
        assertNull(jogador.getAgente());
        assertNull(jogador.getContrato());
    }

    @Test
    public void testAssociarTime() {
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        Time time = new Time("Time A", 1000.0, null);
        jogador.associarTime(time);
        assertEquals(time, jogador.getTimeAtual());
        assertTrue(time.getJogadores().contains(jogador));
    }

    @Test
    public void testAssociarAgente() {
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        Agente agente = new Agente("Agente X", null);
        jogador.associarAgente(agente);
        assertEquals(agente, jogador.getAgente());
        assertTrue(agente.getJogadoresAgenciados().contains(jogador));
    }

    @Test
    public void testIsFreeAgent() {
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        assertTrue(jogador.isFreeAgent());
        Time time = new Time("Time A", 1000.0, null);
        jogador.associarTime(time);
        assertFalse(jogador.isFreeAgent());
    }
}
