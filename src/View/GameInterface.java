package View;

import Controller.CrowMovementThread;
import Controller.DisplaysThread;
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
    private JLabel healthLabel = new JLabel("Health: ");
    private JLabel scoreLabel = new JLabel("Score: ");
    private JLabel seedsLabel = new JLabel("Seeds: ");
    private JLabel cornsLabel = new JLabel("Corns: ");

    public GameInterface() {
        setTitle("Game Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 600);

        gameEngine = new GameEngine();

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

        healthLabel.setFont(new Font("Arial", Font.BOLD, 20));

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));

        seedsLabel.setFont(new Font("Arial", Font.BOLD, 20));

        cornsLabel.setFont(new Font("Arial", Font.BOLD, 20));

        setupRepaintTimer(30);

        setResizable(false);
        //startGameThreads();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private JPanel createStartPanel() {
        BackgroundPanel startPanel = new BackgroundPanel("images/background.jpg");
        startPanel.setLayout(new GridBagLayout());
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Game");
            startGameThreads();
        });
        startPanel.add(startButton);
        return startPanel;
    }

    /*
    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setPreferredSize(new Dimension(200, getHeight()));
        String[] buttonLabels = { "Plant Corn", "Harvest Corn","Drop Corn", "Place Corn in the bag", "Eat Corn", "Take Scarecrow", "Place Scarecrow"};
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // for padding


        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(e -> {
                // existing action listeners
            });
            controlPanel.add(button, gbc);
        }

        // Add score display
        JLabel scoreLabel = new JLabel("Score: " + gameEngine.getScore());
        controlPanel.add(scoreLabel, gbc);

        // Add seeds and corns display
        JLabel seedsAndCornsLabel = new JLabel("Seeds: " + gameEngine.getSeedConversionCenter().getNumSeeds() + " Corns: " + gameEngine.getSellingCenter().getCornSold());
        controlPanel.add(seedsAndCornsLabel, gbc);

        // Show health of the farmer
        JLabel healthLabel = new JLabel("Health: " + gameEngine.getFarmer().getHealth());
        controlPanel.add(healthLabel, gbc);

        return controlPanel;
    }*/


    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setPreferredSize(new Dimension(200, getHeight()));
        String[] buttonLabels = { "Plant Corn", "Harvest Corn","Drop Corn", "Place Corn in the bag", "Eat Corn", "Take Scarecrow", "Place Scarecrow"};
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // for padding

        // Show health of the farmer
        controlPanel.add(healthLabel, gbc);

        // Add buttons
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(e -> {
                if (label.equals("Plant Corn")) {
                    gameEngine.getFarmer().plantSeed();
                } else if (label.equals("Harvest Corn")) {
                    gameEngine.getFarmer().collectCorn();
                } else if (label.equals("Drop Corn")) {
                    gameEngine.getFarmer().dropCornAtHarvestingCenter();
                } else if (label.equals("Place Corn in the bag")) {
                    gameEngine.getFarmer().placeCornInBag();
                } else if (label.equals("Eat Corn")) {
                    gameEngine.getFarmer().eatCorn();
                } else if (label.equals("Take Scarecrow")) {
                    gameEngine.getFarmer().takeScarecrow();
                } else if (label.equals("Place Scarecrow")) {
                    gameEngine.getFarmer().placeScareCrow();
                }
            });
            controlPanel.add(button, gbc);
        }

        // Add score display
        controlPanel.add(scoreLabel, gbc);

        // Add seeds and corns display
        JLabel inventoryLabel = new JLabel("Inventory: ");
        inventoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
        controlPanel.add(inventoryLabel, gbc);
        controlPanel.add(seedsLabel, gbc);
        controlPanel.add(cornsLabel, gbc);

        return controlPanel;
    }

    public void updateDisplays() {
        healthLabel.setText("Health: " + gameEngine.getFarmer().getHealth() + "/100");
        System.out.println("Health: " + gameEngine.getFarmer().getHealth() + "/100");
        scoreLabel.setText("Score: " + gameEngine.getScore());
        System.out.println("Score: " + gameEngine.getScore());
        seedsLabel.setText("Seeds: " + gameEngine.getSeedConversionCenter().getNumSeeds());
        System.out.println("Seeds: " + gameEngine.getSeedConversionCenter().getNumSeeds());
        cornsLabel.setText("Corns: " + gameEngine.getSellingCenter().getCornSold());
        System.out.println("Corns: " + gameEngine.getSellingCenter().getCornSold());
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
        DisplaysThread displaysThread = new DisplaysThread(this);
        CrowMovementThread crowMovementThread = new CrowMovementThread(gamePanel, gameEngine);
        CrowGenerationThread crowGenerationThread = new CrowGenerationThread(gameEngine);
        CornGenerationThread cornGenerationThread = new CornGenerationThread(gameEngine);
        FarmerHealthThread farmerHealthThread = new FarmerHealthThread(gameEngine);
        displaysThread.start();
        crowMovementThread.start();
        crowGenerationThread.start();
        cornGenerationThread.start();
        farmerHealthThread.start();
        gameEngine.getFarmer().setTimers();
    }
}