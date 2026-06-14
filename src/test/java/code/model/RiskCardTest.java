package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the RiskCard class.
 */
public final class RiskCardTest {

    private Territory createRealTerritory(final String name) {
        Continent continent = new Continent("North America", 5);
        return new Territory(name, continent, Collections.emptyList());
    }

    @Test
    public void nonWildInfantryCardStoresTypeAndTerritory() {
        Territory alaska = createRealTerritory("Alaska");

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
        Territory alaska = createRealTerritory("Alaska");

        RiskCard card = new RiskCard(alaska, CardType.CAVALRY, false);

        assertEquals(CardType.CAVALRY, card.getType());
    }

    @Test
    public void nonWildArtilleryCardStoresType() {
        Territory alaska = createRealTerritory("Alaska");

        RiskCard card = new RiskCard(alaska, CardType.ARTILLERY, false);

        assertEquals(CardType.ARTILLERY, card.getType());
    }

    @Test
    public void nonWildCardDoesNotMatchDifferentTerritory() {
        Territory alaska = createRealTerritory("Alaska");
        Territory brazil = createRealTerritory("Brazil");

        RiskCard card = new RiskCard(alaska, CardType.INFANTRY, false);

        assertFalse(card.matchesTerritory(brazil));
    }

    @Test
    public void wildCardDoesNotMatchTerritory() {
        Territory alaska = createRealTerritory("Alaska");

        RiskCard card = new RiskCard(null, CardType.WILD, true);

        assertFalse(card.matchesTerritory(alaska));
    }

    @Test
    public void nonWildCardMatchesItsOwnTerritoryByReference() {
        Territory alaska = createRealTerritory("Alaska");
        Territory alaskaCopy = createRealTerritory("Alaska");

        RiskCard card = new RiskCard(alaska, CardType.INFANTRY, false);

        assertTrue(card.matchesTerritory(alaska));
        assertFalse(card.matchesTerritory(alaskaCopy));
    }
}
