package Model;

import java.awt.*;

public class Scarecrow extends Units {

    private final int efficiencyTime = 180; // 3 minutes
    private final int efficiencyRange = 16 * 3 * 3; // 16 pixels * 3 tiles * 3 tiles

    public Scarecrow(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    public int getEfficiencyTime() {
        return efficiencyTime;
    }
}
