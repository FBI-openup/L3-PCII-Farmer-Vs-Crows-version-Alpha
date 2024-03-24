package Model;

import java.awt.*;

/*
    * Scarecrow class
*/
public class Scarecrow extends Units {

    // Scarecrow properties
    private final int efficiencyTime = 180; // 3 minutes
    private final int efficiencyRange = 16 * 3 * 3; // 16 pixels * 3 tiles * 3 tiles

    // Constructor
    public Scarecrow(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    // Get the efficiency time of the scarecrow
    public int getEfficiencyTime() {
        return efficiencyTime;
    }

    // Get the efficiency range of the scarecrow
    public int getEfficiencyRange() {
        return efficiencyRange;
    }
}
