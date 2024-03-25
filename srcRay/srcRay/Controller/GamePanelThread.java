package Controller;

import Model.*;
import View.GamePanel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
    This class is responsible for updating the game panel in the game engine.
*/
public class GamePanelThread extends Thread {
    private final GamePanel gamePanel;
    private final GameEngine gameEngine;
    private volatile boolean running = true;
    private final int DELAY = 40;

    public GamePanelThread(GamePanel gamePanel, GameEngine gameEngine) {
        this.gamePanel = gamePanel;
        this.gameEngine = gameEngine;
    }

    @Override
    public void run() {
        while (running) {
            // DRAW : draw the game panel
            while (running) {
                renderGame();
                try {
                    Thread.sleep(DELAY);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopThread() {
        running = false;
    }

    // DRAW : draw the game panel
    private void renderGame() {
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}
