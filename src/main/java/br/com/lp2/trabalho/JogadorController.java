package br.com.lp2.trabalho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    @Autowired
    private Sistema sistema;

    @GetMapping
    public List<Jogador> listarJogadores() {
        return sistema.listarJogadores();
    }

    // Outros endpoints para CRUD de jogadores podem ser adicionados aqui
}
