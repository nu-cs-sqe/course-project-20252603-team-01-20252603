package code.model;

import java.util.HashMap;
import java.util.List;

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
    public void removeTerritory(final Territory territory) {
        throw new UnsupportedOperationException("NullPlayer cannot remove territories.");
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
    public void addArmies(final HashMap<ArmyType, Integer> armiesToAdd) {
        throw new UnsupportedOperationException("NullPlayer cannot receive armies.");
    }

    @Override
    public void removeArmies(final HashMap<ArmyType, Integer> armiesToRemove) {
        throw new UnsupportedOperationException("NullPlayer cannot remove armies.");
    }

    @Override
    public boolean hasAvailableArmies(final HashMap<ArmyType, Integer> requiredArmies) {
        return false;
    }

    @Override
    public String getAvailableArmies() {
        throw new UnsupportedOperationException("NullPlayer does not have available armies.");
    }

    @Override
    public void addArmiesToAvailableBasedOnTerritories() {
        throw new UnsupportedOperationException("NullPlayer cannot receive armies.");
    }

    @Override
    public boolean tradeCardsAndAddArmies(
            final List<Integer> cardIndices,
            final Deck deck,
            final int numSetsTradedIn) {
        throw new UnsupportedOperationException("NullPlayer cannot trade cards.");
    }

    @Override
    public void addCard(final RiskCard card) {
        throw new UnsupportedOperationException("NullPlayer cannot receive cards.");
    }

    @Override
    public void addCards(final List<RiskCard> cardsToAdd) {
        throw new UnsupportedOperationException("NullPlayer cannot receive cards.");
    }
}
