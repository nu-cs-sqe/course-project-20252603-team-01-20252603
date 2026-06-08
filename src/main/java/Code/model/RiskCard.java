package code.model;

/**
 * Represents a Risk card associated with a territory and card type.
 */
public class RiskCard {

    private final Territory territory;
    private final CardType type;
    private final boolean wild;

    public RiskCard(
            final Territory cardTerritory,
            final CardType cardType,
            final boolean isWildCard) {
        territory = cardTerritory;
        type = cardType;
        wild = isWildCard;
    }

    public boolean isWild() {
        return wild;
    }

    public CardType getType() {
        return type;
    }

    public Territory getTerritory() {
        return territory;
    }

    public boolean matchesTerritory(final Territory targetTerritory) {
        return !wild && territory == targetTerritory;
    }
}