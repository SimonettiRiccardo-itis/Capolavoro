import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HearthstoneFrame extends JFrame {
    private final GameController controller;
    private HeroPanel heroTop;
    private HeroPanel heroBottom;
    private BoardRow enemyBoard;
    private BoardRow myBoard;
    private HandPanel handPanel;
    private JTextArea logArea;
    private JScrollPane logScroll;
    private JLabel infoLabel;

    public HearthstoneFrame(GameController controller) {
        this.controller = controller;
        setTitle("Arcane Legends");
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new TavernPanel());
        getContentPane().setLayout(new BorderLayout(16, 16));
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(14, 14, 14, 14));

        infoLabel = new JLabel("Locanda pronta");
        infoLabel.setForeground(new Color(255, 221, 162));
        infoLabel.setFont(new Font("Serif", Font.BOLD, 30));
        add(infoLabel, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(10, 18));
        center.setOpaque(false);

        heroTop = new HeroPanel();
        heroBottom = new HeroPanel();
        enemyBoard = new BoardRow(false, controller);
        myBoard = new BoardRow(true, controller);
        handPanel = new HandPanel(controller);

        center.add(heroTop, BorderLayout.NORTH);

        JPanel middle = new JPanel(new BorderLayout(10, 10));
        middle.setOpaque(false);
        middle.add(enemyBoard, BorderLayout.NORTH);

        JPanel lower = new JPanel(new BorderLayout(10, 10));
        lower.setOpaque(false);
        lower.add(myBoard, BorderLayout.NORTH);
        lower.add(handPanel, BorderLayout.SOUTH);

        middle.add(lower, BorderLayout.SOUTH);
        center.add(middle, BorderLayout.CENTER);
        center.add(heroBottom, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);
        add(buildRightPanel(), BorderLayout.EAST);

        heroTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        heroBottom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        heroTop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.clicEroeNemico();
            }
        });

        heroBottom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.clicEroeGiocatore();
            }
        });

        setVisible(true);
    }

    private JPanel buildRightPanel() {
        JPanel side = new RoundedPanel(new Color(38, 22, 14, 215), new Color(182, 126, 65), 28);
        side.setPreferredSize(new Dimension(330, 0));
        side.setLayout(new BorderLayout(10, 10));
        side.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel buttons = new JPanel(new GridLayout(0, 1, 0, 10));
        buttons.setOpaque(false);

        JButton heroPower = goldButton("Potere eroe");
        JButton endTurn = goldButton("Fine turno");

        heroPower.addActionListener(e -> controller.usaPotereEroe());
        endTurn.addActionListener(e -> controller.fineTurno());

        buttons.add(heroPower);
        buttons.add(endTurn);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logArea.setFont(new Font("Serif", Font.PLAIN, 15));
        logArea.setForeground(new Color(255, 238, 210));
        logArea.setOpaque(false);
        logArea.setMargin(new Insets(8, 8, 8, 8));

        logScroll = new JScrollPane(logArea);
        logScroll.setOpaque(false);
        logScroll.getViewport().setOpaque(false);
        logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        logScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(199, 148, 83)), "Cronologia partita"));

        side.add(buttons, BorderLayout.NORTH);
        side.add(logScroll, BorderLayout.CENTER);

        return side;
    }

    private JButton goldButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 17));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(174, 110, 31));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(247, 212, 147), 2),
                new EmptyBorder(10, 12, 10, 12)));
        b.setFocusPainted(false);
        return b;
    }

    public void addLog(String text) {
        String separatore = logArea.getText().isEmpty() ? "" : "\n";
        logArea.setText(logArea.getText() + separatore + text);
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = logScroll.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }

    public void mostraCambioTurno(String nomeGiocatore) {
        JOptionPane.showMessageDialog(this, "Passa il dispositivo a " + nomeGiocatore + " e premi OK per iniziare il turno.");
    }

    public void refresh() {
        Player attuale = controller.getAttuale();
        Player avversario = controller.getAvversario();
        String modalita = controller.isControCpu() ? "1 vs CPU" : "1 vs 1";
        infoLabel.setText(modalita + " - Turno di " + attuale.getNome() );

        heroTop.setData(avversario);
        heroBottom.setData(attuale);
        enemyBoard.setServitori(avversario.getCampo().getServitori(), controller.getAttaccanteSelezionato());
        myBoard.setServitori(attuale.getCampo().getServitori(), controller.getAttaccanteSelezionato());
        handPanel.setCarte(attuale.getMano());

        repaint();
        revalidate();
    }
}