package model;

public class Corn {
    private CornState state; // this variable will be used to store the state of the corn
    private int revenue; // this variable will be used to store the revenue of the corn


    // constructor for the Corn class
    public Corn(CornState state, int revenue) {
        this.state = state;
        this.revenue = revenue;
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