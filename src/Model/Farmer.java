package Model;

import java.awt.Point;
import javax.swing.Timer;

/*
    * Farmer class
 */
public class Farmer extends MovingUnits {

    // Farmer attributes
    private boolean isSelected = false; //check if the unit is selected
    private final int scareRange = 16 * 3 * 3; //the range to scare the crow
    private final int collectingDistance = 16 * 3; //the distance to collect corn
    private final Timer moveTimer = new Timer(25, e -> move()); //used to control the movement of the farmer
    private final Timer collectCornCd = new Timer(10000, e -> isCollectCornInCd = false);
    private boolean isCollectCornInCd = false;
    private final Timer plantSeedCd = new Timer(5000, e -> isPlantSeedInCd = false);
    private boolean isPlantSeedInCd = false;
    private final Timer takeScareCrowCdTimer = new Timer(5000, e -> takeScareCrowInCd = false);
    private boolean takeScareCrowInCd = false;
    private Scarecrow scarecrowTaken = null;
    private final Timer placeScareCrowCdTimer = new Timer(5000, e -> placeScareCrowInCd = false);
    private boolean placeScareCrowInCd = false;

    // Constructor
    public Farmer(GameEngine gameEngine) {
        super(new Point(384, 288), gameEngine);
        this.setTimers();
    }

    // Set the timers
    private void setTimers() {
        collectCornCd.setRepeats(true);
        plantSeedCd.setRepeats(true);
        takeScareCrowCdTimer.setRepeats(true);
        placeScareCrowCdTimer.setRepeats(true);
        moveTimer.start();
    }

    // Move the farmer
    @Override
    public void move() {
        //set a speed for the farmers movement
        
        if (scarecrowTaken != null) {
            speed = 2;
            scarecrowTaken.setPosition(position);
        }
        else {
            speed = 3;
        }

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

    // Collect corn
    /*
    public synchronized void collectCorn() {
        for (Corn corn : gameEngine.getCorns()) {
            if (position.distance(corn.getPosition()) <= collectingDistance) {
                System.out.println("Farmer is collecting corn");
                //CornMouseListener cornMouseListener = new CornMouseListener(corn, new CornStateView(new CornStateThread(corn)));
                gameEngine.removeUnit(corn);
                System.out.println("Farmer collected corn");
            }
        }
    }*/

    public synchronized void collectCorn() {
        if (!isCollectCornInCd) {
            for (Corn corn : gameEngine.getCorns()) {
                if (position.distance(corn.getPosition()) <= collectingDistance) {
                    System.out.println("Farmer is collecting corn");
                    gameEngine.removeUnit(corn);
                    System.out.println("Farmer collected corn");
                }
            }

            // Start the Cd
            isCollectCornInCd = true;
            collectCornCd.start();
        }
    }

    public synchronized void plantSeed() {
        Point pos = new Point(this.position);
        // Check if the farmer is not in Cd
        if (!isPlantSeedInCd) {
            System.out.println("Farmer is planting a seed");
            Corn corn = new Corn(pos, gameEngine);
            gameEngine.addUnit(corn);
            System.out.println("Farmer planted a seed");
            isPlantSeedInCd = true;
            plantSeedCd.start();
        }
    }

    public void takeScarecrow() {
        if (!takeScareCrowInCd && scarecrowTaken == null) {
            for (Scarecrow scarecrow : gameEngine.getScarecrows()) {
                if (position.distance(scarecrow.getPosition()) <= collectingDistance) {
                    System.out.println("Farmer is taking scarecrow");
                    //scarecrow.setX(position.x + 16);
                    //scarecrow.setY(position.y + 16);
                    scarecrowTaken = scarecrow;
                    scarecrow.setTaken(true);
                    System.out.println("Farmer took scarecrow");
                }
            }

            // Start the Cd
            takeScareCrowInCd = true;
            takeScareCrowCdTimer.start();
        }
    }

    public void placeScareCrow() {
        // Check if the farmer is not in Cd
        if (!placeScareCrowInCd && scarecrowTaken != null) {
            Point pos = new Point(this.position);
            scarecrowTaken.setPosition(pos);
            scarecrowTaken.setTaken(false);
            scarecrowTaken = null;
            /*
            Point pos = new Point(this.position);
            for (Scarecrow scarecrow : gameEngine.getScarecrows()) {
                if (scarecrow.isTaken()) {
                    scarecrow.setPosition(pos);
                    System.out.println("Farmer placed a scarecrow");
                    placeScareCrowInCd = true;
                    placeScareCrowCdTimer.start();
                }
            }*/
        }
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
}


