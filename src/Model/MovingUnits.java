package Model;

import java.awt.Point;

public abstract class MovingUnits extends Units {
    protected double speed;
    protected Point destination;

    public MovingUnits(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
        this.speed = 0;
        this.destination = position;
    }

    public Point getDestination() {
        return destination;
    }
    public void setDestination(Point destination) {
        this.destination = destination;
    }
    public abstract void move();

}