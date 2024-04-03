package Controller;

import Model.GameEngine;
import View.GameInterface;

public class DisplaysThread extends Thread {
    private final GameInterface gameInterface;
    public DisplaysThread(GameInterface gameInterface) {
        this.gameInterface = gameInterface;
    }
    private volatile boolean running = true;
    @Override
    public void run() {
        while (running) {
            try {
                gameInterface.updateDisplays();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        running = false;
    }
}
