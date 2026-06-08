package code.model;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the RiskCard class.
 */
public final class RiskCardTest {

    @Test
    public void nonWildInfantryCardStoresTypeAndTerritory() {
        Territory alaska = createMock(Territory.class);

        RiskCard card = new RiskCard(alaska, CardType.INFANTRY, false);

        assertFalse(card.isWild());
        assertEquals(CardType.INFANTRY, card.getType());
        assertTrue(card.matchesTerritory(alaska));
    }

    @Test
    public void wildCardStoresWildTypeAndFlag() {
        RiskCard card = new RiskCard(null, CardType.WILD, true);

        assertTrue(card.isWild());
        assertEquals(CardType.WILD, card.getType());
    }

    @Test
    public void nonWildCavalryCardStoresType() {
        Territory alaska = createMock(Territory.class);

        RiskCard card = new RiskCard(alaska, CardType.CAVALRY, false);

        assertEquals(CardType.CAVALRY, card.getType());
    }

    @Test
    public void nonWildArtilleryCardStoresType() {
        Territory alaska = createMock(Territory.class);

        RiskCard card = new RiskCard(alaska, CardType.ARTILLERY, false);

        assertEquals(CardType.ARTILLERY, card.getType());
    }

    @Test
    public void nonWildCardDoesNotMatchDifferentTerritory() {
        Territory alaska = createMock(Territory.class);
        Territory brazil = createMock(Territory.class);

        RiskCard card = new RiskCard(alaska, CardType.INFANTRY, false);

        assertFalse(card.matchesTerritory(brazil));
    }

    @Test
    public void wildCardDoesNotMatchTerritory() {
        Territory alaska = createMock(Territory.class);

        RiskCard card = new RiskCard(null, CardType.WILD, true);

        assertFalse(card.matchesTerritory(alaska));
    }
}