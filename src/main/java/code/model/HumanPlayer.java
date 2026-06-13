package code.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a human player in the Risk game.
 */
public class HumanPlayer extends Player {

    private static final int INFANTRY_VALUE = 1;

    private static final int CAVALRY_VALUE = 5;

    private static final int ARTILLERY_VALUE = 10;

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
    public void addArmies(final HashMap<ArmyType, Integer> armiesToAdd) {
        for (Map.Entry<ArmyType, Integer> entry : armiesToAdd.entrySet()) {
            ArmyType armyType = entry.getKey();
            int addedCount = entry.getValue();
            int currentCount = availableArmies.getOrDefault(armyType, 0);

            availableArmies.put(armyType, currentCount + addedCount);
        }
    }

    @Override
    public void removeArmies(final HashMap<ArmyType, Integer> armiesToRemove) {
        if (hasExactArmies(armiesToRemove)) {
            removeExactArmies(armiesToRemove);
            return;
        }

        int remainingValue = calculateArmyValue(availableArmies)
                - calculateArmyValue(armiesToRemove);

        availableArmies = convertValueToArmies(remainingValue);
    }

    private boolean hasExactArmies(final HashMap<ArmyType, Integer> requiredArmies) {
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

    private void removeExactArmies(final HashMap<ArmyType, Integer> armiesToRemove) {
        for (Map.Entry<ArmyType, Integer> entry : armiesToRemove.entrySet()) {
            ArmyType armyType = entry.getKey();
            int removedCount = entry.getValue();
            int currentCount = availableArmies.getOrDefault(armyType, 0);

            availableArmies.put(armyType, currentCount - removedCount);
        }
    }

    @Override
    public String getAvailableArmies() {
        return availableArmies.toString();
    }

    @Override
    public boolean hasAvailableArmies(final HashMap<ArmyType, Integer> requiredArmies) {
        return calculateArmyValue(availableArmies) >= calculateArmyValue(requiredArmies);
    }

    @Override
    public void addArmiesToAvailableBasedOnTerritories() {
        if (getTerritoryCount() == 0) {
            throw new IllegalStateException(
                    "Player cannot own 0 territories and play a turn because they have been eliminated.");
        }
    }

    private int calculateArmyValue(final HashMap<ArmyType, Integer> armies) {
        int infantryCount = armies.getOrDefault(ArmyType.INFANTRY, 0);
        int cavalryCount = armies.getOrDefault(ArmyType.CAVALRY, 0);
        int artilleryCount = armies.getOrDefault(ArmyType.ARTILLERY, 0);

        return infantryCount * INFANTRY_VALUE
                + cavalryCount * CAVALRY_VALUE
                + artilleryCount * ARTILLERY_VALUE;
    }

    private HashMap<ArmyType, Integer> convertValueToArmies(final int armyValue) {
        HashMap<ArmyType, Integer> convertedArmies = new HashMap<>();
        int remainingValue = armyValue;

        convertedArmies.put(ArmyType.ARTILLERY, remainingValue / ARTILLERY_VALUE);
        remainingValue %= ARTILLERY_VALUE;

        convertedArmies.put(ArmyType.CAVALRY, remainingValue / CAVALRY_VALUE);
        remainingValue %= CAVALRY_VALUE;

        convertedArmies.put(ArmyType.INFANTRY, remainingValue);

        return convertedArmies;
    }


}
