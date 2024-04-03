package Model;

public class FarmerHealthThread extends Thread {
    // Properties
    private final GameEngine gameEngine;
    private volatile boolean running = true;

    // Constructor
    public FarmerHealthThread(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    // Run the thread
    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(4000);
                gameEngine.decreaseFarmerHealth(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Stop the thread
    public void stopThread() {
        running = false;
    }

}
