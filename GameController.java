import javax.swing.*;
import java.util.List;

public class GameController {
    private Player p1;
    private Player p2;
    private Player attuale;
    private Player avversario;
    private Turni turni;
    private HearthstoneFrame frame;
    private Carta cartaInAttesa;
    private Integer attaccanteSelezionato;
    private boolean partitaFinita;
    private boolean potereUsatoNelTurno;
    private boolean attesaBersaglioPotere;
    private boolean controCpu;
    private int turnoGlobale;

    public void avvia() {
        p1 = new Player("");
        p2 = new Player("");

        setUp setup = new setUp();
        setup.start(p1, p2);
        controCpu = setup.isControCpu();

        attuale = p1;
        avversario = p2;
        turnoGlobale = 0;

        turni = new Turni(this);
        frame = new HearthstoneFrame(this);

        turni.inizioTurno(attuale);
        potereUsatoNelTurno = false;
        attesaBersaglioPotere = false;

        aggiornaInterfaccia();
        frame.setVisible(true);
        log("La partita inizia. Tocca a " + attuale.getNome() + ".");
    }

    public Player getAttuale() {
        return attuale;
    }

    public Player getAvversario() {
        return avversario;
    }

    public boolean isTurnoGiocatore() {
        return true;
    }

    public Carta getCartaInAttesa() {
        return cartaInAttesa;
    }

    public Integer getAttaccanteSelezionato() {
        return attaccanteSelezionato;
    }

    public boolean isPartitaFinita() {
        return partitaFinita;
    }

    public boolean isPotereUsatoNelTurno() {
        return potereUsatoNelTurno;
    }

    public boolean isAttesaBersaglioPotere() {
        return attesaBersaglioPotere;
    }

    public boolean isControCpu() {
        return controCpu;
    }

    public int prossimoTurnoGlobale() {
        return ++turnoGlobale;
    }

    public int getTurnoGlobale() {
        return turnoGlobale;
    }

    public void giocaCartaDaMano(Carta carta) {
        if (partitaFinita) return;

        if (attesaBersaglioPotere) {
            log("Prima completa la selezione del bersaglio del potere eroe.");
            return;
        }

        if (carta.getCosto() > attuale.getManaAttuale()) {
            log(attuale.getNome() + " prova a giocare " + carta.getNome() + " ma non ha abbastanza mana.");
            return;
        }

        if (carta instanceof Servitori servitore) {
            if (!attuale.getCampo().evocazione(servitore)) {
                log(attuale.getNome() + " prova a evocare " + carta.getNome() + " ma il campo è pieno.");
                return;
            }

            attuale.setManaAttuale(attuale.getManaAttuale() - carta.getCosto());
            attuale.rimuoviCartaMano(carta);
            log(attuale.getNome() + " evoca " + carta.getNome() + " pagando " + carta.getCosto() + " mana.");
            aggiornaInterfaccia();

        } else if (carta instanceof Magie) {
            cartaInAttesa = carta;
            attaccanteSelezionato = null;
            log(attuale.getNome() + " prepara la magia " + carta.getNome() + ". Seleziona un bersaglio.");
        }
    }

    public void selezionaMioServitore(int index) {
        if (partitaFinita) return;

        if (attesaBersaglioPotere) {
            usaPotereSuMioServitore(index);
            return;
        }

        if (cartaInAttesa instanceof Magie) {
            risolviMagiaSuServitore(attuale, index);
            return;
        }

        attaccanteSelezionato = index;

        if (index >= 0 && index < attuale.getCampo().getServitori().size()) {
            Servitori s = attuale.getCampo().getServitori().get(index);
            log(attuale.getNome() + " seleziona " + s.getNome() + " per attaccare.");
        }

        aggiornaInterfaccia();
    }

    public void selezionaServitoreNemico(int index) {
        if (partitaFinita) return;

        if (attesaBersaglioPotere) {
            log("Questo potere eroe non può bersagliare i servitori nemici.");
            return;
        }

        if (cartaInAttesa instanceof Magie) {
            risolviMagiaSuServitore(avversario, index);
            return;
        }

        if (attaccanteSelezionato == null) return;
        if (attaccanteSelezionato < 0 || attaccanteSelezionato >= attuale.getCampo().getServitori().size()) return;
        if (index < 0 || index >= avversario.getCampo().getServitori().size()) return;

        Servitori att = attuale.getCampo().getServitori().get(attaccanteSelezionato);

        if (!att.isPuoAttaccare()) {
            log(att.getNome() + " non può attaccare in questo turno.");
            attaccanteSelezionato = null;
            aggiornaInterfaccia();
            return;
        }

        Servitori def = avversario.getCampo().getServitori().get(index);

        int dannoInflitto = att.getAttacco();
        int dannoSubito = def.getAttacco();

        def.setDifesa(def.getDifesa() - dannoInflitto);
        att.setDifesa(att.getDifesa() - dannoSubito);
        att.setPuoAttaccare(false);

        StringBuilder msg = new StringBuilder();
        msg.append(attuale.getNome())
           .append(": ")
           .append(att.getNome())
           .append(" attacca ")
           .append(def.getNome())
           .append(" e infligge ")
           .append(dannoInflitto)
           .append(" danni; ")
           .append(def.getNome())
           .append(" contrattacca e infligge ")
           .append(dannoSubito)
           .append(" danni.");

        if (def.getDifesa() <= 0) {
            avversario.getCampo().rimuoviServitore(def);
            msg.append(" ").append(def.getNome()).append(" viene distrutto.");
        } else {
            msg.append(" ").append(def.getNome()).append(" resta a ").append(def.getDifesa()).append(" salute.");
        }

        if (att.getDifesa() <= 0) {
            attuale.getCampo().rimuoviServitore(att);
            msg.append(" ").append(att.getNome()).append(" viene distrutto.");
        } else {
            msg.append(" ").append(att.getNome()).append(" resta a ").append(att.getDifesa()).append(" salute.");
        }

        log(msg.toString());
        attaccanteSelezionato = null;
        aggiornaInterfaccia();
        controllaFinePartita();
    }

    public void clicEroeGiocatore() {
        if (partitaFinita) return;

        if (attesaBersaglioPotere) {
            completaPotereSacerdoteSuEroe();
            return;
        }

        if (cartaInAttesa instanceof Magie magia) {
            magia.applicaSuEroe(attuale.getEroe());
            attuale.setManaAttuale(attuale.getManaAttuale() - magia.getCosto());
            attuale.rimuoviCartaMano(magia);

            String azione = "attacco".equalsIgnoreCase(magia.getTipo())
                    ? "infligge " + magia.getValore() + " danni"
                    : "cura di " + magia.getValore();

            log(attuale.getNome() + " lancia " + magia.getNome() +
                    " sul proprio eroe e " + azione +
                    ". HP attuali: " + attuale.getEroe().getHp() + ".");

            cartaInAttesa = null;
            aggiornaInterfaccia();
        }
    }

    public void clicEroeNemico() {
        if (partitaFinita) return;

        if (attesaBersaglioPotere) {
            log("Questo potere eroe non può bersagliare l'eroe nemico.");
            return;
        }

        if (cartaInAttesa instanceof Magie magia) {
            magia.applicaSuEroe(avversario.getEroe());
            attuale.setManaAttuale(attuale.getManaAttuale() - magia.getCosto());
            attuale.rimuoviCartaMano(magia);

            String azione = "attacco".equalsIgnoreCase(magia.getTipo())
                    ? "infligge " + magia.getValore() + " danni"
                    : "cura di " + magia.getValore();

            log(attuale.getNome() + " lancia " + magia.getNome() +
                    " sull'eroe nemico e " + azione +
                    ". HP rimanenti: " + avversario.getEroe().getHp() + ".");

            cartaInAttesa = null;
            aggiornaInterfaccia();
            controllaFinePartita();
            return;
        }

        if (attaccanteSelezionato == null) return;
        if (attaccanteSelezionato < 0 || attaccanteSelezionato >= attuale.getCampo().getServitori().size()) return;

        Servitori att = attuale.getCampo().getServitori().get(attaccanteSelezionato);

        if (!att.isPuoAttaccare()) {
            log(att.getNome() + " non può attaccare in questo turno.");
            attaccanteSelezionato = null;
            aggiornaInterfaccia();
            return;
        }

        if (!avversario.getCampo().getServitori().isEmpty()) {
            log(attuale.getNome() + " prova ad attaccare l'eroe nemico con " + att.getNome()
                    + " ma ci sono ancora servitori sul campo avversario.");
            attaccanteSelezionato = null;
            aggiornaInterfaccia();
            return;
        }

        avversario.getEroe().subisciDanno(att.getAttacco());
        att.setPuoAttaccare(false);

        log(attuale.getNome() + ": " + att.getNome() +
                " attacca l'eroe nemico " + avversario.getEroe().getNome() +
                " e infligge " + att.getAttacco() +
                " danni. HP rimanenti: " + avversario.getEroe().getHp() + ".");

        attaccanteSelezionato = null;
        aggiornaInterfaccia();
        controllaFinePartita();
    }

    private void risolviMagiaSuServitore(Player proprietarioBersaglio, int index) {
        if (!(cartaInAttesa instanceof Magie magia)) return;

        List<Servitori> lista = proprietarioBersaglio.getCampo().getServitori();
        if (index < 0 || index >= lista.size()) return;

        Servitori target = lista.get(index);
        magia.applicaSuServitore(target);

        attuale.setManaAttuale(attuale.getManaAttuale() - magia.getCosto());
        attuale.rimuoviCartaMano(magia);

        String azione = "attacco".equalsIgnoreCase(magia.getTipo())
                ? "infligge " + magia.getValore() + " danni"
                : "cura di " + magia.getValore();

        StringBuilder msg = new StringBuilder();
        msg.append(attuale.getNome())
           .append(" lancia ")
           .append(magia.getNome())
           .append(" su ")
           .append(target.getNome())
           .append(" e ")
           .append(azione)
           .append(".");

        if (target.getDifesa() <= 0) {
            proprietarioBersaglio.getCampo().rimuoviServitore(target);
            msg.append(" ").append(target.getNome()).append(" viene distrutto.");
        } else {
            msg.append(" ").append(target.getNome()).append(" ora ha ").append(target.getDifesa()).append(" salute.");
        }

        log(msg.toString());
        cartaInAttesa = null;
        aggiornaInterfaccia();
    }

    public void usaPotereEroe() {
        if (partitaFinita) return;

        if (attesaBersaglioPotere) {
            log("Seleziona un tuo servitore o il tuo eroe per completare il potere.");
            return;
        }

        if (potereUsatoNelTurno) {
            log("Hai già usato il potere eroe in questo turno.");
            return;
        }

        if (!(attuale.getEroe() instanceof Poteri potere)) return;

        if (attuale.getManaAttuale() < potere.costoPotere()) {
            log("Mana insufficiente per usare il potere eroe.");
            return;
        }

        if (attuale.getEroe() instanceof Sacerdote) {
            cartaInAttesa = null;
            attaccanteSelezionato = null;
            attesaBersaglioPotere = true;
            log(attuale.getNome() + " sta usando il suo potere eroe. Seleziona il tuo eroe o un tuo servitore da curare.");
            aggiornaInterfaccia();
            return;
        }

        potere.usaPotere(attuale, avversario, this);
        potereUsatoNelTurno = true;
        attesaBersaglioPotere = false;
        aggiornaInterfaccia();
    }

    private void usaPotereSuMioServitore(int index) {
        if (!(attuale.getEroe() instanceof Sacerdote sacerdote)) return;

        List<Servitori> lista = attuale.getCampo().getServitori();
        if (index < 0 || index >= lista.size()) return;

        Servitori target = lista.get(index);
        sacerdote.usaPotereSuServitore(attuale, target, this);
        potereUsatoNelTurno = true;
        attesaBersaglioPotere = false;

        log(attuale.getNome() + " usa il suo potere eroe su " + target.getNome() +
                ". Salute attuale del servitore: " + target.getDifesa() + ".");
        aggiornaInterfaccia();
    }

    private void completaPotereSacerdoteSuEroe() {
        if (!(attuale.getEroe() instanceof Sacerdote sacerdote)) return;

        sacerdote.usaPotere(attuale, avversario, this);
        potereUsatoNelTurno = true;
        attesaBersaglioPotere = false;

        log(attuale.getNome() + " usa il suo potere eroe sul proprio eroe. HP attuali: " +
                attuale.getEroe().getHp() + ".");
        aggiornaInterfaccia();
    }

    public void fineTurno() {
        if (partitaFinita) return;

        log("=== " + attuale.getNome() + " termina il turno ===");

        if (controCpu) {
            scambiaTurno();
            eseguiTurnoCpu();

            if (!partitaFinita) {
                scambiaTurno();
                turni.inizioTurno(attuale);
                potereUsatoNelTurno = false;
                attesaBersaglioPotere = false;
                aggiornaInterfaccia();
            }
        } else {
            scambiaTurno();
            frame.mostraCambioTurno(attuale.getNome());
            turni.inizioTurno(attuale);
            potereUsatoNelTurno = false;
            attesaBersaglioPotere = false;
            aggiornaInterfaccia();
        }
    }

    private void scambiaTurno() {
        Player temp = attuale;
        attuale = avversario;
        avversario = temp;
        cartaInAttesa = null;
        attaccanteSelezionato = null;
        attesaBersaglioPotere = false;
    }

    private void eseguiTurnoCpu() {
        turni.inizioTurno(attuale);
        potereUsatoNelTurno = false;

        for (Carta carta : List.copyOf(attuale.getMano())) {
            if (carta.getCosto() <= attuale.getManaAttuale()) {

                if (carta instanceof Servitori servitore) {
                    if (attuale.getCampo().evocazione(servitore)) {
                        attuale.setManaAttuale(attuale.getManaAttuale() - carta.getCosto());
                        attuale.rimuoviCartaMano(carta);
                        log("CPU evoca " + carta.getNome() + " pagando " + carta.getCosto() + " mana.");
                        aggiornaInterfaccia();
                        break;
                    }
                }

                else if (carta instanceof Magie magia) {
                    if ("attacco".equalsIgnoreCase(magia.getTipo())) {
                        magia.applicaSuEroe(avversario.getEroe());
                        attuale.setManaAttuale(attuale.getManaAttuale() - magia.getCosto());
                        attuale.rimuoviCartaMano(carta);

                        log("CPU lancia " + magia.getNome() + " sul tuo eroe e infligge " +
                                magia.getValore() + " danni. HP rimanenti: " +
                                avversario.getEroe().getHp() + ".");

                        aggiornaInterfaccia();
                        controllaFinePartita();
                        if (partitaFinita) return;
                        break;
                    }

                    else if ("cura".equalsIgnoreCase(magia.getTipo())) {
                        magia.applicaSuEroe(attuale.getEroe());
                        attuale.setManaAttuale(attuale.getManaAttuale() - magia.getCosto());
                        attuale.rimuoviCartaMano(carta);

                        log("CPU lancia " + magia.getNome() + " sul proprio eroe e recupera " +
                                magia.getValore() + " HP. HP attuali: " +
                                attuale.getEroe().getHp() + ".");

                        aggiornaInterfaccia();
                        break;
                    }
                }
            }
        }

        if (!potereUsatoNelTurno &&
                attuale.getEroe() instanceof Poteri potere &&
                attuale.getManaAttuale() >= potere.costoPotere()) {
            potere.usaPotere(attuale, avversario, this);
            potereUsatoNelTurno = true;
            aggiornaInterfaccia();
            controllaFinePartita();
            if (partitaFinita) return;
        }

        for (Servitori s : List.copyOf(attuale.getCampo().getServitori())) {
            if (!s.isPuoAttaccare()) {
                continue;
            }

            if (!avversario.getCampo().getServitori().isEmpty()) {
                Servitori bersaglio = avversario.getCampo().getServitori().get(0);

                int dannoInflitto = s.getAttacco();
                int dannoSubito = bersaglio.getAttacco();

                bersaglio.setDifesa(bersaglio.getDifesa() - dannoInflitto);
                s.setDifesa(s.getDifesa() - dannoSubito);
                s.setPuoAttaccare(false);

                StringBuilder msg = new StringBuilder();
                msg.append("CPU: ")
                   .append(s.getNome())
                   .append(" attacca ")
                   .append(bersaglio.getNome())
                   .append(" e infligge ")
                   .append(dannoInflitto)
                   .append(" danni; ")
                   .append(bersaglio.getNome())
                   .append(" contrattacca e infligge ")
                   .append(dannoSubito)
                   .append(" danni.");

                if (bersaglio.getDifesa() <= 0) {
                    avversario.getCampo().rimuoviServitore(bersaglio);
                    msg.append(" ").append(bersaglio.getNome()).append(" viene distrutto.");
                } else {
                    msg.append(" ").append(bersaglio.getNome()).append(" resta a ")
                       .append(bersaglio.getDifesa()).append(" salute.");
                }

                if (s.getDifesa() <= 0) {
                    attuale.getCampo().rimuoviServitore(s);
                    msg.append(" ").append(s.getNome()).append(" viene distrutto.");
                } else {
                    msg.append(" ").append(s.getNome()).append(" resta a ")
                       .append(s.getDifesa()).append(" salute.");
                }

                log(msg.toString());
                aggiornaInterfaccia();
                controllaFinePartita();
                if (partitaFinita) return;

            } else {
                avversario.getEroe().subisciDanno(s.getAttacco());
                s.setPuoAttaccare(false);

                log("CPU: " + s.getNome() + " attacca l'eroe " +
                        avversario.getEroe().getNome() +
                        " e infligge " + s.getAttacco() +
                        " danni. HP rimanenti: " + avversario.getEroe().getHp() + ".");

                aggiornaInterfaccia();
                controllaFinePartita();
                if (partitaFinita) return;
            }
        }

        log("=== La CPU termina il turno ===");
    }

    public void controllaFinePartita() {
        if (p1.getEroe().getHp() <= 0) {
            partitaFinita = true;
            log("Partita finita: " + p2.getNome() + " vince.");
            JOptionPane.showMessageDialog(frame, p2.getNome() + " ha vinto!");
        } else if (p2.getEroe().getHp() <= 0) {
            partitaFinita = true;
            log("Partita finita: " + p1.getNome() + " vince.");
            JOptionPane.showMessageDialog(frame, p1.getNome() + " ha vinto!");
        }
    }

    public void log(String testo) {
        if (frame != null) {
            frame.addLog(testo);
        }
    }

    public void aggiornaInterfaccia() {
        if (frame != null) {
            frame.refresh();
        }
    }
}