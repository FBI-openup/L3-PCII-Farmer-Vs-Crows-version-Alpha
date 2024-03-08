package model;

import java.util.ArrayList;
import java.util.List;
import model.units.Crow;
import model.units.Farmer;
import model.units.Scarecrow;
import model.units.Corn;

public class Game {
    private Farmer farmers;
    private List<Scarecrow> scarecrows;
    private List<Crow> crows;
    private List<Corn> corns;

    public Game() {
        // Initialize the game objects
        farmers = new Farmer();
        scarecrows = new ArrayList<>();
        crows = new ArrayList<>();
        corns = new ArrayList<>();
    }

    public void start() {
        // Start the game loop
    }

    private void update() {
        // Update the game state
    }

    private void render() {
        // Render the game scene
    }
}