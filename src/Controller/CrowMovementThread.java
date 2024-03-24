package Controller;

import Model.*;
import View.GamePanel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrowMovementThread extends Thread {
    private final GamePanel gamePanel;
    private final GameEngine gameEngine;

    private volatile boolean running = true;

    public CrowMovementThread(GamePanel gamePanel, GameEngine gameEngine) {
        this.gamePanel = gamePanel;
        this.gameEngine = gameEngine;
    }

    @Override
    public void run() {
        while(running) {
            // UPDATE : update the crow positions
            updateCrow();
            try {
                Thread.sleep(25);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void stopThread() {
        running = false;
        executorService.shutdown();
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // 10 threads in the pool

    private void updateCrow() {
        List<Crow> crows = gameEngine.getCrows();
        for (Crow crow : crows) {
            executorService.submit(() -> {
                try {
                    crow.move();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            System.out.println(crow.getPosition());
        }
    }
}
