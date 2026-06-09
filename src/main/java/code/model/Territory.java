package code.model;

import java.util.ArrayList;
import java.util.List;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


/**
 * Represents a territory on the Risk game board.
 */
public class Territory {

    private final String name;

    private final Continent continent;

    private final List<Territory> adjacentTerritories;

    private int armyCount;

    private Player owner;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Territory stores a reference to its continent in the board model."
    )
    public Territory(
            final String territoryName,
            final Continent territoryContinent,
            final List<Territory> territoryAdjacentTerritories) {


        name = territoryName;
        continent = territoryContinent;
        adjacentTerritories = new ArrayList<>(territoryAdjacentTerritories);
        armyCount = 0;
        owner = new NullPlayer();
    }

    String getName() {
        return name;
    }

    Continent getContinent() {
        return continent;
    }

    List<Territory> getAdjacentTerritories() {
        return new ArrayList<>(adjacentTerritories);
    }


    public int getArmyCount() {
        return armyCount;
    }

    void addAdjacentTerritory(final Territory adjacentTerritory) {
        adjacentTerritories.add(adjacentTerritory);
    }

    public boolean isUnclaimed() {
        return owner instanceof NullPlayer;
    }

    public void setOwner(final Player player) {
        owner = player;
    }

    public boolean isOwnedBy(final Player player) {
        return owner.equals(player);
    }
}
