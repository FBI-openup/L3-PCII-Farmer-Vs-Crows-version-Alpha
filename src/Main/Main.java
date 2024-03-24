package Main;

import Controller.*;
import Model.*;
import View.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Crow and Corn");

        GameEngine gameEngine = new GameEngine();

        // Create units and add them to the game engine
        Farmer farmer = new Farmer(gameEngine);
        gameEngine.addUnit(farmer);

        Scarecrow scarecrow = new Scarecrow(new Point(200, 450), gameEngine);
        gameEngine.addUnit(scarecrow);

        GamePanel gamePanel = new GamePanel(gameEngine);
        window.add(gamePanel);
        CrowMovementThread gameThread = new CrowMovementThread(gamePanel, gameEngine);
        GamePanelThread gamePanelThread = new GamePanelThread(gamePanel, gameEngine);
        CrowGenerationThread crowThread = new CrowGenerationThread(gameEngine);
        CornGenerationThread cornThread = new CornGenerationThread(gameEngine);

        gameThread.start();
        gamePanelThread.start();
        crowThread.start();
        cornThread.start();

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}