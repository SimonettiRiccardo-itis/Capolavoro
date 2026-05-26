import java.util.ArrayList;
import java.util.List;

public class Campo {
    private ArrayList<Servitori> servitori;

    public Campo() {
        servitori = new ArrayList<>();
    }

    public boolean evocazione(Servitori s) {
        if (servitori.size() >= 7) {
            return false;
        }
        s.setPuoAttaccare(false);
        servitori.add(s);
        return true;
    }

    public List<Servitori> getServitori() {
        return servitori;
    }

    public boolean rimuoviServitore(Servitori s) {
        return servitori.remove(s);
    }
}
