package model.units;

import java.awt.Point;

public abstract class MovingUnits extends Units {
    protected Point destination; //the place where the unit is moving to ,given by right click
    protected double speed ;
    public MovingUnits(Point position) {
        super(position);
        this.destination = position; //default destination is the current position
        this.speed =0;
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
