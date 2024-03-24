package Model;

import Model.*;

import java.awt.Point;
import javax.swing.Timer;

public class Farmer extends MovingUnits {
    private boolean isSelected = false; //check if the unit is selected
    private final Timer moveTimer; //used to control the movement of the farmer

    private final int scareRange = 16 * 3 * 3; //the range to scare the crow


    public Farmer(GameEngine gameEngine) {
        super(new Point(200, 200),gameEngine);

        //initialize the timer
        moveTimer = new Timer(10, e -> move());
        moveTimer.start();
    }

    //the method to move the unit to the destination
    @Override
    public void move() {
        //set a speed for the farmers movement
        speed = 2;

        //calculate the direction to the destination
        int dx = destination.x - position.x;
        int dy = destination.y - position.y;

        // calculate the distance to the destination
        double distance = Math.sqrt(dx * dx + dy * dy);

        // if the distance is less than the speed, the farmer has reached the destination
        if (distance <= speed) {
            System.out.println("Farmer reached destination");
            position.setLocation(destination);
            return;
        }

        // calculate the unit vector of the direction
        double unitX = dx / distance;
        double unitY = dy / distance;

        // calculate the movement for this frame
        int moveX = (int) (unitX * speed);
        int moveY = (int) (unitY * speed);

        // update the position each time
        position.translate(moveX, moveY);
        System.out.println("Farmer moving to " + position);
    }


    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    //check if the click is within the circle
    public boolean isClickWithinCircle(Point clickPoint, int radius) {
        return clickPoint.distance(position) <= radius;
    }

    public void startMoveTimer() {
        if (moveTimer != null && !moveTimer.isRunning()) {
            moveTimer.start();
        }
    }

    public int getScareRange() {
        return scareRange;
    }

    public void collectCorn() {

    }

    public void stopMoveTimer() {
        if (moveTimer != null && moveTimer.isRunning()) {
            moveTimer.stop();
        }
    }
}


