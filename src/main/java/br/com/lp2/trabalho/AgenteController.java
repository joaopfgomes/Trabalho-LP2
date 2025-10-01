package br.com.lp2.trabalho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agentes")
public class AgenteController {

    @Autowired
    private Sistema sistema;

    @GetMapping
    public List<Agente> listarAgentes() {
        return sistema.listarAgentes();
    }

    @PostMapping
    public void cadastrarAgente(@RequestBody Agente agente) {
        sistema.cadastrarAgente(agente.getNomeAgente());
    }
}
