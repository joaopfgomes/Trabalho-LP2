package br.com.lp2.trabalho;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import br.com.lp2.trabalho.Sistema;


public class SistemaTest {
    @Test
    public void testCadastrarTime() {
        Sistema sistema = new Sistema();
        sistema.cadastrarTime("Time A", 1000.0);
        assertEquals(1, sistema.listarTimes().size());
        assertEquals("Time A", sistema.listarTimes().get(0).getNomeTime());
    }

    @Test
    public void testCadastrarAgente() {
        Sistema sistema = new Sistema();
        sistema.cadastrarAgente("Agente X");
        assertEquals(1, sistema.listarAgentes().size());
        assertEquals("Agente X", sistema.listarAgentes().get(0).getNomeAgente());
    }
}
