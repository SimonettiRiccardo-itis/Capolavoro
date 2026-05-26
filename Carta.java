public abstract class Carta {
    private String nome;
    private int costo;
    private String descrizione;

    public Carta(String nome, int costo, String descrizione) {
        this.nome = nome;
        this.costo = costo;
        this.descrizione = descrizione;
    }

    public String getNome() {
         return nome;
         }
    public int getCosto() {
         return costo;
         }
    public String getDescrizione() {
         return descrizione;
         }
    public void setNome(String nome) {
         this.nome = nome; 
        }
    public void setCosto(int costo) {
         this.costo = costo; 
        }
    public void setDescrizione(String descrizione) {
         this.descrizione = descrizione;
         }
}
