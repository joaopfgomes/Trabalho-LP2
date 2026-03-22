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
    @PostMapping
    public ResponseEntity<String> cadastrarJogador(@RequestBody JogadorDTO dto) {
        try {
            Agente agente = sistema.buscarAgente(dto.getNomeAgente());
            Time time = null;
            if (dto.getNomeTime() != null && !dto.getNomeTime().isEmpty()) {
                time = sistema.buscarTime(dto.getNomeTime());
            }

            sistema.cadastrarJogadorCompleto(
                    dto.getNomeJogador(),
                    dto.getPosicao(),
                    dto.getValorMercado(),
                    agente,
                    time,
                    dto.getInicioContrato() != null ? java.time.LocalDate.parse(dto.getInicioContrato()) : null,
                    dto.getFimContrato() != null ? java.time.LocalDate.parse(dto.getFimContrato()) : null,
                    dto.getMultaRescisoria(),
                    dto.getClausulas()
            );
            return ResponseEntity.ok("Jogador cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public static class JogadorDTO {
        private String nomeJogador;
        private String posicao;
        private double valorMercado;
        private String nomeAgente;
        private String nomeTime;
        private String inicioContrato;
        private String fimContrato;
        private double multaRescisoria;
        private String clausulas;

        public String getNomeJogador() { return nomeJogador; }
        public void setNomeJogador(String nomeJogador) { this.nomeJogador = nomeJogador; }
        public String getPosicao() { return posicao; }
        public void setPosicao(String posicao) { this.posicao = posicao; }
        public double getValorMercado() { return valorMercado; }
        public void setValorMercado(double valorMercado) { this.valorMercado = valorMercado; }
        public String getNomeAgente() { return nomeAgente; }
        public void setNomeAgente(String nomeAgente) { this.nomeAgente = nomeAgente; }
        public String getNomeTime() { return nomeTime; }
        public void setNomeTime(String nomeTime) { this.nomeTime = nomeTime; }
        public String getInicioContrato() { return inicioContrato; }
        public void setInicioContrato(String inicioContrato) { this.inicioContrato = inicioContrato; }
        public String getFimContrato() { return fimContrato; }
        public void setFimContrato(String fimContrato) { this.fimContrato = fimContrato; }
        public double getMultaRescisoria() { return multaRescisoria; }
        public void setMultaRescisoria(double multaRescisoria) { this.multaRescisoria = multaRescisoria; }
        public String getClausulas() { return clausulas; }
        public void setClausulas(String clausulas) { this.clausulas = clausulas; }
    }
}
