public interface Poteri {
    public int costoPotere();
    public String getNomePotere();
    public void usaPotere(Player proprietario, Player avversario, GameController controller);
}
