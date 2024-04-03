package Model;

import java.awt.Point;
import javax.swing.Timer;

/*
    * Farmer class
 */
public class Farmer extends MovingUnits {

    // Farmer attributes
    private int health = 100; //the health of the farmer
    private boolean isSelected = false; //check if the unit is selected
    private int numCornForFood = 3; //the number of corn for food in the bag
    private final int scareRange = 16 * 3 * 3; //the range to scare the crow
    private final int collectingDistance = 16 * 3; //the distance to collect corn
    private final Timer moveTimer = new Timer(25, e -> move()); //used to control the movement of the farmer
    private final Timer collectCornCd = new Timer(10000, e -> collectCornInCd = false);
    private boolean collectCornInCd = false;
    private Corn cornTaken = null;
    private final Timer plantSeedCd = new Timer(5000, e -> isPlantSeedInCd = false);
    private boolean isPlantSeedInCd = false;
    private final Timer takeScareCrowCd = new Timer(5000, e -> takeScareCrowInCd = false);
    private boolean takeScareCrowInCd = false;
    private Scarecrow scarecrowTaken = null;
    private final Timer placeScareCrowCd = new Timer(5000, e -> placeScareCrowInCd = false);
    private boolean placeScareCrowInCd = false;

    // Constructor
    public Farmer(GameEngine gameEngine) {
        super(new Point(384, 288), gameEngine);
        //this.setTimers();
    }

    // Set the timers
    public void setTimers() {
        collectCornCd.setRepeats(true);
        plantSeedCd.setRepeats(true);
        takeScareCrowCd.setRepeats(true);
        placeScareCrowCd.setRepeats(true);
        moveTimer.start();
    }

    // Move the farmer
    @Override
    public void move() {
        //set a speed for the farmers movement
        if (health > 30 && health < 150) {
            if (scarecrowTaken != null) {
                speed = 2;
                scarecrowTaken.setPosition(position);
            } else if (cornTaken != null) {
                speed = 3;
                cornTaken.setPosition(position);
            } else {
                speed = 3;
            }
        } else {
            speed = 2;
            if (scarecrowTaken != null) {
                scarecrowTaken.setPosition(position);
            } else if (cornTaken != null) {
                cornTaken.setPosition(position);
            }
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

    public void collectCorn() {
        if (!collectCornInCd && cornTaken == null) {
            for (Corn corn : gameEngine.getCorns()) {
                if (position.distance(corn.getPosition()) <= collectingDistance) {
                    System.out.println("Farmer is taking corn");
                    cornTaken = corn;
                    corn.setTaken(true);
                    System.out.println("Farmer took corn");
                    break;
                }
            }

            // Start the Cd
            collectCornInCd = true;
            collectCornCd.start();
        }
    }

    public void dropCornAtHarvestingCenter() {
        for (HarvestingCenter center : gameEngine.getHarvestingCenters()) {
            if (position.distance(center.getPosition()) <= collectingDistance) {
                if (cornTaken.getLifeCycle() == Corn.LifeCycle.MATURE)
                    center.getCorn(cornTaken);
                gameEngine.removeUnit(cornTaken);
                cornTaken = null;
                break;
            }
        }
    }

    /*
    public synchronized void collectCorn() {
        if (!isCollectCornInCd) {
            for (Corn corn : gameEngine.getCorns()) {
                if (position.distance(corn.getPosition()) <= collectingDistance) {
                    System.out.println("Farmer is collecting corn");
                    gameEngine.removeUnit(corn);
                    gameEngine.setScore(gameEngine.getScore() + 1);
                    System.out.println("Farmer collected corn");
                    break;
                }
            }
            // Start the Cd
            isCollectCornInCd = true;
            collectCornCd.start();
        }
    }*/

    public synchronized void plantSeed() {
        if (gameEngine.getSeedConversionCenter().getNumSeeds() > 0) {
            Point pos = new Point(this.position);
            // Check if the farmer is not in Cd
            if (!isPlantSeedInCd) {
                System.out.println("Farmer is planting a seed");
                Corn corn = new Corn(pos, gameEngine);
                gameEngine.addUnit(corn);
                CornLifeCycleThread cornLifeCycleThread = new CornLifeCycleThread(corn);
                cornLifeCycleThread.start();
                gameEngine.getSeedConversionCenter().consumeSeeds();
                System.out.println("Farmer planted a seed");
                isPlantSeedInCd = true;
                plantSeedCd.start();
            }
        }
    }

    // Place corn in the bag
    public void placeCornInBag() {
        if (cornTaken != null && numCornForFood < 5) {
            gameEngine.removeUnit(cornTaken);
            cornTaken = null;
            numCornForFood++;
        }
    }

    // Eat corn
    public void eatCorn() {
        if (cornTaken != null) {
            switch (cornTaken.getLifeCycle()) {
                case SEED:
                    health -= 15;
                    break;
                case GROWING:
                    health += 15;
                    break;
                case MATURE:
                    health += 30;
                    break;
                case WITHERED:
                    health -= 15;
                    break;
            }
            gameEngine.removeUnit(cornTaken);
            cornTaken = null;
        } else if (numCornForFood > 0) {
            numCornForFood--;
            switch (cornTaken.getLifeCycle()) {
                case SEED:
                    health -= 15;
                    break;
                case MATURE:
                    health += 30;
                    break;
                case GROWING:
                    health += 15;
                    break;
                case WITHERED:
                    health -= 15;
                    break;
            }
        }
    }

    // Take scarecrow
    public void takeScarecrow() {
        if (!takeScareCrowInCd && scarecrowTaken == null) {
            for (Scarecrow scarecrow : gameEngine.getScarecrows()) {
                if (position.distance(scarecrow.getPosition()) <= collectingDistance) {
                    System.out.println("Farmer is taking scarecrow");
                    scarecrowTaken = scarecrow;
                    scarecrow.setTaken(true);
                    System.out.println("Farmer took scarecrow");
                    break;
                }
            }
            // Start the Cd
            takeScareCrowInCd = true;
            takeScareCrowCd.start();
        }
    }

    // Place scarecrow
    public void placeScareCrow() {
        // Check if the farmer is not in Cd
        if (!placeScareCrowInCd && scarecrowTaken != null) {
            Point pos = new Point(this.position);
            scarecrowTaken.setPosition(pos);
            scarecrowTaken.setTaken(false);
            scarecrowTaken = null;
        }
    }

    // Get the health of the farmer
    public int getHealth() {
        return health;
    }

    // Set the health of the farmer
    public void setHealth(int health) {
        this.health = health;
    }

    // Get the corn taken by the farmer
    public Corn getCornTaken() {
        return cornTaken;
    }

    // Get the scarecrow taken by the farmer
    public Scarecrow getScarecrowTaken() {
        return scarecrowTaken;
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


