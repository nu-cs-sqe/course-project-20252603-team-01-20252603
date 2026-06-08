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

    public void initializeContinentsAndTerritories() {
        continents.clear();

        continents.add(new Continent("North America", 5));
        continents.add(new Continent("South America", 2));
        continents.add(new Continent("Europe", 5));
        continents.add(new Continent("Africa", 3));
        continents.add(new Continent("Asia", 7));
        continents.add(new Continent("Australia", 2));
    }

    public List<Continent> getContinents() {
        return new ArrayList<>(continents);
    }
}