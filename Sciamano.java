public class Sciamano extends Eroe implements Poteri {
    public Sciamano(String nome, int hp) {
        super(nome, hp);
    }

    @Override
    public int costoPotere() {
        return 2;
    }

    @Override
    public String getNomePotere() {
        return "Scarica";
    }

    @Override
    public void usaPotere(Player proprietario, Player avversario, GameController controller) {
        if (proprietario.getManaAttuale() < costoPotere()) {
            controller.log("Mana insufficiente per usare il potere dello Sciamano.");
            return;
        }
        proprietario.setManaAttuale(proprietario.getManaAttuale() - costoPotere());
        avversario.getEroe().subisciDanno(1);
        proprietario.getEroe().cura(1);
        controller.log(proprietario.getNome() + " usa il suo potere eroe: infligge 1 danno al nemico e cura il proprio eroe di 1.");
        controller.aggiornaInterfaccia();
        controller.controllaFinePartita();
    }
}