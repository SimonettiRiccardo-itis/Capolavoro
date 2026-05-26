import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private final Color backgroundColor;
    private final Color borderColor;
    private final int arc;

    public RoundedPanel(Color backgroundColor, Color borderColor, int arc) {
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.arc = arc;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0, 0, 0, 70));
        g2.fillRoundRect(4, 6, getWidth() - 8, getHeight() - 8, arc, arc);

        GradientPaint gp = new GradientPaint(
                0, 0, backgroundColor.brighter(),
                0, getHeight(), backgroundColor.darker()
        );
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, arc, arc);

        g2.setStroke(new BasicStroke(3f));
        g2.setColor(borderColor);
        g2.drawRoundRect(1, 1, getWidth() - 6, getHeight() - 6, arc, arc);

        g2.setColor(new Color(255, 236, 180, 45));
        g2.drawRoundRect(4, 4, getWidth() - 12, getHeight() - 12, arc - 8, arc - 8);

        g2.dispose();
        super.paintComponent(g);
    }
}