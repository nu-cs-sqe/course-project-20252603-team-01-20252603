package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the Continent class.
 */
public final class ContinentTest {

    private static final int MIN_BONUS_ARMIES = 2;
    private static final int MAX_BONUS_ARMIES = 7;

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
}