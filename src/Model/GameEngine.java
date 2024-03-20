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

    public Crow generateCrow() {
        if (getCrows().size() >= 3) {
            return null;
        }
        Point[] corners = new Point[] {
                new Point(0, 0),
                new Point(0, 576),
                new Point(768, 0),
                new Point(768, 576)
        };
        Point position = corners[(int) (Math.random() * 4)];
        Crow crow = new Crow(position, this);
        this.addUnit(crow);
        return crow;
    }

    // Update the game state
//    public void update() {
//        for (Units unit : units) {
//            if (unit instanceof MovingUnits) {
//                ((MovingUnits) unit).move();
//            }
//        }
//    }

}