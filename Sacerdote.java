public class Sacerdote extends Eroe implements Poteri {
    public Sacerdote(String nome, int hp) {
        super(nome, hp);
    }

    @Override
    public int costoPotere() {
        return 2;
    }

    @Override
    public String getNomePotere() {
        return "Cura Minore";
    }

    @Override
    public void usaPotere(Player proprietario, Player avversario, GameController controller) {
        if (proprietario.getManaAttuale() < costoPotere()) {
            controller.log("Mana insufficiente per usare il potere del Sacerdote.");
            return;
        }
        proprietario.setManaAttuale(proprietario.getManaAttuale() - costoPotere());
        proprietario.getEroe().cura(2);
        controller.log(proprietario.getNome() + " usa il suo potere eroe: il proprio eroe recupera 2 HP.");
        controller.aggiornaInterfaccia();
    }

    public void usaPotereSuServitore(Player proprietario, Servitori bersaglio, GameController controller) {
        if (proprietario.getManaAttuale() < costoPotere()) {
            controller.log("Mana insufficiente per usare il potere del Sacerdote.");
            return;
        }
        proprietario.setManaAttuale(proprietario.getManaAttuale() - costoPotere());
        bersaglio.setDifesa(bersaglio.getDifesa() + 2);
        controller.log(proprietario.getNome() + " usa il suo potere eroe: " + bersaglio.getNome() + " recupera 2 salute.");
        controller.aggiornaInterfaccia();
    }
}