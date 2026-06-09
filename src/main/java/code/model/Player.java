package code.model;

import java.util.HashMap;

/**
 * Represents a player in the Risk game.
 */
public abstract class Player {

    private final String name;

    private final PlayerColor color;

    private final HashMap<ArmyType, Integer> availableArmies;

    protected Player(
            final String playerName,
            final PlayerColor playerColor,
            final int startingInfantry) {
        name = playerName;
        color = playerColor;
        availableArmies = new HashMap<>();
        availableArmies.put(ArmyType.INFANTRY, startingInfantry);
        availableArmies.put(ArmyType.CAVALRY, 0);
        availableArmies.put(ArmyType.ARTILLERY, 0);
    }

    public String getName() {
        return name;
    }

    public PlayerColor getColor() {
        return color;
    }

    public HashMap<ArmyType, Integer> getAvailableArmies() {
        return new HashMap<>(availableArmies);
    }

    public boolean hasAvailableArmies() {
        return availableArmies.values().stream().anyMatch(armyCount -> armyCount > 0);
    }
}
