package Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final List<Units> units;

    // CONSTRUCTOR
    public GameEngine() {
        units = new ArrayList<>();
    }

    // Add a unit to the game
    public void addUnit(Units unit) {
        units.add(unit);
    }

    // Remove a unit from the game
    public void removeUnit(Units unit) {
        units.remove(unit);
    }

    // Get all units in the game
    public List<Units> getUnits() {
        return units;
    }

    // Get the player's farmer
    public Farmer getFarmer() {
        for (Units unit : units) {
            if (unit instanceof Farmer) {
                return (Farmer) unit;
            }
        }
        return null;
    }

    // Get the crows in the game
    public List<Crow> getCrows() {
        List<Crow> crows = new ArrayList<>();
        for (Units unit : units) {
            if (unit instanceof Crow) {
                crows.add((Crow) unit);
            }
        }
        return crows;
    }

    // Get the corns in the game
    public List<Corn> getCorns() {
        List<Corn> corns = new ArrayList<>();
        for (Units unit : units) {
            if (unit instanceof Corn) {
                corns.add((Corn) unit);
            }
        }
        return corns;
    }

    // Get the scarecrows in the game
    public List<Scarecrow> getScarecrows() {
        List<Scarecrow> scarecrows = new ArrayList<>();
        for (Units unit : units) {
            if (unit instanceof Scarecrow) {
                scarecrows.add((Scarecrow) unit);
            }
        }
        return scarecrows;
    }

    public void generateCrow() {
        Point[] corners = new Point[] {
                new Point(0, 0),
                new Point(0, 576),
                new Point(768, 0),
                new Point(768, 576)
        };
        Point position = corners[(int) (Math.random() * 4)];
        Crow crow = new Crow(position, this);
        this.addUnit(crow);
    }

    public void generateCorn() {
        List<Corn> corns = getCorns();
        // Generate a random position for the corn
        //The corns should not be drawn too close to the edge of the game panel
        //The corns should not be drawn too close to each other
        int x = (int) (Math.random() * (768 - 200)) + 100; // x will be between 100 and 668 and 768 is the width of the game panel
        int y = (int) (Math.random() * (576 - 200)) + 100; // y will be between 100 and 476 and 576 is the height of the game panel
        if (corns.size() == 0) {
            Corn corn = new Corn(new Point(x, y), this);
            this.addUnit(corn);
        }
        for (Corn c : corns) {
            Point pos = c.getPosition();
            if (Math.abs(pos.x - x) > 16 * 3 || Math.abs(pos.y - y) > 16 * 3) {
                Corn corn = new Corn(new Point(x, y), this);
                this.addUnit(corn);
            }
        }

    }

    /*
    public void generateCorns(int numberOfCorns) {
        int attempts = 0;
        List<Corn> corns = getCorns();
        while (corns.size() < numberOfCorns && attempts < 10) {
            // Generate a random position for the corn
            //The corns should not be drawn too close to the edge of the game panel
            //The corns should not be drawn too close to each other

            int x = (int) (Math.random() * (768 - 200)) + 100; // x will be between 100 and 668 and 768 is the width of the game panel
            int y = (int) (Math.random() * (576 - 200)) + 100; // y will be between 100 and 476 and 576 is the height of the game panel
            boolean tooClose = false;
            for (Corn corn : corns) {
                Point pos = corn.getPosition();
                if (Math.abs(pos.x - x) < 16 * 3 || Math.abs(pos.y - y) < 16 * 3) {
                    tooClose = true;
                    break;
                }
            }
            if (!tooClose) {
                Corn corn = new Corn(new Point(x, y), this);
                this.addUnit(corn);
            }
            attempts++;
        }
    }
    */
}