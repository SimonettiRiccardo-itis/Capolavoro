import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class HandPanel extends JPanel {
    private final GameController controller;

    public HandPanel(GameController controller) {
        this.controller = controller;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, -20, 14));
        setPreferredSize(new Dimension(900, 180));
    }

    public void setCarte(List<Carta> carte) {
        removeAll();

        for (Carta carta : carte) {
            CardComponent comp = new CardComponent(carta);

            comp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    controller.giocaCartaDaMano(carta);
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
        g2.setColor(new Color(40, 26, 16, 150));
        g2.fillRoundRect(36, 18, getWidth() - 72, getHeight() - 30, 100, 100);

        g2.setColor(new Color(255, 219, 158));
        g2.setFont(new Font("Serif", Font.BOLD, 16));
        g2.drawString("Mano", 46, 18);

        g2.dispose();
    }
}