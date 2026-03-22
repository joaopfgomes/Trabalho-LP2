package br.com.lp2.trabalho;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class SistemaTest {

    @Autowired
    private Sistema sistema;

    @Test
    public void testCadastrarTime() {
        sistema.cadastrarTime("Time A", 1000.0);
        assertTrue(sistema.listarTimes().stream().anyMatch(t -> t.getNomeTime().equals("Time A")));
    }

    @Test
    public void testCadastrarAgente() {
        sistema.cadastrarAgente("Agente X");
        assertTrue(sistema.listarAgentes().stream().anyMatch(a -> a.getNomeAgente().equals("Agente X")));
    }
}
