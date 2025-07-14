import java.util.ArrayList;
import java.util.List;

public class Campeonato {
    private String nomeCampeonato;
    private List<Time> times;

    public Campeonato(String nomeCampeonato, List<Time> times) {
        this.nomeCampeonato = nomeCampeonato;
        if (times == null) {
            this.times = new ArrayList<>();
        } else {
            this.times = times;
        }
    }

    public String getNomeCampeonato() {
        return nomeCampeonato;
    }

    public void setNomeCampeonato(String nomeCampeonato) {
        this.nomeCampeonato = nomeCampeonato;
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    public void removerTime(Time time) {
        times.remove(time);
    }
}