package Model;

import Model.*;

import java.awt.Point;
import javax.swing.Timer;

public class Farmer extends MovingUnits {
    // Farmer attributes
    private boolean isSelected = false; //check if the unit is selected
    private final Timer moveTimer; //used to control the movement of the farmer
    private final int scareRange = 16 * 3 * 3; //the range to scare the crow
    private final int collectingDistance = 16 * 3; //the distance to collect corn

    // CONSTRUCTOR
    public Farmer(GameEngine gameEngine) {
        super(new Point(200, 200),gameEngine);

        //initialize the timer
        moveTimer = new Timer(25, e -> move());
        moveTimer.start();
    }

    // Move the farmer
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
            //System.out.println("Farmer reached destination");
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

    // Get the position of the farmer
    public Point getPosition() {
        return position;
    }

    // Set the position of the farmer
    public void setPosition(Point position) {
        this.position = position;
    }

    // Get the selected state of the farmer
    public boolean isSelected() {
        return isSelected;
    }

    // Set the selected state of the farmer
    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    // Get the destination of the farmer
    public Point getDestination() {
        return destination;
    }

    // Set the destination of the farmer
    public void setDestination(Point destination) {
        this.destination = destination;
    }

    // Check if the click is within the circle
    public boolean isClickWithinCircle(Point clickPoint, int radius) {
        return clickPoint.distance(position) <= radius;
    }

    // Start the movement of the farmer
    public void startMoveTimer() {
        if (moveTimer != null && !moveTimer.isRunning()) {
            moveTimer.start();
        }
    }

    // Stop the movement of the farmer
    public void stopMoveTimer() {
        if (moveTimer != null && moveTimer.isRunning()) {
            moveTimer.stop();
        }
    }

    // Get the scare range of the farmer
    public int getScareRange() {
        return scareRange;
    }

    // Collect corn
    public void collectCorn() {
        for (Corn corn : gameEngine.getCorns()) {
            if (position.distance(corn.getPosition()) <= collectingDistance) {
                System.out.println("Farmer is collecting corn");
                gameEngine.removeUnit(corn);
                System.out.println("Farmer collected corn");
            }
        }
    }
}


