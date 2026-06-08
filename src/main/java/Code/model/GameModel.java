package code.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main model for the Risk game.
 */
public class GameModel {

    private final List<Continent> continents;

    public GameModel() {
        continents = new ArrayList<>();
    }

    public List<Continent> getContinents() {
        return new ArrayList<>(continents);
    }
}