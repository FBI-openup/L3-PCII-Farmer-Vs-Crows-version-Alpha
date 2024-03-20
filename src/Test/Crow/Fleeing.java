package Test.Crow;

import Controller.*;
import Model.*;
import View.GamePanel;

import javax.swing.*;
import java.awt.*;

public class Fleeing {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Crow and Corn");

        GameEngine gameEngine = new GameEngine();

        // Create units and add them to the game engine
        Farmer farmer = new Farmer(new Point(300, 350), gameEngine);
        gameEngine.addUnit(farmer);

        Crow crow = new Crow(new Point(300, 300), gameEngine);
        gameEngine.addUnit(crow);
        Crow crow2 = new Crow(new Point(500, 500), gameEngine);
        gameEngine.addUnit(crow2);

        GamePanel gamePanel = new GamePanel(gameEngine);
        window.add(gamePanel);
        CrowMovement gameThread = new CrowMovement(gamePanel, gameEngine);
        gameThread.start();

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
