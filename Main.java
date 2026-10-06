import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main extends JFrame {
    public Main() {
        setTitle("Ludo Game with Common Dice");
        setSize(700, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main layout
        setLayout(new BorderLayout());
        
        LudoBoardPanel boardPanel = new LudoBoardPanel();
        add(boardPanel, BorderLayout.CENTER);
        
        // Control Panel for Dice & Turn
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.DARK_GRAY);
        
        JLabel statusLabel = new JLabel("Player Red's Turn. Roll the Dice!");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JButton rollButton = new JButton("Roll Dice");
        rollButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int diceValue = boardPanel.rollDice();
                statusLabel.setText(boardPanel.getCurrentPlayerName() + " rolled a " + diceValue + "!");
            }
        });
        
        controlPanel.add(statusLabel);
        controlPanel.add(rollButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(Main.thisWindowVisible()));
    }
    
    private static boolean thisWindowVisible() {
        return true;
    }
}

class LudoBoardPanel extends JPanel {
    private static final int CELL = 40;
    private static final int OFFSET = 50;

    private final Color RED = new Color(220, 50, 50);
    private final Color GREEN = new Color(50, 170, 70);
    private final Color YELLOW = new Color(235, 190, 40);
    private final Color BLUE = new Color(60, 100, 220);

    private String[] playerNames = {"Red", "Green", "Yellow", "Blue"};
    private int currentPlayerIndex = 0;
    private Random random = new Random();
    private int lastDiceResult = 1;

    public int rollDice() {
        lastDiceResult = random.nextInt(6) + 1;
        // Turn shift logic (Aap yahan pawno ko chalane ka code add kar sakte hain)
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
        repaint();
        return lastDiceResult;
    }

    public String getCurrentPlayerName() {
        return playerNames[currentPlayerIndex];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background / Outer Board Box
        g2.setColor(Color.WHITE);
        g2.fillRect(OFFSET, OFFSET, 15 * CELL, 15 * CELL);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(OFFSET, OFFSET, 15 * CELL, 15 * CELL);

        // 1. Draw 4 Home Bases (Corners)
        drawHomeBase(g2, 0, 0, RED);     
        drawHomeBase(g2, 9, 0, GREEN);   
        drawHomeBase(g2, 9, 9, YELLOW);  
        drawHomeBase(g2, 0, 9, BLUE);    

        // 2. Draw Center Home / Winning Area
        int cx = OFFSET + 6 * CELL;
        int cy = OFFSET + 6 * CELL;
        g2.setColor(Color.WHITE);
        g2.fillRect(cx, cy, 3 * CELL, 3 * CELL);
        g2.setColor(Color.BLACK);
        g2.drawRect(cx, cy, 3 * CELL, 3 * CELL);

        // Center Triangles
        Polygon redTri = new Polygon();
        redTri.addPoint(cx, cy); redTri.addPoint(cx + (int)(CELL*1.5), cy + (int)(CELL*1.5)); redTri.addPoint(cx, cy + 3*CELL);
        g2.setColor(RED); g2.fillPolygon(redTri);

        Polygon greenTri = new Polygon();
        greenTri.addPoint(cx, cy); greenTri.addPoint(cx + 3*CELL, cy); greenTri.addPoint(cx + (int)(CELL*1.5), cy + (int)(CELL*1.5));
        g2.setColor(GREEN); g2.fillPolygon(greenTri);

        Polygon yellowTri = new Polygon();
        yellowTri.addPoint(cx + 3*CELL, cy); yellowTri.addPoint(cx + 3*CELL, cy + 3*CELL); yellowTri.addPoint(cx + (int)(CELL*1.5), cy + (int)(CELL*1.5));
        g2.setColor(YELLOW); g2.fillPolygon(yellowTri);

        Polygon blueTri = new Polygon();
        blueTri.addPoint(cx, cy + 3*CELL); blueTri.addPoint(cx + 3*CELL, cy + 3*CELL); blueTri.addPoint(cx + (int)(CELL*1.5), cy + (int)(CELL*1.5));
        g2.setColor(BLUE); g2.fillPolygon(blueTri);

        g2.setColor(Color.BLACK);
        g2.drawRect(cx, cy, 3 * CELL, 3 * CELL);
    }

    private void drawHomeBase(Graphics2D g2, int gridX, int gridY, Color color) {
        int x = OFFSET + gridX * CELL;
        int y = OFFSET + gridY * CELL;

        g2.setColor(color);
        g2.fillRect(x, y, 6 * CELL, 6 * CELL);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, 6 * CELL, 6 * CELL);

        g2.setColor(Color.WHITE);
        g2.fillRect(x + CELL, y + CELL, 4 * CELL, 4 * CELL);
        g2.setColor(Color.BLACK);
        g2.drawRect(x + CELL, y + CELL, 4 * CELL, 4 * CELL);
    }
}