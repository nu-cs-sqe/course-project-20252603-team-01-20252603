package code.model;

import java.util.List;

/**
 * Represents a territory on the Risk game board.
 */
public class Territory {

    private final String name;
    private final Continent continent;

    public Territory(
            final String territoryName,
            final Continent territoryContinent,
            final List<Territory> territoryAdjacentTerritories) {
        name = territoryName;
        continent = territoryContinent;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }
}