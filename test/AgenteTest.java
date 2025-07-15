package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.Agente;
import src.Jogador;


public class AgenteTest {
    @Test
    public void testCriacaoAgente() {
        Agente agente = new Agente("Agente X", null);
        assertEquals("Agente X", agente.getNomeAgente());
        assertNotNull(agente.getJogadoresAgenciados());
        assertTrue(agente.getJogadoresAgenciados().isEmpty());
    }

    @Test
    public void testAdicionarRemoverJogadorAgenciado() {
        Agente agente = new Agente("Agente X", null);
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        agente.adicionarJogadorAgenciado(jogador);
        assertTrue(agente.getJogadoresAgenciados().contains(jogador));
        agente.removerJogadorAgenciado(jogador);
        assertFalse(agente.getJogadoresAgenciados().contains(jogador));
    }

    @Test
    public void testCalcularComissao() {
        Agente agente = new Agente("Agente X", null);
        double comissao = agente.calcularComissao(1000.0);
        assertEquals(100.0, comissao);
    }

    @Test
    public void testTemJogadores() {
        Agente agente = new Agente("Agente X", null);
        assertFalse(agente.temJogadores());
        Jogador jogador = new Jogador("João", "Atacante", 500.0, null, null, null);
        agente.adicionarJogadorAgenciado(jogador);
        assertTrue(agente.temJogadores());
    }
}
