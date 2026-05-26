public class CavaliereDellaMorte extends Eroe implements Poteri {
    public CavaliereDellaMorte(String nome, int hp) {
        super(nome, hp);
    }

    @Override
    public int costoPotere() {
        return 2;
    }

    @Override
    public String getNomePotere() {
        return "Colpo Mortale";
    }

    @Override
    public void usaPotere(Player proprietario, Player avversario, GameController controller) {
        if (proprietario.getManaAttuale() < costoPotere()) {
            controller.log("Mana insufficiente per usare il potere del Cavaliere della Morte.");
            return;
        }
        proprietario.setManaAttuale(proprietario.getManaAttuale() - costoPotere());
        avversario.getEroe().subisciDanno(2);
        controller.log(proprietario.getNome() + " usa il suo potere eroe: infligge 2 danni all'eroe nemico.");
        controller.aggiornaInterfaccia();
        controller.controllaFinePartita();
    }
}