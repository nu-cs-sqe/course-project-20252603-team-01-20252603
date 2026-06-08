package code.model;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.junit.jupiter.api.Test;


/**
 * Tests boundary values and core behavior for the Continent class.
 */
public final class ContinentTest {

    private static final int MIN_BONUS_ARMIES = 2;

    private static final int MAX_BONUS_ARMIES = 7;

    private static final int NEGATIVE_BONUS_ARMIES = -1;

    private static final int ZERO_BONUS_ARMIES = 0;

    private static final int BELOW_MIN_BONUS_ARMIES = 1;

    private static final int ABOVE_MAX_BONUS_ARMIES = 8;

    @Test
    public void constructorStoresValidName() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);

        assertEquals("Asia", continent.getName());
    }

    @Test
    public void constructorAcceptsMinimumBonusArmies() {
        Continent continent = new Continent("Australia", MIN_BONUS_ARMIES);

        assertEquals(MIN_BONUS_ARMIES, continent.getBonusArmies());
    }

    @Test
    public void constructorAcceptsMaximumBonusArmies() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);

        assertEquals(MAX_BONUS_ARMIES, continent.getBonusArmies());
    }

    @Test
    public void constructorRejectsBonusArmiesBelowMinimum() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Continent("Invalid", BELOW_MIN_BONUS_ARMIES));
    }

    @Test
    public void constructorRejectsZeroBonusArmies() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Continent("Invalid", ZERO_BONUS_ARMIES));
    }

    @Test
    public void constructorRejectsNegativeBonusArmies() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Continent("Invalid", NEGATIVE_BONUS_ARMIES));
    }

    @Test
    public void constructorRejectsBonusArmiesAboveMaximum() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Continent("Invalid", ABOVE_MAX_BONUS_ARMIES));
    }

    @Test
    public void constructorRejectsEmptyName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Continent("", MIN_BONUS_ARMIES));
    }

    @Test
    public void constructorRejectsNullName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Continent(null, MIN_BONUS_ARMIES));
    }

    @Test
    public void containsTerritoryFindsFirstTerritory() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Territory firstTerritory = createMock(Territory.class);
        Territory secondTerritory = createMock(Territory.class);

        continent.addTerritory(firstTerritory);
        continent.addTerritory(secondTerritory);

        assertTrue(continent.containsTerritory(firstTerritory));
    }

    @Test
    public void containsTerritoryFindsLastTerritory() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Territory firstTerritory = createMock(Territory.class);
        Territory lastTerritory = createMock(Territory.class);

        continent.addTerritory(firstTerritory);
        continent.addTerritory(lastTerritory);

        assertTrue(continent.containsTerritory(lastTerritory));
    }

    @Test
    public void containsTerritoryRejectsMissingTerritory() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Territory includedTerritory = createMock(Territory.class);
        Territory missingTerritory = createMock(Territory.class);

        continent.addTerritory(includedTerritory);

        assertFalse(continent.containsTerritory(missingTerritory));
    }

    @Test
    public void containsTerritoryReturnsFalseForNullTerritory() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);

        assertFalse(continent.containsTerritory(null));
    }

    @Test
    public void containsTerritoryReturnsFalseWhenTerritoryListIsEmpty() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Territory territory = createMock(Territory.class);

        assertFalse(continent.containsTerritory(territory));
    }

    @ParameterizedTest
    @CsvSource({
            "North America, 5",
            "South America, 2",
            "Europe, 5",
            "Africa, 3",
            "Asia, 7",
            "Australia, 2"
    })
    public void constructorStoresClassicRiskBonusValues(
            final String continentName,
            final int expectedBonusArmies) {
        Continent continent = new Continent(continentName, expectedBonusArmies);

        assertEquals(expectedBonusArmies, continent.getBonusArmies());
    }
}
