package code.model;

import java.util.HashMap;
import java.util.List;

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

    public abstract void addTerritory(Territory territory);

    public abstract void removeTerritory(Territory territory);

    public abstract boolean ownsTerritory(Territory territory);

    public abstract int getTerritoryCount();

    public abstract void addArmies(HashMap<ArmyType, Integer> armiesToAdd);

    public abstract void removeArmies(HashMap<ArmyType, Integer> armiesToRemove);

    public abstract boolean hasAvailableArmies(HashMap<ArmyType, Integer> requiredArmies);

    public abstract String getAvailableArmies();

    public abstract void addArmiesToAvailableBasedOnTerritories();

    public abstract boolean tradeCardsAndAddArmies(
            List<Integer> cardIndices,
            Deck deck,
            int numSetsTradedIn);

    public abstract void addCard(RiskCard card);

    public abstract void addCards(List<RiskCard> cardsToAdd);

    public abstract List<RiskCard> removeAllCards();

    public abstract void markEliminated();

    @Override
    public String toString() {
        return name + " - " + color;
    }
}
