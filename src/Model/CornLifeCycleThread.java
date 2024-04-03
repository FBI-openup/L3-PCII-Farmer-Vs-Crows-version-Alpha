package Model;

public class CornLifeCycleThread extends Thread {
    private final Corn corn;

    public CornLifeCycleThread(Corn corn) {
        this.corn = corn;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Update the progress of the corn's life cycle
                corn.setProgress(corn.getProgress() + 0.01f); // Increase progress by 10% every 10 second
                System.out.println("Progress: " + corn.getProgress());
                // Update the life cycle state based on the progress
                if (corn.getProgress() > 0.33f && corn.getProgress() <= 0.66f) {
                    corn.setLifeCycle(Corn.LifeCycle.GROWING);
                } else if (corn.getProgress() > 0.66f && corn.getProgress() <= 1.0f) {
                    corn.setLifeCycle(Corn.LifeCycle.MATURE);
                } else if (corn.getProgress() > 1.0f) {
                    corn.setLifeCycle(Corn.LifeCycle.WITHERED);
                }

                // Sleep for 1 second
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        this.interrupt();
    }
}