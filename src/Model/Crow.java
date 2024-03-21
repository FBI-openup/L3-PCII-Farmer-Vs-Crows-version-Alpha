package Model;

import java.awt.Point;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Crow extends MovingUnits {
    // Crow properties
    private final int safetyDistance = 16 * 3 * 2;
    private boolean isScared = false;
    private int remainingTime = 6000;
    private int eatingTime;
    private Clip chewingSound;

    // Constructor
    public Crow(Point position, GameEngine gameEngine) {
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

    public Units locateThreat() {
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
        return threat;
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
        if (position.distance(destination) > 16) {
            position.x += dx;
            position.y += dy;
            remainingTime--;
        }
        if (position.distance(destination) <= 16) {
            eatCorn(c);
        }
    }

    public void flee() {
        System.out.println("-- FLEE --"); //
        destination = locateThreat().getPosition();
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
            speed *= 2;
            dx *= speed;
            dy *= speed;
            // Update the crow's position
            if (position.distance(destination) > speed) {
                position.x += dx;
                position.y += dy;
                remainingTime--;
            }

            if (Math.abs(position.x - destination.x) < 8 && Math.abs(position.y - destination.y) < 8) {
                gameEngine.removeUnit(this);
            }
        }
    }

    public void leave() {
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
            double distance = position.distance(corner);
            if (distance < shortestDistance) {
                nearestCorner = corner;
                shortestDistance = distance;
            }
        }
        destination = nearestCorner;

        // Calculate the direction vector
        double dx = destination.x - position.x;
        double dy = destination.y - position.y;
        // Normalize the direction vector
        speed = 1/Math.sqrt(dx * dx + dy * dy);
        dx *= speed;
        dy *= speed;
        // Update the crow's position
        if (position.distance(destination) > speed) {
            position.x += dx;
            position.y += dy;
            remainingTime--;
        }
        if (Math.abs(position.x - destination.x) < 8 && Math.abs(position.y - destination.y) < 8) {
            gameEngine.removeUnit(this);
        }
    }

    // Method to move the crow
    @Override
    public void move() {
        // Update the crow's scared state
        updateState();
        Corn nearestCorn = locateCorn();

        // If the crow does not find corn, it will leave, or flee if it is scared
        if (nearestCorn == null) {
            if ((!isScared() || remainingTime <= 0))
                leave();
            else
                flee();
        }
        // If the crow finds corn, it will try to eat it, or flee if it is scared, or leave
        else {
            if (!isScared() && remainingTime > 0)
                goLookForCorn(nearestCorn);
            else if (isScared())
                flee();
            else
                leave();
        }
    }

    // Method to update the crow's scared state
    public void updateState() {
        // If the farmer or the nearest scarecrow are within a certain distance of the crow, the crow is scared
        Farmer farmer = locateFarmer();
        Scarecrow scarecrow = locateScarecrow();
        // If the crow is already scared, it remains scared
        if (!isScared) {
            // If there are no scarecrows, the crow is scared if the farmer is within a certain distance
            if (scarecrow == null) {
                isScared = position.distance(farmer.getPosition()) < safetyDistance;
            } else {
                // If the farmer or the nearest scarecrow are not within a certain distance of the crow, the crow is not scared
                isScared = position.distance(farmer.getPosition()) < safetyDistance || position.distance(scarecrow.getPosition()) < safetyDistance && scarecrow.getEfficiencyTime() > 0;
            }
        }
    }
    // Method to eat corn
    public void eatCorn(Corn nearestCorn) {
        // Eat the corn
        if (position.distance(nearestCorn.getPosition()) <= 16) {
            eatingTime++;
            //playChewingSound();
            if (eatingTime >= 200) {
                gameEngine.removeUnit(nearestCorn);
                eatingTime = 0;
            }
        } else {
            eatingTime = 0;
            //chewingSound.stop();
        }
    }

    public void playChewingSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("sounds/chewing2 (1).wav").getAbsoluteFile());
            chewingSound = AudioSystem.getClip();
            chewingSound.open(audioInputStream);
            chewingSound.start();
        } catch(Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
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