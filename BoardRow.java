import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BoardRow extends JPanel {
    private final boolean miaRiga;
    private final GameController controller;

    public BoardRow(boolean miaRiga, GameController controller) {
        this.miaRiga = miaRiga;
        this.controller = controller;

        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 14, 8));
        setPreferredSize(new Dimension(900, 130));
    }

    public void setServitori(List<Servitori> servitori, Integer selected) {
        removeAll();

        for (int i = 0; i < servitori.size(); i++) {
            final int idx = i;
            Servitori s = servitori.get(i);

            CardComponent comp = new CardComponent(s);
            comp.setBoardMode(true);
            comp.setSleep(miaRiga && !s.isPuoAttaccare());
            comp.setSelected(miaRiga && selected != null && selected == i);

            comp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (miaRiga) {
                        controller.selezionaMioServitore(idx);
                    } else {
                        controller.selezionaServitoreNemico(idx);
                    }
                }
            });

            add(comp);
        }

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(new Color(255, 234, 189, 22));
        g2.fillRoundRect(6, 8, getWidth() - 12, getHeight() - 16, 28, 28);

        g2.setColor(new Color(183, 133, 74, 90));
        g2.drawRoundRect(6, 8, getWidth() - 12, getHeight() - 16, 28, 28);

        g2.setColor(new Color(255, 224, 175));
        g2.setFont(new Font("Serif", Font.BOLD, 15));
        g2.drawString(miaRiga ? "Campo alleato" : "Campo avversario", 18, 20);

        g2.dispose();
    }
}