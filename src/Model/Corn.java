package Model;


import View.CornStateView;

import javax.swing.*;
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
    private CornLifeCycleThread cornLifeCycleThread = new CornLifeCycleThread(this);
    private float progress = 0.0f;
    private int revenue = 0;
    private boolean isTaken = false;

    public Corn(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
        cornLifeCycleThread.start();
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
    public CornLifeCycleThread getCornLifeCycleThread() {
        return cornLifeCycleThread;
    }
    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}