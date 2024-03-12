package model.units;

import java.awt.Point;

public abstract class MovingUnits extends Units {
    protected Point destination; //the place where the unit is moving to ,given by right click

    public MovingUnits(int x, int y) {
        super(x, y);
        this.destination = new Point(x, y); //default destination is the current position
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    //the method to move the unit to the destination
    public abstract void move();
}
