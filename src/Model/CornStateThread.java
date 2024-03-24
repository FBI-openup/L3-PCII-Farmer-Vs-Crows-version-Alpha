package Model;

import Model.Corn;
import Model.CornState;
import View.CornStateView;

import java.util.Random;

/*
    * CornStateThread class
    * It simulates the growth of the corn over time
*/
public class CornStateThread extends Thread {
    private final Corn corn;
    private final Random random = new Random();
    public int GROWING_TIME ; // time to grow the corn
    public int READY_TO_HARVEST_TIME ; // time to harvest the corn


    public CornStateThread(Corn corn) {
        this.corn = corn;
    }

    @Override
    public void run() {
        GROWING_TIME = (40 + (random.nextInt(20)))*50; // time to grow the corn
        READY_TO_HARVEST_TIME = GROWING_TIME + (20 + (random.nextInt(20)))*50; // time to harvest the corn
        try {
            while (true) {
                // Simulate the growth of the corn over time
                if (corn.getState() == CornState.GROWING) {
                    Thread.sleep(GROWING_TIME);
                    corn.setState(CornState.READY_TO_HARVEST);
                    //cornStateView.updateProgressBar();
                } else if (corn.getState() == CornState.READY_TO_HARVEST) {
                    Thread.sleep(READY_TO_HARVEST_TIME);
                    corn.setState(CornState.WILTED);
                    //cornStateView.updateProgressBar();
                } else {
                    break;
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Corn getCorn() {
        return corn;
    }

}