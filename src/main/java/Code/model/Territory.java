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
    private int armyCount;

    public Territory(
            final String territoryName,
            final Continent territoryContinent,
            final List<Territory> territoryAdjacentTerritories) {
        validateName(territoryName);

        name = territoryName;
        continent = territoryContinent;
        adjacentTerritories = new ArrayList<>(territoryAdjacentTerritories);
        owner = null;
        armyCount = 0;
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

    private void validateName(final String territoryName) {
        if (territoryName == null || territoryName.isEmpty()) {
            throw new IllegalArgumentException("Territory name cannot be empty.");
        }
    }

    public int getArmyCount() {
        return armyCount;
    }

    void addAdjacentTerritory(final Territory adjacentTerritory) {
        adjacentTerritories.add(adjacentTerritory);
    }
}