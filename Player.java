import java.util.ArrayList;

public class Player {
    private String nome;
    private ArrayList<Carta> mano;
    private int turno;
    private int manaMax;
    private int manaAttuale;
    private Campo campo;
    private Eroe eroe;
    private Mazzo mazzo;

    public Player(String nome) {
        this.nome = nome;
        this.mano = new ArrayList<>();
        this.turno = 0;
        this.manaMax = 0;
        this.manaAttuale = 0;
        this.campo = new Campo();
        this.eroe = null;
        this.mazzo = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public int getTurno() {
        return turno;
    }

    public int getManaMax() {
        return manaMax;
    }

    public int getManaAttuale() {
        return manaAttuale;
    }

    public void setManaAttuale(int manaAttuale) {
        this.manaAttuale = manaAttuale;
    }

    public Campo getCampo() {
        return campo;
    }

    public Eroe getEroe() {
        return eroe;
    }

    public void setEroe(Eroe eroe) {
        this.eroe = eroe;
    }

    public Mazzo getMazzo() {
        return mazzo;
    }

    public void setMazzo(Mazzo mazzo) {
        this.mazzo = mazzo;
    }

    public void nuovoTurno() {
        turno++;
        if (manaMax < 10)
            manaMax++;
        manaAttuale = manaMax;
        for (Servitori s : campo.getServitori()) {
            s.setPuoAttaccare(true);
        }
    }

    public Carta pescaCarta() {
        if (mazzo == null)
            return null;
        Carta pescata = mazzo.pesca();
        if (pescata == null)
            return null;
        if (mano.size() >= 7)
            return pescata;
        mano.add(pescata);
        return pescata;
    }

    public boolean rimuoviCartaMano(Carta carta) {
        return mano.remove(carta);
    }
}
