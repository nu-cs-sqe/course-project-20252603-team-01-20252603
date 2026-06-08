package code.model;

import java.util.List;

/**
 * Represents a territory on the Risk game board.
 */
public class Territory {

    private final String name;
    private final Continent continent;
    private Player owner;

    public Territory(
            final String territoryName,
            final Continent territoryContinent,
            final List<Territory> territoryAdjacentTerritories) {
        name = territoryName;
        continent = territoryContinent;
        owner = null;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }

    public boolean isUnclaimed() {
        return owner == null;
    }
}