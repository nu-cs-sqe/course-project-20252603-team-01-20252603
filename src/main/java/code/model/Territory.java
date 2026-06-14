package code.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final HashMap<ArmyType, Integer> pieces;

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
        pieces = new HashMap<>();
    }

    String getName() {
        return name;
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

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Territory stores a reference to player."
    )
    public void setOwner(final Player player) {
        if (player instanceof NullPlayer) {
            throw new IllegalArgumentException("Owner cannot be NullPlayer.");
        }
        owner = player;
    }

    public boolean isOwnedBy(final Player player) {
        return owner.equals(player);
    }

    public boolean placeArmies(final HashMap<ArmyType, Integer> newPieces) {
        for (Map.Entry<ArmyType, Integer> entry : newPieces.entrySet()) {
            ArmyType armyType = entry.getKey();
            int currentCount = pieces.getOrDefault(armyType, 0);
            int addedCount = entry.getValue();

            pieces.put(armyType, currentCount + addedCount);
        }

        armyCount += newPieces.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        return true;
    }

    public Object getContinentName() {
        return continent.getName();
    }

    public int getArmiesOfType(final ArmyType armyType) {
        return pieces.getOrDefault(armyType, 0);
    }
}
