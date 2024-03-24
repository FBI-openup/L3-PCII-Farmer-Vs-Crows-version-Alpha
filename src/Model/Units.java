package Model;

import java.awt.Point;

public class Units {
    protected Point position;
    protected GameEngine gameEngine;

    public Units(Point position, GameEngine gameEngine) {
        this.position = position;
        this.gameEngine = gameEngine;
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
}