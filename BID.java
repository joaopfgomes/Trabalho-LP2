import java.util.ArrayList;
import java.util.List;

public class BID {
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

    public void removerTransferencia(Transferencia transferencia) {
        transferencias.remove(transferencia);
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }
}