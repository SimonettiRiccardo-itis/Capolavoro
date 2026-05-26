public abstract class Eroe {
    private String nome;
    private int hp;

    public Eroe(String nome, int hp) {
        this.nome = nome;
        this.hp = hp;
    }

    public String getNome() { return nome; }
    public int getHp() { return hp; }
    public void setNome(String nome) { this.nome = nome; }
    public void setHp(int hp) { this.hp = Math.max(0, hp); }

    public void subisciDanno(int danno) {
        this.hp -= danno;
        if (this.hp < 0){

         this.hp = 0;
        }
    }

    public void cura(int quantita) {
        this.hp += quantita;
        if (this.hp > 30) {
            this.hp = 30;
        }
    }
}
