package model.units;

import java.awt.Point;

public class Units {
    protected Point position; //units position

    public Units(int x, int y) {
        this.position = new Point(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
