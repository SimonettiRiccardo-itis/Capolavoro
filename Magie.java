public class Magie extends Carta {
    private int valore;
    private String tipo;

    public Magie(String nome, int costo, String descrizione, int valore, String tipo) {
        super(nome, costo, descrizione);
        this.valore = valore;
        this.tipo = tipo;
    }

    public int getValore() { return valore; }
    public String getTipo() { return tipo; }

    public void applicaSuServitore(Servitori bersaglio) {
        if ("attacco".equalsIgnoreCase(tipo)) {
            bersaglio.setDifesa(bersaglio.getDifesa() - valore);
        } else if ("cura".equalsIgnoreCase(tipo)) {
            bersaglio.setDifesa(bersaglio.getDifesa() + valore);
        }
    }

    public void applicaSuEroe(Eroe bersaglio) {
        if ("attacco".equalsIgnoreCase(tipo)) {
            bersaglio.subisciDanno(valore);
        } else if ("cura".equalsIgnoreCase(tipo)) {
            bersaglio.cura(valore);
        }
    }

    public Magie copia() {
        return new Magie(getNome(), getCosto(), getDescrizione(), valore, tipo);
    }
}
