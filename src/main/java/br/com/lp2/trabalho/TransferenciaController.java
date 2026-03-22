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

    @org.springframework.web.bind.annotation.PostMapping
    public org.springframework.http.ResponseEntity<String> registrarTransferencia(@org.springframework.web.bind.annotation.RequestBody TransferenciaDTO dto) {
        try {
            Jogador jogador = sistema.buscarJogador(dto.getNomeJogador());
            Time timeDestino = sistema.buscarTime(dto.getNomeTimeDestino());

            if (jogador == null) return org.springframework.http.ResponseEntity.badRequest().body("Jogador não encontrado.");
            if (timeDestino == null) return org.springframework.http.ResponseEntity.badRequest().body("Time de destino não encontrado.");

            Contrato novoContrato = null;
            if (dto.getInicioContrato() != null && dto.getFimContrato() != null) {
                java.time.LocalDate inicio = java.time.LocalDate.parse(dto.getInicioContrato());
                java.time.LocalDate fim = java.time.LocalDate.parse(dto.getFimContrato());
                novoContrato = new Contrato(null, timeDestino, inicio, fim, dto.getMultaContrato(), dto.getClausulasContrato());
            }

            sistema.registrarTransferencia(
                    jogador,
                    timeDestino,
                    dto.getValor(),
                    dto.getLuvas(),
                    dto.getMultaRescisoria(),
                    dto.getComissaoAgente(),
                    novoContrato
            );
            return org.springframework.http.ResponseEntity.ok("Transferência registrada com sucesso!");
        } catch (IllegalArgumentException e) {
            return org.springframework.http.ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public static class TransferenciaDTO {
        private String nomeJogador;
        private String nomeTimeDestino;
        private double valor;
        private double luvas;
        private double multaRescisoria;
        private double comissaoAgente;
        private String inicioContrato;
        private String fimContrato;
        private double multaContrato;
        private String clausulasContrato;

        public String getNomeJogador() { return nomeJogador; }
        public void setNomeJogador(String nomeJogador) { this.nomeJogador = nomeJogador; }
        public String getNomeTimeDestino() { return nomeTimeDestino; }
        public void setNomeTimeDestino(String nomeTimeDestino) { this.nomeTimeDestino = nomeTimeDestino; }
        public double getValor() { return valor; }
        public void setValor(double valor) { this.valor = valor; }
        public double getLuvas() { return luvas; }
        public void setLuvas(double luvas) { this.luvas = luvas; }
        public double getMultaRescisoria() { return multaRescisoria; }
        public void setMultaRescisoria(double multaRescisoria) { this.multaRescisoria = multaRescisoria; }
        public double getComissaoAgente() { return comissaoAgente; }
        public void setComissaoAgente(double comissaoAgente) { this.comissaoAgente = comissaoAgente; }
        public String getInicioContrato() { return inicioContrato; }
        public void setInicioContrato(String inicioContrato) { this.inicioContrato = inicioContrato; }
        public String getFimContrato() { return fimContrato; }
        public void setFimContrato(String fimContrato) { this.fimContrato = fimContrato; }
        public double getMultaContrato() { return multaContrato; }
        public void setMultaContrato(double multaContrato) { this.multaContrato = multaContrato; }
        public String getClausulasContrato() { return clausulasContrato; }
        public void setClausulasContrato(String clausulasContrato) { this.clausulasContrato = clausulasContrato; }
    }
}
