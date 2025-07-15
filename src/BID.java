package src;

import java.util.ArrayList;
import java.util.List;

public class BID implements Relatorio {
    private List<Transferencia> transferencias;

    public BID(List<Transferencia> transferencias) {
        if (transferencias == null) {
            this.transferencias = new ArrayList<>();
        } else {
            this.transferencias = transferencias;
        }
    }

    public void registrarTransferencia(Transferencia transferencia) {
        transferencias.add(transferencia);
    }

    @Override
    public void gerarRelatorio() {
        System.out.println("===== RELATÓRIO DE TRANSFERÊNCIAS (BID) =====");
        System.out.println("Total de transferências registradas: " + transferencias.size());
        for (Transferencia t : transferencias) {
            Contrato contrato = t.getJogador().getContrato();
            String contratoInfo = (contrato != null)
                ? (" | Contrato: Início=" + contrato.getInicio() + ", Fim=" + contrato.getFim() + ", Multa=" + contrato.getMultaRescisoria() + ", Cláusulas='" + contrato.getClausulas() + "'")
                : " | Contrato: Não informado";
            System.out.println("- Jogador: " + t.getJogador().getNomeJogador()
                + " | Origem: " + (t.getTimeOrigem() != null ? t.getTimeOrigem().getNomeTime() : "Sem time")
                + " | Destino: " + t.getTimeDestino().getNomeTime()
                + " | Valor: " + t.getValor()
                + " | Data: " + t.getData()
                + " | Comissão: " + t.getComissaoAgente()
                + contratoInfo);
        }
    }

    public boolean removerTransferencia(Transferencia transferencia) {
        return transferencias.remove(transferencia);
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }
}
