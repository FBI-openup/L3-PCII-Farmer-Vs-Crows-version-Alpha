package Model;

import View.GamePanel;

import java.awt.Point;
import java.util.List;

public class Crow extends MovingUnits {
    // Crow properties
    private final int safetyDistance = 16 * 3;
    private boolean isScared = false;
    private int remainingTime = 6000;
    private int eatingTime;

    // Constructor
    public Crow(Point position, GameEngine gameEngine) {
        super(position, gameEngine); speed = 2;
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

    /*
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
        //speed = 1/Math.sqrt(dx * dx + dy * dy);
        //speed = 0.0125;
        dx *= speed;
        dy *= speed;
        // Update the crow's position
        if (position.distance(nearestCorner) > 0) {
            position.x += dx;
            position.y += dy;
        }
        else {
            gameEngine.removeUnit(this);
        }

//        if (position.x < -16*3 || position.x >= 768 || position.y < -16*3 || position.y >= 576) {
//            gameEngine.removeUnit(this);
    }*/

    /*
    public void goLookForCorn(Corn c) {
        System.out.println("-- GO LOOK FOR CORN --"); //
        // Move towards the nearest corn
        this.destination = c.getPosition();
        // Calculate the direction vector
        double dx = destination.x - position.x;
        double dy = destination.y - position.y;
        // Normalize the direction vector
        speed = 0.0125;
        //speed = 1/Math.sqrt(dx * dx + dy * dy);
        // System.out.println("speed " + speed); // DEBUG
        dx *= speed * 2.5;
        dy *= speed * 2.5;
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
    }*/

    public synchronized void goLookForCorn(Corn c) {
        System.out.println("-- GO LOOK FOR CORN --"); //
        // Move towards the nearest corn
        this.destination = c.getPosition();
        // Calculate the direction vector
        double dx = destination.x - position.x;
        double dy = destination.y - position.y;
        // Normalize the direction vector
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= speed) {
            position = destination;
        }
        dx /= distance;
        dy /= distance;
        // Si la valeur absolue de dx * speed est plus petite que 1 alors on multiplie par 2
        while (Math.abs(dx * speed) < 1) {
            dx *= 2;
        }
        while (Math.abs(dy * speed) < 1) {
            dy *= 2;
        }
        System.out.println("dx " + dx + " dy " + dy); // DEBUG

        int moveX = (int) (dx * speed);
        int moveY = (int) (dy * speed);
        System.out.println("moveX " + moveX + " moveY " + moveY); // DEBUG


        // Update the crow's position
        if (position.distance(destination) > 16) {
            position.x += moveX;
            position.y += moveY;
            System.out.println("positionx " + position.x + " positiony " + position.y); // DEBUG
            remainingTime--;
        }
        if (position.distance(destination) <= 8) {
            eatCorn(c);
        }
    }

    /*
    public void flee(Units threat) {
        System.out.println("-- FLEE --"); //
        Point threatPosition = threat.getPosition();
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
            double threatToCornerDistance = threatPosition.distance(corner);
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
            //speed = 1/Math.sqrt(dx * dx + dy * dy);
            speed = 0.025;
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
*/
    public synchronized void flee(Units threat) {
        System.out.println("-- FLEE --"); //
        Point threatPosition = threat.getPosition();
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
            double threatToCornerDistance = threatPosition.distance(corner);
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
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance <= speed) {
                position = destination;
            }
            dx /= distance;
            dy /= distance;
            while (Math.abs(dx * speed) < 1) {
                dx *= 2;
            }
            while (Math.abs(dy * speed) < 1) {
                dy *= 2;
            }
            int moveX = (int) (dx * speed);
            int moveY = (int) (dy * speed);
            System.out.println("moveX " + moveX + " moveY " + moveY); // DEBUG


            // Update the crow's position
            if (position.distance(destination) > speed) {
                position.x += moveX;
                position.y += moveY;
                remainingTime--;
            } else {
                position.x = destination.x;
                position.y = destination.y;
            }
        }
    }

    public synchronized void leave(Point destination) {
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
        System.out.println("nearestCorner " + nearestCorner); // DEBUG

        // Calculate the direction vector
        double dx = nearestCorner.x - position.x;
        double dy = nearestCorner.y - position.y;
        // Normalize the direction vector
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance <= speed) {
            position = nearestCorner;
        }
        dx /= distance;
        dy /= distance;

        // Si la valeur absolue de dx * speed est plus petite que 1 alors on multiplie par 2
        while (Math.abs(dx * speed) < 1) {
            dx *= speed * 4;
        }
        while (Math.abs(dy * speed) < 1) {
            dy *= speed * 4;
        }
        System.out.println("dx " + dx + " dy " + dy); // DEBUG

        int moveX = (int) (dx * speed);
        int moveY = (int) (dy * speed);

        // Update the crow's position
        if (position.distance(nearestCorner) > 0) {
            position.x += moveX;
            position.y += moveY;
        }
        else {
            gameEngine.removeUnit(this);
        }

//        if (position.x < -16*3 || position.x >= 768 || position.y < -16*3 || position.y >= 576) {
//            gameEngine.removeUnit(this);
    }

    // Method to move the crow
    @Override
    public void move(Point destination) {
        // Update the crow's scared state
        updateState();
        Corn nearestCorn = locateCorn();
        // If the crow doesn't find any corn, it will leave or flee
        if (nearestCorn == null) {
            if ((!isScared() || remainingTime <= 0)) {
                System.out.println("LEAVE"); //
                /*System.out.println("isScared" + isScared);
                System.out.println("remainingTime" + remainingTime);
                System.out.println("runningForMyLife" + isScared);*/
                leave(destination);
            }
            else {
                // Move away from the nearest threat
                Units threat = locateThreat();
                flee(threat);
            }
        }
        // If the crow finds corn, it will go look for it or flee if it's scared or leave
        else {
            if (!isScared() && remainingTime > 0) {
                goLookForCorn(nearestCorn);
            }
            else if (isScared()) {
                // Move away from the nearest threat
                Units threat = locateThreat();
                flee(threat);
            }
            else {
                System.out.println("LEAVE 2"); //
                /*System.out.println("isScared" + isScared);
                System.out.println("remainingTime" + remainingTime);
                System.out.println("runningForMyLife" + isScared);*/
                leave(destination);
            }

        }
    }

    // Method to update the crow's scared state
    public void updateState() {
        // If the farmer or the nearest scarecrow are within a certain distance of the crow, the crow is scared
        Farmer farmer = locateFarmer();
        Scarecrow scarecrow = locateScarecrow();
        /**/
        if (!isScared) {
            /**/
            // If there are no scarecrows, the crow is scared if the farmer is within a certain distance
            if (scarecrow == null) {
                isScared = position.distance(farmer.getPosition()) < safetyDistance;
            } else {
                // If the farmer or the nearest scarecrow are not within a certain distance of the crow, the crow is not scared
                isScared = position.distance(farmer.getPosition()) < 2 * safetyDistance || position.distance(scarecrow.getPosition()) < safetyDistance && scarecrow.getEfficiencyTime() > 0;
            }
        }
    }
    // Method to eat corn
    public void eatCorn(Corn nearestCorn) {
        // Eat the corn
        if (position.distance(nearestCorn.getPosition()) < 16) {
            eatingTime++;
            if (eatingTime >= 200) {
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