package main;

import control.CornController;
import model.Corn;
import model.CornState;
import model.CornThread;
import view.CornPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create a new corn object
        Corn corn = new Corn(CornState.GROWING, 5);

        // Create a new corn panel
        CornPanel cornPanel = new CornPanel(corn);

        // Create a new corn thread and start it
        CornThread cornThread = new CornThread(corn, cornPanel);
        cornThread.start();

        // Create a new corn controller and add it to the corn panel
        CornController cornController = new CornController(corn, cornPanel);

        // Create a new JFrame and add the corn panel to it
        JFrame frame = new JFrame("Corn Farm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(cornPanel);
        frame.pack();
        frame.setVisible(true);
    }
}