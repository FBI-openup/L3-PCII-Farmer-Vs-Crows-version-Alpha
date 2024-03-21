package Controller;

import Model.*;
import View.GamePanel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrowMovementThread extends Thread {
    GamePanel gamePanel;
    GameEngine gameEngine;

    private volatile boolean running = true;

    public CrowMovementThread(GamePanel gamePanel, GameEngine gameEngine) {
        this.gamePanel = gamePanel;
        this.gameEngine = gameEngine;
    }

    @Override
    public void run() {
        int FPS = 45;
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while(running) {
            // UPDATE : update the crow positions
            updateCrow();
            // DRAW : draw the game panel
            renderGame();
            // SLEEP : sleep until next draw
            try {
                double sleepTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (sleepTime < 0) {
                    sleepTime = 0;
                }
                Thread.sleep((long) sleepTime);
                nextDrawTime += drawInterval;
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


//    private void updateCrow() {
//        List<Crow> crows = gameEngine.getCrows();
//        int i = 0;
//        for (Crow crow : crows) {
//            new Thread(() -> {
//                try {
//                    crow.move(corn.getPosition());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();
//            i++;
//        }
//    }

    // DRAW : draw the game panel
    private void renderGame() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}
