import java.util.List;

public class BID {
    private List<Transferencia> transferencias;

    public BID(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    public void registrarTransferencia(Transferencia transferencia) {
        transferencias.add(transferencia);
    }

    // Getters e setters
}