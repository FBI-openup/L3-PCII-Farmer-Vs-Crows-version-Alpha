package Model;


import java.awt.*;

/*
    * Corn class
*/
public class Corn extends Units {

    private CornState state = CornState.valueOf("GROWING");
    private int revenue = 0;

    public Corn(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    // getters and setters for the state and revenue variables
    public CornState getState() {
        return state;
    }

    public void setState(CornState state) {
        this.state = state;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }


    }