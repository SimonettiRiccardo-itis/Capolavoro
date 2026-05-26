import javax.swing.*;
import java.awt.*;

public class CardComponent extends JPanel {
    private final Carta carta;
    private boolean selected;
    private boolean sleep;
    private boolean boardMode;

    public CardComponent(Carta carta) {
        this.carta = carta;
        this.boardMode = false;
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateSize();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public void setSleep(boolean sleep) {
        this.sleep = sleep;
        repaint();
    }

    public void setBoardMode(boolean boardMode) {
        this.boardMode = boardMode;
        updateSize();
        revalidate();
        repaint();
    }

    private void updateSize() {
        if (boardMode) {
            setPreferredSize(new Dimension(100, 128));
        } else {
            setPreferredSize(new Dimension(100, 150));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 4;
        int y = 4;
        int w = getWidth() - 8;
        int h = getHeight() - 8;

        g2.setColor(new Color(0, 0, 0, 70));
        g2.fillRoundRect(x + 4, y + 5, w - 2, h - 2, 20, 20);

        g2.setColor(new Color(228, 195, 145));
        g2.fillRoundRect(x, y, w, h, 20, 20);

        g2.setColor(selected ? new Color(83, 219, 255) : new Color(112, 68, 34));
        g2.setStroke(new BasicStroke(selected ? 4f : 3f));
        g2.drawRoundRect(x, y, w, h, 20, 20);

        int manaSize = boardMode ? 24 : 28;
        g2.setColor(new Color(255, 231, 194));
        g2.fillOval(x + 7, y + 7, manaSize, manaSize);

        g2.setColor(new Color(32, 22, 15));
        g2.setFont(new Font("SansSerif", Font.BOLD, boardMode ? 14 : 16));
        g2.drawString(String.valueOf(carta.getCosto()), x + 15, y + 25);

        g2.setFont(new Font("Serif", Font.BOLD, boardMode ? 11 : 13));
        drawCentered(g2, carta.getNome(), 10, getWidth() - 10, boardMode ? 55 : 88);

        g2.setFont(new Font("SansSerif", Font.PLAIN, boardMode ? 9 : 10));
        drawCentered(g2, cut(carta.getDescrizione(), boardMode ? 20 : 28), 10, getWidth() - 10, boardMode ? 73 : 108);

        if (carta instanceof Servitori s) {
            int orb = boardMode ? 24 : 28;
            int statY = boardMode ? getHeight() - 33 : getHeight() - 38;

            g2.setColor(new Color(178, 58, 36));
            g2.fillOval(8, statY, orb, orb);

            g2.setColor(new Color(61, 102, 177));
            g2.fillOval(getWidth() - orb - 8, statY, orb, orb);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, boardMode ? 13 : 15));
            g2.drawString(String.valueOf(s.getAttacco()), 16, statY + 17);
            g2.drawString(String.valueOf(s.getDifesa()), getWidth() - orb, statY + 17);
        } else if (carta instanceof Magie m) {
            int rx = 14;
            int ry = getHeight() - 34;
            int rw = getWidth() - 28;
            int rh = 18;

            g2.setColor(new Color(114, 53, 156));
            g2.fillRoundRect(rx, ry, rw, rh, 10, 10);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, boardMode ? 9 : 11));
            drawCentered(g2, m.getTipo().toUpperCase(), rx, rx + rw, ry + 13);
        }

        if (sleep) {
            g2.setColor(new Color(17, 17, 17, 85));
            g2.fillRoundRect(x, y, w, h, 20, 20);
            g2.setColor(new Color(255, 244, 180));
            g2.setFont(new Font("SansSerif", Font.BOLD, boardMode ? 14 : 18));
            drawCentered(g2, "Zzz", 0, getWidth(), getHeight() / 2 + 5);
        }

        g2.dispose();
    }

    private String cut(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }

    private void drawCentered(Graphics2D g2, String text, int x1, int x2, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int x = x1 + ((x2 - x1) - fm.stringWidth(text)) / 2;
        g2.drawString(text, Math.max(x1, x), y);
    }
}