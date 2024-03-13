package model.units;

import java.awt.Point;

public class Units {
    protected Point position; //units position

    public Units (Point position){
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
