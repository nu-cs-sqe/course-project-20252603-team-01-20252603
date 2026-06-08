package code.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Represents a Risk card associated with a territory and card type.
 */
public class RiskCard {

    private final Territory territory;

    private final CardType type;

    private final boolean wild;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "RiskCard stores a reference to the board territory it represents."
    )
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

    Territory getTerritory() {
        return territory;
    }

    public boolean matchesTerritory(final Territory targetTerritory) {
        return !wild && territory == targetTerritory;
    }
}
