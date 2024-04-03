package Model;

import java.awt.Point;

public abstract class HarvestingCenter extends Units {
    public HarvestingCenter(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    public abstract void getCorn(Corn corn);
}
