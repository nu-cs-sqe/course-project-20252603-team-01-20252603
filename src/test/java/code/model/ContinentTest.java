package code.model;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @Test
    public void isFullyOwnedByReturnsFalseWhenContinentHasNoTerritories() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Player player = createMock(Player.class);

        assertFalse(continent.isFullyOwnedBy(player));
    }

    @Test
    public void isFullyOwnedByReturnsTrueForSingleOwnedTerritory() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Player player = createMock(Player.class);
        Territory territory = createMock(Territory.class);

        expect(territory.isOwnedBy(player)).andReturn(true);
        replay(territory);
        continent.addTerritory(territory);

        assertTrue(continent.isFullyOwnedBy(player));
    }

    @Test
    public void isFullyOwnedByReturnsFalseForSingleTerritoryOwnedByAnotherPlayer() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Player player = createMock(Player.class);
        Territory territory = createMock(Territory.class);

        expect(territory.isOwnedBy(player)).andReturn(false);
        replay(territory);
        continent.addTerritory(territory);

        assertFalse(continent.isFullyOwnedBy(player));
    }

    @Test
    public void isFullyOwnedByReturnsTrueWhenAllTerritoriesAreOwnedByPlayer() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Player player = createMock(Player.class);
        Territory firstTerritory = createMock(Territory.class);
        Territory secondTerritory = createMock(Territory.class);

        expect(firstTerritory.isOwnedBy(player)).andReturn(true);
        expect(secondTerritory.isOwnedBy(player)).andReturn(true);
        replay(firstTerritory, secondTerritory);
        continent.addTerritory(firstTerritory);
        continent.addTerritory(secondTerritory);

        assertTrue(continent.isFullyOwnedBy(player));
    }

    @Test
    public void isFullyOwnedByReturnsFalseWhenFirstTerritoryIsOwnedByAnotherPlayer() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Player player = createMock(Player.class);
        Territory firstTerritory = createMock(Territory.class);
        Territory secondTerritory = createMock(Territory.class);

        expect(firstTerritory.isOwnedBy(player)).andReturn(false);
        replay(firstTerritory, secondTerritory);
        continent.addTerritory(firstTerritory);
        continent.addTerritory(secondTerritory);

        assertFalse(continent.isFullyOwnedBy(player));
    }

    @Test
    public void isFullyOwnedByReturnsFalseWhenLastTerritoryIsOwnedByAnotherPlayer() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Player player = createMock(Player.class);
        Territory firstTerritory = createMock(Territory.class);
        Territory secondTerritory = createMock(Territory.class);

        expect(firstTerritory.isOwnedBy(player)).andReturn(true);
        expect(secondTerritory.isOwnedBy(player)).andReturn(false);
        replay(firstTerritory, secondTerritory);
        continent.addTerritory(firstTerritory);
        continent.addTerritory(secondTerritory);

        assertFalse(continent.isFullyOwnedBy(player));
    }

    @Test
    public void isFullyOwnedByReturnsFalseWhenMiddleTerritoryIsOwnedByAnotherPlayer() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Player player = createMock(Player.class);
        Territory firstTerritory = createMock(Territory.class);
        Territory secondTerritory = createMock(Territory.class);
        Territory thirdTerritory = createMock(Territory.class);

        expect(firstTerritory.isOwnedBy(player)).andReturn(true);
        expect(secondTerritory.isOwnedBy(player)).andReturn(false);
        replay(firstTerritory, secondTerritory, thirdTerritory);
        continent.addTerritory(firstTerritory);
        continent.addTerritory(secondTerritory);
        continent.addTerritory(thirdTerritory);

        assertFalse(continent.isFullyOwnedBy(player));
    }

    @Test
    public void isFullyOwnedByReturnsFalseWhenQueriedWithNullPlayer() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Player nullPlayer = new NullPlayer();
        Territory territory = createMock(Territory.class);

        expect(territory.isOwnedBy(nullPlayer)).andReturn(false);
        replay(territory);
        continent.addTerritory(territory);

        assertFalse(continent.isFullyOwnedBy(nullPlayer));
    }

    @Test
    public void addTerritoryNonNullTerritoryIsFoundByContainsTerritory() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Territory territory = createRealTerritory("Japan", continent);

        continent.addTerritory(territory);

        assertTrue(continent.containsTerritory(territory));
    }

    @Test
    public void addTerritoryFirstOfTwoRealTerritoriesIsFound() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Territory japan = createRealTerritory("Japan", continent);
        Territory china = createRealTerritory("China", continent);

        continent.addTerritory(japan);
        continent.addTerritory(china);

        assertTrue(continent.containsTerritory(japan));
    }

    @Test
    public void containsTerritoryReturnsTrueForRealAddedTerritory() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Territory japan = createRealTerritory("Japan", continent);
        Territory china = createRealTerritory("China", continent);

        continent.addTerritory(japan);
        continent.addTerritory(china);

        assertTrue(continent.containsTerritory(china));
    }

    @Test
    public void containsTerritoryReturnsFalseForRealTerritoryNotAdded() {
        Continent continent = new Continent("Asia", MAX_BONUS_ARMIES);
        Territory japan = createRealTerritory("Japan", continent);
        Territory china = createRealTerritory("China", continent);

        continent.addTerritory(japan);

        assertFalse(continent.containsTerritory(china));
    }

    private Territory createRealTerritory(final String name, final Continent continent) {
        return new Territory(name, continent, Collections.emptyList());
    }
}
