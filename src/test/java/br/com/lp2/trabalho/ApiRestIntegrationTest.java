package br.com.lp2.trabalho;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiRestIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void validarPersistenciaDaApiEndpoints() {
        System.out.println("--- INICIANDO TESTE DE INTEGRAÇÃO DA API ---");

        // 1. Criar um Time usando POST /times
        Time novoTime = new Time();
        novoTime.setNomeTime("Flamengo");
        novoTime.setSaldoCaixa(50000000.0);
        
        ResponseEntity<Void> resTime = restTemplate.postForEntity("/times", novoTime, Void.class);
        assertTrue(resTime.getStatusCode().is2xxSuccessful(), "POST /times deve retornar sucesso");

        // 2. Criar um Agente usando POST /agentes
        Agente novoAgente = new Agente();
        novoAgente.setNomeAgente("Jorge Mendes");
        
        ResponseEntity<Void> resAgente = restTemplate.postForEntity("/agentes", novoAgente, Void.class);
        assertTrue(resAgente.getStatusCode().is2xxSuccessful(), "POST /agentes deve retornar sucesso");

        // 3. Buscar os Times usando GET /times
        ResponseEntity<Time[]> getTimes = restTemplate.getForEntity("/times", Time[].class);
        assertTrue(getTimes.getStatusCode().is2xxSuccessful());
        assertNotNull(getTimes.getBody());
        assertTrue(getTimes.getBody().length > 0, "O banco deve conter o time persistido.");
        assertEquals("Flamengo", getTimes.getBody()[0].getNomeTime());

        // 4. Buscar os Agentes usando GET /agentes
        ResponseEntity<Agente[]> getAgentes = restTemplate.getForEntity("/agentes", Agente[].class);
        assertTrue(getAgentes.getStatusCode().is2xxSuccessful());
        assertNotNull(getAgentes.getBody());
        assertTrue(getAgentes.getBody().length > 0, "O banco deve conter o agente persistido.");
        assertEquals("Jorge Mendes", getAgentes.getBody()[0].getNomeAgente());

        System.out.println("--- TESTE DE INTEGRAÇÃO CONCLUÍDO COM SUCESSO! BD H2 ESTÁ SAUDÁVEL. ---");
    }
}
