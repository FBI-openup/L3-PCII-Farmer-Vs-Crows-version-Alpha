package View;

import Controller.CrowMovementThread;
import Model.GameEngine;
import javax.swing.*;
import java.awt.*;
import Model.*;
import java.awt.event.ActionEvent;

public class GameInterface extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private GameEngine gameEngine;
    private GamePanel gamePanel;

    private Timer repaintTimer;

    public GameInterface() {
        setTitle("Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1400, 800);

        gameEngine = new GameEngine();
        initializeGameUnits();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel startPanel = createStartPanel();
        cardPanel.add(startPanel, "Start");

        gamePanel = new GamePanel(gameEngine);
        JPanel gamePanelContainer = new JPanel(new BorderLayout());
        gamePanelContainer.add(gamePanel, BorderLayout.CENTER);
        gamePanelContainer.add(createControlPanel(), BorderLayout.EAST);

        cardPanel.add(gamePanelContainer, "Game");
        add(cardPanel, BorderLayout.CENTER);

        setupRepaintTimer(30);

        startGameThreads();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGameUnits() {

        gameEngine.addUnit(new Farmer(gameEngine));
        Scarecrow scarecrow = new Scarecrow(new Point(150, 500), gameEngine);
        gameEngine.addUnit(scarecrow);



    }

    private JPanel createStartPanel() {
        BackgroundPanel startPanel = new BackgroundPanel("images/background.jpg");
        startPanel.setLayout(new GridBagLayout());
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> cardLayout.show(cardPanel, "Game"));
        startPanel.add(startButton);
        return startPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(2, 2));
        String[] buttonLabels = {"Move", "Plant a seed", "Harvest", "Stay"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(e -> System.out.println(label + " action performed!"));
            controlPanel.add(button);
        }
        return controlPanel;
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = Toolkit.getDefaultToolkit().getImage(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    private void setupRepaintTimer(int delay) {
        repaintTimer = new Timer(delay, e -> gamePanel.repaint());
        repaintTimer.start();
    }
    private void startGameThreads() {

        CrowMovementThread gameThread = new CrowMovementThread(gamePanel, gameEngine);
        CrowGenerationThread crowThread = new CrowGenerationThread(gameEngine);
        CornGenerationThread cornThread = new CornGenerationThread(gameEngine);
        gameThread.start();
        crowThread.start();
        cornThread.start();
    }



}



























