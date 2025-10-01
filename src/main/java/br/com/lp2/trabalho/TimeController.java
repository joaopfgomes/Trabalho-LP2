package br.com.lp2.trabalho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    private Sistema sistema;

    @GetMapping
    public List<Time> listarTimes() {
        return sistema.listarTimes();
    }

    @PostMapping
    public void cadastrarTime(@RequestBody Time time) {
        sistema.cadastrarTime(time.getNomeTime(), time.getSaldoCaixa());
    }
}
