package Test.Crow;

import Controller.*;
import Model.*;
import View.GamePanel;

import javax.swing.*;
import java.awt.*;

public class General {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Crow and Corn");

        GameEngine gameEngine = new GameEngine();

        // Create units and add them to the game engine
        Farmer farmer = new Farmer(new Point(300, 400), gameEngine);
        gameEngine.addUnit(farmer);

        Crow crow = new Crow(new Point(300, 300), gameEngine);
        gameEngine.addUnit(crow);
        Crow crow2 = new Crow(new Point(400, 100), gameEngine);
        gameEngine.addUnit(crow2);

        Corn corn = new Corn(new Point(150, 200), gameEngine);
        gameEngine.addUnit(corn);
        Corn corn2 = new Corn(new Point(300, 400), gameEngine);
        gameEngine.addUnit(corn2);
        Corn corn3 = new Corn(new Point(500, 200), gameEngine);
        gameEngine.addUnit(corn3);
        Corn corn4 = new Corn(new Point(600, 400), gameEngine);
        gameEngine.addUnit(corn4);
        Corn corn5 = new Corn(new Point(700, 200), gameEngine);
        gameEngine.addUnit(corn5);
        Corn corn6 = new Corn(new Point(800, 400), gameEngine);
        gameEngine.addUnit(corn6);


        Scarecrow scarecrow = new Scarecrow(new Point(150, 500), gameEngine);
        gameEngine.addUnit(scarecrow);

        GamePanel gamePanel = new GamePanel(gameEngine);
        window.add(gamePanel);
        CrowMovement gameThread = new CrowMovement(gamePanel, gameEngine);
        gameThread.start();

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}