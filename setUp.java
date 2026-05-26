import javax.swing.JOptionPane;

public class setUp {
    private boolean controCpu;

    public boolean isControCpu() {
        return controCpu;
    }

    public void start(Player p1, Player p2) {
        Object[] modalita = {"1 vs 1", "1 vs CPU"};
        String sceltaModalita = (String) JOptionPane.showInputDialog(
                null,
                "Scegli la modalità di gioco:",
                "Modalità",
                JOptionPane.PLAIN_MESSAGE,
                null,
                modalita,
                modalita[1]
        );
        if (sceltaModalita == null) sceltaModalita = "1 vs CPU";
        controCpu = sceltaModalita.equals("1 vs CPU");

        String nome1 = JOptionPane.showInputDialog(null, "Nome del primo giocatore:", "Giocatore 1");
        if (nome1 == null || nome1.isBlank()) nome1 = "Giocatore 1";
        p1.setNome(nome1);

        if (controCpu) {
            p2.setNome("CPU");
        } else {
            String nome2 = JOptionPane.showInputDialog(null, "Nome del secondo giocatore:", "Giocatore 2");
            if (nome2 == null || nome2.isBlank()) nome2 = "Giocatore 2";
            p2.setNome(nome2);
        }

        p1.setEroe(scegliEroePer(p1.getNome()));
        if (controCpu) {
            p2.setEroe(new CavaliereDellaMorte("Nemico", 30));
        } else {
            p2.setEroe(scegliEroePer(p2.getNome()));
        }

        p1.setMazzo(new Mazzo(CollezioneCarte.creaMazzoBase()));
        p2.setMazzo(new Mazzo(CollezioneCarte.creaMazzoBase()));
        p1.getMazzo().mescola();
        p2.getMazzo().mescola();
        for (int i = 0; i < 3; i++) {
            p1.pescaCarta();
            p2.pescaCarta();
        }
    }

    private Eroe scegliEroePer(String nomeGiocatore) {
        String[] eroi = {"Sacerdote", "Sciamano", "CavaliereDellaMorte"};
        String scelta = (String) JOptionPane.showInputDialog(
                null,
                "Scegli l'eroe di " + nomeGiocatore + ":",
                "Eroe",
                JOptionPane.PLAIN_MESSAGE,
                null,
                eroi,
                eroi[0]
        );
        if (scelta == null) scelta = "Sacerdote";

        return switch (scelta) {
            case "Sciamano" -> new Sciamano("Thrall-ish", 30);
            case "CavaliereDellaMorte" -> new CavaliereDellaMorte("Arthas-ish", 30);
            default -> new Sacerdote("Anduin-ish", 30);
        };
    }
}
