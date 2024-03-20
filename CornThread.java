package model;

import javax.swing.*;
import view.*;
import java.util.Random;

public class CornThread extends Thread {
    private Corn corn;
    private CornPanel cornPanel;
    private Random random;
    public int GROWING_TIME ; // time to grow the corn
    public int READY_TO_HARVEST_TIME ; // time to harvest the corn


    public CornThread(Corn corn, CornPanel cornPanel) {
        this.corn = corn;
        this.cornPanel = cornPanel;
        this.random = new Random();
    }

    @Override
    public void run() {
        GROWING_TIME = (40 + (random.nextInt(20)))*1000; // time to grow the corn
        READY_TO_HARVEST_TIME = GROWING_TIME + (20 + (random.nextInt(20)))*1000; // time to harvest the corn
        try {
            while (true) {
                // Simulate the growth of the corn over time
                if (corn.getState() == CornState.GROWING) {
                    Thread.sleep(GROWING_TIME);
                    corn.setState(CornState.READY_TO_HARVEST);
                } else if (corn.getState() == CornState.READY_TO_HARVEST) {
                    Thread.sleep(READY_TO_HARVEST_TIME);
                    corn.setState(CornState.WILTED);
                } else {
                    break;
                }

                // Update the progress bar on the UI thread
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        cornPanel.updateProgressBar();
                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}