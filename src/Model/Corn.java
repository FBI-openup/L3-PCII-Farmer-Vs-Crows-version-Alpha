package Model;


import View.CornStateView;

import java.awt.*;

/*
    * Corn class
*/
public class Corn extends Units {

    public static enum LifeCycle {
        SEED,
        GROWING,
        MATURE,
        WITHERED
    }

    private LifeCycle lifeCycle = LifeCycle.SEED;
    private float progress = 0.0f;
    private int revenue = 0;
    private boolean isTaken = false;

    public Corn(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(LifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
    }
    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}