

import View.GamePanel;
import Model.*;

import java.awt.Point;
import java.util.List;

public class Crow2 extends MovingUnits {
    // Crow properties
    private final int safetyDistance = 16 * 4;
    private boolean isScared = false;
    private int remainingTime = 1000;
    private int eatingTime;
    private boolean runningForMyLife = false;

    // Constructor
    public Crow2(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    // Method to locate corn
    public synchronized Corn locateCorn() {
        // Find the nearest corn
        List<Corn> corns = gameEngine.getCorns();
        if (corns.isEmpty()) {
            return null;
        }
        Corn nearestCorn = corns.getFirst();
        for (Corn corn : corns) {
            if (position.distance(corn.getPosition()) < position.distance(nearestCorn.getPosition())) {
                nearestCorn = corn;
            }
        }
        System.out.println("NEAREST CORN " + nearestCorn.getPosition().getLocation()); //
        return nearestCorn;
    }

    // Method to locate farmer
    public Farmer locateFarmer() {
        return gameEngine.getFarmer();
    }

    // Method to locate scarecrow
    public Scarecrow locateScarecrow() {
        // Find the nearest scarecrow
        List<Scarecrow> scarecrows = gameEngine.getScarecrows();
        if (scarecrows.isEmpty()) {
            return null;
        }
        Scarecrow nearestScarecrow = scarecrows.getFirst();
        for (Scarecrow scarecrow : scarecrows) {
            if (position.distance(scarecrow.getPosition()) < position.distance(nearestScarecrow.getPosition())) {
                nearestScarecrow = scarecrow;
            }
        }
        return nearestScarecrow;
    }

    public void leave(Point destination) {
        System.out.println("-- LEAVE --"); //
        // Define the corners
        Point[] corners = new Point[] {
                new Point(0, 0),
                new Point(0, 576),
                new Point(768, 0),
                new Point(768, 576)
        };

        // Find the nearest corner
        Point nearestCorner = corners[0];
        double shortestDistance = position.distance(nearestCorner);
        for (Point corner : corners) {
            System.out.println("corner " + corner); // DEBUG
            double distance = position.distance(corner);
            if (distance < shortestDistance) {
                nearestCorner = corner;
                shortestDistance = distance;
            }
        }
        destination = nearestCorner;
        System.out.println("nearestCorner " + nearestCorner); // DEBUG

        // Calculate the direction vector
        double dx = nearestCorner.x - position.x;
        double dy = nearestCorner.y - position.y;
        // Normalize the direction vector
        speed = 1/Math.sqrt(dx * dx + dy * dy);
        System.out.println("speed " + speed); // DEBUG
        dx *= speed;
        dy *= speed;
        System.out.println("dx " + dx + " dy " + dy); // DEBUG
        // Update the crow's position
        if (position.distance(nearestCorner) > 0) {
            position.x += dx;
            position.y += dy;
        }
        if (position.x < 0 || position.x >= 768 || position.y < 0 || position.y >= 576) {
            gameEngine.removeUnit(this);
        }
    }

    public void goLookForCorn(Corn c) {
        System.out.println("-- GO LOOK FOR CORN --"); //
        // Move towards the nearest corn
        this.destination = c.getPosition();
        // Calculate the direction vector
        double dx = destination.x - position.x;
        double dy = destination.y - position.y;
        // Normalize the direction vector
        speed = 1/Math.sqrt(dx * dx + dy * dy);
        dx *= speed * 2;
        dy *= speed * 2;
        // Update the crow's position
        if (position.distance(destination) > 8) {
            position.x += dx;
            position.y += dy;
            System.out.println("positionx " + position.x + " positiony " + position.y); // DEBUG
            remainingTime--;
        }
        if (position.distance(destination) < 16) {
            eatCorn(c);
        }
    }

    public void flee(Units threat) {
        destination = threat.getPosition();
        // Define the corners
        Point[] corners = new Point[] {
                new Point(0, 0),
                new Point(0, 576),
                new Point(768, 0),
                new Point(768, 576)
        };

        // Find the corner that is furthest from the threat but still within a reachable distance from the crow
        Point bestCorner = null;
        double bestDistance = Double.NEGATIVE_INFINITY;
        for (Point corner : corners) {
            double crowToCornerDistance = position.distance(corner);
            double threatToCornerDistance = destination.distance(corner);
            if (threatToCornerDistance > bestDistance && crowToCornerDistance < threatToCornerDistance) {
                bestCorner = corner;
                bestDistance = threatToCornerDistance;
            }
        }

        // If a suitable corner was found, move towards it
        if (bestCorner != null) {
            destination = bestCorner;
            // Calculate the direction vector
            double dx = destination.x - position.x;
            double dy = destination.y - position.y;
            // Normalize the direction vector
            speed = 1/Math.sqrt(dx * dx + dy * dy);
            dx *= speed * 2.5;
            dy *= speed * 2.5;
            // Update the crow's position
            if (position.distance(destination) > speed) {
                position.x += dx;
                position.y += dy;
                remainingTime--;
            } else {
                position.x = destination.x;
                position.y = destination.y;
            }
        }
    }

    // Method to move the crow
    @Override
    public void move(Point destination) {
        // Update the crow's scared state
        updateState();
        Corn nearestCorn = locateCorn();
        // If the crow doesn't find any corn, it will leave or flee
        if (nearestCorn == null) {
            if ((!isScared() || remainingTime <= 0) && !runningForMyLife) {
                leave(destination);
            }
            else {
                // Move away from the nearest threat
                runningForMyLife = true;
                Units threat = locateFarmer();
                Point threatPosition = threat.getPosition();
                for (Units unit : gameEngine.getUnits()) {
                    if (unit instanceof Scarecrow || unit instanceof Farmer) {
                        if (position.distance(unit.getPosition()) < position.distance(threatPosition)) {
                            threat = unit;
                            threatPosition = unit.getPosition();
                        }
                    }
                }
                flee(threat);
            }
        }
        // If the crow finds corn, it will go look for it or flee if it's scared or leave
        else {
            if (!isScared() && remainingTime > 0 && !runningForMyLife) {
                goLookForCorn(nearestCorn);
            }
            else if (isScared()) {
                // Move away from the nearest threat
                runningForMyLife = true;
                Units threat = locateFarmer();
                Point threatPosition = threat.getPosition();
                for (Units unit : gameEngine.getUnits()) {
                    if (unit instanceof Scarecrow || unit instanceof Farmer) {
                        if (position.distance(unit.getPosition()) < position.distance(threatPosition)) {
                            threat = unit;
                            threatPosition = unit.getPosition();
                        }
                    }
                }
                flee(threat);
            }
            else {
                leave(destination);
            }

        }
    }

    // Method to update the crow's scared state
    public void updateState() {
        // If the farmer or the nearest scarecrow are within a certain distance of the crow, the crow is scared
        Farmer farmer = locateFarmer();
        Scarecrow scarecrow = locateScarecrow();
        // If there are no scarecrows, the crow is scared if the farmer is within a certain distance
        if (scarecrow == null) {
            isScared = position.distance(farmer.getPosition()) < safetyDistance;
        }
        else {
            // If the farmer or the nearest scarecrow are not within a certain distance of the crow, the crow is not scared
            isScared = position.distance(farmer.getPosition()) < safetyDistance || position.distance(scarecrow.getPosition()) < safetyDistance && scarecrow.getEfficiencyTime() > 0;
        }
    }
    // Method to eat corn
    public void eatCorn(Corn nearestCorn) {
        // Eat the corn
        if (position.distance(nearestCorn.getPosition()) < 16) {
            eatingTime++;
            if (eatingTime >= 100) {
                gameEngine.removeUnit(nearestCorn);
                eatingTime = 0;
            }
        } else {
            eatingTime = 0;
        }
    }

    // Getters
    public int getSafetyDistance() {
        return safetyDistance;
    }
    public boolean isScared() {
        return isScared;
    }
    public int getRemainingTime() {
        return remainingTime;
    }
}