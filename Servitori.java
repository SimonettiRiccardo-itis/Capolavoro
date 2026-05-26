public class Servitori extends Carta {
    private int attacco;
    private int difesa;
    private boolean puoAttaccare;

    public Servitori(String nome, int costo, String descrizione, int attacco, int difesa) {
        super(nome, costo, descrizione);
        this.attacco = attacco;
        this.difesa = difesa;
        this.puoAttaccare = false;
    }

    public int getAttacco() { 
        return attacco; 
    }
    public int getDifesa() {
         return difesa;
         }
    public void setAttacco(int attacco) {
         this.attacco = attacco;
         }
    public void setDifesa(int difesa) {
         this.difesa = difesa; 
        }
    public boolean isPuoAttaccare() {
         return puoAttaccare;
         }
    public void setPuoAttaccare(boolean puoAttaccare) {
         this.puoAttaccare = puoAttaccare;
         }

    public Servitori copia() {
        return new Servitori(getNome(), getCosto(), getDescrizione(), attacco, difesa);
    }
}
