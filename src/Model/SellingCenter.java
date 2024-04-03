package Model;

import java.awt.*;

public class SellingCenter extends HarvestingCenter {
    private int cornSold = 0;
    public SellingCenter(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    public int getCornSold() {
        return cornSold;
    }

    public void setCornSold(int cornSold) {
        this.cornSold = cornSold;
    }

    @Override
    public void getCorn(Corn corn) {
        // Implement the logic for selling corn
        // For example, increase the score by 1
        Farmer farmer = gameEngine.getFarmer();
        if (farmer.getCornTaken() != null) {
            cornSold++;
        }
    }
}
