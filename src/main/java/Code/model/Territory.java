package code.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a territory on the Risk game board.
 */
public class Territory {

    private final String name;
    private final Continent continent;
    private final List<Territory> adjacentTerritories;
    private Player owner;

    public Territory(
            final String territoryName,
            final Continent territoryContinent,
            final List<Territory> territoryAdjacentTerritories) {
        name = territoryName;
        continent = territoryContinent;
        adjacentTerritories = new ArrayList<>(territoryAdjacentTerritories);
        owner = null;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }

    public List<Territory> getAdjacentTerritories() {
        return new ArrayList<>(adjacentTerritories);
    }

    public boolean isUnclaimed() {
        return owner == null;
    }
}