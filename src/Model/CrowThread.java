package Model;

import View.GamePanel;

import java.util.Timer;
import java.util.TimerTask;

public class CrowThread extends Thread {
    private final GameEngine gameEngine;
    private volatile boolean running = true;
    private final Timer timer;

    public CrowThread(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.timer = new Timer();
    }

    @Override
    public void run() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    System.out.println("Generating Crow");
                    gameEngine.generateCrow();
                }
            }
        }, 0, 6000);
    }

    public void stopThread() {
        running = false;
        timer.cancel();
    }
}