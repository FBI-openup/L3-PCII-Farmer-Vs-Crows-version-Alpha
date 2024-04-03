package Model;

import java.awt.*;

public class FoodStorageCenter extends HarvestingCenter {
    private int numCornForFood = 3;
    public FoodStorageCenter(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    public int getNumCornForFood() {
        return numCornForFood;
    }

    public void setNumCornForFood(int numCornForFood) {
        this.numCornForFood = numCornForFood;
    }

    @Override
    public void getCorn(Corn corn) {
        // Implement the logic for storing corn as food
        // For example, increase the number of corns for food
        numCornForFood++;
    }

    public void giveFood() {
        // Implement the logic for getting food from the storage center
        // For example, decrease the number of corns for food
        if (numCornForFood > 0)
            numCornForFood--;
    }
}
