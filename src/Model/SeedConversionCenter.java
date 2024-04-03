package Model;

import java.awt.*;

public class SeedConversionCenter extends HarvestingCenter {

    private int numSeeds = 3;

    public SeedConversionCenter(Point position, GameEngine gameEngine) {
        super(position, gameEngine);
    }

    public int getNumSeeds() {
        return numSeeds;
    }

    public void setNumSeeds(int numSeeds) {
        this.numSeeds = numSeeds;
    }

    @Override
    public void getCorn(Corn corn) {
        // Implement the logic for converting corn to seeds
        // For example, increase the number of seeds by 2
        numSeeds += 2;
    }

    public void consumeSeeds() {
        // Implement the logic for getting seeds from the storage center
        // For example, decrease the number of seeds
        if (numSeeds > 0)
            numSeeds--;
    }
}
