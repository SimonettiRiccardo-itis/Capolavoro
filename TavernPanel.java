import javax.swing.*;
import java.awt.*;

public class TavernPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint bg = new GradientPaint(
                0, 0, new Color(28, 17, 14),
                0, getHeight(), new Color(84, 48, 27)
        );
        g2.setPaint(bg);
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(new Color(87, 52, 28, 120));
        for (int y = 0; y < getHeight(); y += 42) {
            g2.fillRect(0, y, getWidth(), 20);
        }

        g2.setColor(new Color(255, 187, 77, 48));
        g2.fillOval(-120, getHeight() - 260, 360, 360);
        g2.fillOval(getWidth() - 260, 70, 300, 300);

        g2.setColor(new Color(90, 170, 255, 40));
        g2.fillOval(getWidth() / 2 - 180, getHeight() / 2 - 160, 360, 260);

        g2.setStroke(new BasicStroke(6f));
        g2.setColor(new Color(50, 30, 18, 170));
        g2.drawRoundRect(16, 16, getWidth() - 32, getHeight() - 32, 42, 42);

        g2.setStroke(new BasicStroke(2f));
        g2.setColor(new Color(232, 188, 124, 65));
        g2.drawRoundRect(24, 24, getWidth() - 48, getHeight() - 48, 36, 36);

        g2.dispose();
    }
}