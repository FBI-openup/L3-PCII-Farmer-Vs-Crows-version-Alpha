package Model;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CornGenerationThread extends Thread {
    // Properties
    private final GameEngine gameEngine;
    private volatile boolean running = true;
    private final Timer timer;

    // Constructor
    public CornGenerationThread(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.timer = new Timer();
    }

    // Run the thread
    @Override
    public void run() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Generate a crow every 15-25 seconds if there are less than 3 crows and more than 0 corns
                if (running && gameEngine.getCorns().size() < 10) {
                    System.out.println("Generating Corn");
                    gameEngine.generateCorn();
                }
            }
        }, 10000, new Random().nextInt(5000) + 10000);
    }

    // Stop the thread
    public void stopThread() {
        running = false;
        timer.cancel();
    }
}