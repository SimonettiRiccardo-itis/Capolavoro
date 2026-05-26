import java.util.*;

public class Mazzo {
    private ArrayList<Carta> carte;

    public Mazzo() {
        this.carte = new ArrayList<>();
    }

    public Mazzo(List<Carta> carteIniziali) {
        this.carte = new ArrayList<>(carteIniziali);
    }

    public void aggiungiCarta(Carta c) {
        carte.add(c);
    }

    public void mescola() {
        Collections.shuffle(carte);
    }

    public boolean isVuoto() {
        return carte.isEmpty();
    }

    public Carta pesca() {
        if (carte.isEmpty()){    
            return null;
        }else{
             return carte.remove(0);
        }
    }

    public int size() {
        return carte.size();
    }
}
