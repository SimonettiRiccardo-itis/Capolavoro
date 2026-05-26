import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HeroPanel extends RoundedPanel {
    private final JLabel nome = new JLabel();
    private final JLabel hp = new JLabel();
    private final JLabel mana = new JLabel();

    public HeroPanel() {
        super(new Color(57, 35, 24, 220), new Color(214, 166, 86), 34);

        setLayout(new FlowLayout(FlowLayout.LEFT, 18, 14));
        setBorder(new EmptyBorder(8, 18, 8, 18));
        setPreferredSize(new Dimension(900, 76));

        nome.setForeground(new Color(255, 228, 179));
        nome.setFont(new Font("Serif", Font.BOLD, 28));

        hp.setForeground(Color.WHITE);
        hp.setFont(new Font("SansSerif", Font.BOLD, 18));

        mana.setForeground(new Color(122, 216, 255));
        mana.setFont(new Font("SansSerif", Font.BOLD, 18));

        add(nome);
        add(hp);
        add(mana);
    }

    public void setData(Player player) {
        nome.setText(player.getEroe().getNome());
        hp.setText("   HP: " + player.getEroe().getHp());
        mana.setText("   Mana: " + player.getManaAttuale() + "/" + player.getManaMax());
    }
}