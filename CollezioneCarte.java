import java.util.ArrayList;

public class CollezioneCarte {
    public static ArrayList<Carta> creaCollezioneBase() {
        ArrayList<Carta> collezioneCarte = new ArrayList<>();
        collezioneCarte.add(new Servitori("Gino il pasticcino", 1, "E' proprio un pasticcino", 1, 1));
        collezioneCarte.add(new Servitori("Coccodrillo di fiume", 2, "AAAAAAAAAHHHH", 2, 3));
        collezioneCarte.add(new Servitori("Cronosignore Epocus", 6, "Urca!", 7, 6));
        collezioneCarte.add(new Magie("Palla di fuoco", 4, "Infligge 6 danni", 6, "attacco"));
        collezioneCarte.add(new Magie("Rimarginazione", 2, "Cura 3", 3, "cura"));
        collezioneCarte.add(new Magie("Tiro mirato", 3, "Infligge 3 danni", 3, "attacco"));
        collezioneCarte.add(new Servitori("Custode del sole Tarim", 9, "E' bello forte", 9, 9));
        collezioneCarte.add(new Servitori("Compagno d'armi", 2, "Compa", 3, 2));
        collezioneCarte.add(new Servitori("Sceriffo", 4, "Lo sceriffo di Meccannia", 3, 5));
        collezioneCarte.add(new Servitori("Kamikaze", 5, "Attenzione!", 9, 1));
        collezioneCarte.add(new Servitori("Zefris il Grande", 4, "Zefris magno", 4, 3));
        collezioneCarte.add(new Servitori("Neomelodico Blu Profondo", 5, "Esperto del Codice Penale", 4, 4));
        collezioneCarte.add(new Servitori("Signor Rho", 10, "Il piu forte della sua epoca", 10, 10));
        collezioneCarte.add(new Servitori("Diego il rider", 8, "W Diego", 5, 8));
        collezioneCarte.add(new Magie("Pozione di cura", 5, "Cura 5", 5, "cura"));
        collezioneCarte.add(new Servitori("Amleto", 5, "Essere o non essere", 5, 6));
        collezioneCarte.add(new Servitori("Alice nel paese delle meraviglie", 3, "Fa brutte battute", 3,4 ));
        collezioneCarte.add(new Servitori("Sherlock Holmes", 8, " odio Watson", 9, 5));
        collezioneCarte.add(new Magie("Ruba anima", 8, "Rubi l'anima al bersaglio", 9, "attacco"));
        collezioneCarte.add(new Magie("Ralsei", 4, "Cura 6", 6, "cura"));
        return collezioneCarte;
    }

    public static ArrayList<Carta> creaMazzoBase() {
        ArrayList<Carta> base = creaCollezioneBase();
        ArrayList<Carta> mazzo = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (Carta c : base) {
                if (c instanceof Servitori s) mazzo.add(s.copia());
                else if (c instanceof Magie m) mazzo.add(m.copia());
            }
        }
        return mazzo;
    }
}
