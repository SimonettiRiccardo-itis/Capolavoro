public class Turni {
    private final GameController controller;

    public Turni(GameController controller) {
        this.controller = controller;
    }

    public void inizioTurno(Player giocatore) {
        giocatore.nuovoTurno();
        int numeroTurno = controller.prossimoTurnoGlobale();
        controller.log("=== Turno " + numeroTurno + " : " + giocatore.getNome() + " ===");

        Carta pescata = giocatore.pescaCarta();

        if (pescata == null) {
            controller.log(giocatore.getNome() + " non pesca carte.");
        } else if (giocatore.getMano().contains(pescata)) {
            controller.log(giocatore.getNome() + " pesca una carta.");
        } else {
            controller.log(giocatore.getNome() + " brucia una carta per mano piena.");
        }
    }
}