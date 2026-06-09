package code.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a human player in the Risk game.
 */
public class HumanPlayer extends Player {

    private final List<Territory> territories;

    private HashMap<ArmyType, Integer> availableArmies;

    public HumanPlayer(
            final String playerName,
            final PlayerColor playerColor,
            final int startingInfantry) {
        super(playerName, playerColor, startingInfantry);
        territories = new ArrayList<>();
        availableArmies = new HashMap<>();
        availableArmies.put(ArmyType.INFANTRY, startingInfantry);
    }

    @Override
    public void addTerritory(final Territory territory) {
        territories.add(territory);
    }

    @Override
    public boolean ownsTerritory(final Territory territory) {
        return territories.contains(territory);
    }

    @Override
    public int getTerritoryCount() {
        return territories.size();
    }

    @Override
    public void setAvailableArmies(final HashMap<ArmyType, Integer> newAvailableArmies) {
        availableArmies = new HashMap<>(newAvailableArmies);
    }

    @Override
    public String getAvailableArmies() {
        return availableArmies.toString();
    }

    @Override
    public boolean hasAvailableArmies(final HashMap<ArmyType, Integer> requiredArmies) {
        for (Map.Entry<ArmyType, Integer> entry : requiredArmies.entrySet()) {
            ArmyType armyType = entry.getKey();
            int requiredCount = entry.getValue();
            int availableCount = availableArmies.getOrDefault(armyType, 0);

            if (availableCount < requiredCount) {
                return false;
            }
        }

        return true;
    }


}
