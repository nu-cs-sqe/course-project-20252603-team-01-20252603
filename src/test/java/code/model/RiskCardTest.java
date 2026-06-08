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
}