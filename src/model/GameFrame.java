package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import view.GamePanel;
import model.units.Crow;
import model.units.Farmer;
import model.units.Scarecrow;
import model.units.Corn;

import javax.swing.*;

public class GameFrame extends JFrame {
    private Farmer farmer;
    private List<Scarecrow> scarecrows;
    private List<Crow> crows;
    private List<Corn> corns;

    public GameFrame() {
        setTitle("Our Game"); // title
        setSize(1000, 800); // size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // default close operation
        setLocationRelativeTo(null); // center the window
        // Initialize the game objects
        farmer = new Farmer();
        scarecrows = new ArrayList<>();
        crows = new ArrayList<>();
        corns = new ArrayList<>();

        // Create a game panel and add it to the frame
        GamePanel gamePanel = new GamePanel(farmer);
        add(gamePanel); // add the game panel to the frame
        gamePanel.setFocusable(true); // set the game panel to be focusable// Set up a timer to call repaint on gamePanel every second (1000 milliseconds)
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.repaint();
            }
        });
        timer.start();
        setVisible(true); // make the frame visible
    }
}
