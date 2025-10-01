package br.com.lp2.trabalho;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

    @Autowired
    private Sistema sistema;

    @GetMapping
    public List<Transferencia> listarTransferencias() {
        return sistema.listarTransferencias();
    }
}
