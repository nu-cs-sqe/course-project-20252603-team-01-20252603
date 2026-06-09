package code.model;

import java.util.HashMap;

/**
 * Represents an unassigned player placeholder.
 */
public class NullPlayer extends Player {

    public NullPlayer() {
        super("", PlayerColor.UNASSIGNED, 0);
    }

    @Override
    public void addTerritory(final Territory territory) {
        throw new UnsupportedOperationException("NullPlayer cannot own territories.");
    }

    @Override
    public boolean ownsTerritory(final Territory territory) {
        return false;
    }

    @Override
    public int getTerritoryCount() {
        return 0;
    }

    @Override
    public void setAvailableArmies(final HashMap<ArmyType, Integer> availableArmies) {
        throw new UnsupportedOperationException("NullPlayer cannot have available armies.");
    }

    @Override
    public boolean hasAvailableArmies(final HashMap<ArmyType, Integer> requiredArmies) {
        return false;
    }

    @Override
    public String getAvailableArmies() {
        throw new UnsupportedOperationException("NullPlayer does not have available armies.");
    }
}
