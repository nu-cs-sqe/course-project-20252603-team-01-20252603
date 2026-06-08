package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}