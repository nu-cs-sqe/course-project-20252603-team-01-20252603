package code.model;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the NullPlayer class.
 */
public final class NullPlayerTest {

    private static final int ONE_INFANTRY = 1;

    private static final int ASIA_BONUS_ARMIES = 7;

    private static final int AUSTRALIA_BONUS_ARMIES = 5;

    private static final int THREE = 3;

    @Test
    public void constructorUnassignedOwnershipCreatesPlayerPlaceholder() {
        NullPlayer player = new NullPlayer();

        assertInstanceOf(Player.class, player);
    }

    @Test
    public void getNameUnassignedOwnershipReturnsEmptyName() {
        NullPlayer player = new NullPlayer();

        assertEquals("", player.getName());
    }

    @Test
    public void getAvailableArmiesUnassignedOwnershipRaisesException() {
        NullPlayer player = new NullPlayer();

        assertThrows(
                UnsupportedOperationException.class,
                player::getAvailableArmies);
    }

    @Test
    public void hasAvailableArmiesUnassignedOwnershipReturnsFalse() {
        NullPlayer player = new NullPlayer();
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertFalse(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesUnassignedOwnershipRaisesException() {
        NullPlayer player = new NullPlayer();

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                player::addArmiesToAvailableBasedOnTerritories);

        assertEquals("NullPlayer cannot receive armies.", exception.getMessage());
    }

    @Test
    public void tradeCardsAndAddArmiesUnassignedOwnershipRaisesException() {
        NullPlayer player = new NullPlayer();

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> player.tradeCardsAndAddArmies(List.of(1, 2, THREE), new Deck(), 0));

        assertEquals("NullPlayer cannot trade cards.", exception.getMessage());

    }

    @Test
    public void addCardThrowsUnsupportedOperationException() {
        NullPlayer player = new NullPlayer();
        RiskCard card = new RiskCard(null, CardType.WILD, true);

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> player.addCard(card));

        assertEquals("NullPlayer cannot receive cards.", exception.getMessage());
    }

    public void ownsTerritoryUnassignedOwnerReturnsFalse() {
        NullPlayer player = new NullPlayer();
        Continent continent = new Continent("Asia", ASIA_BONUS_ARMIES);
        Territory territory = new Territory("Japan", continent, Collections.emptyList());

        assertFalse(player.ownsTerritory(territory));
    }

    @Test
    public void addTerritoryThrowsUnsupportedOperationException() {
        NullPlayer nullPlayer = new NullPlayer();
        Continent continent = new Continent("North America", AUSTRALIA_BONUS_ARMIES);
        Territory territory = new Territory("Alaska", continent, List.of());

        assertThrows(
                UnsupportedOperationException.class,
                () -> nullPlayer.addTerritory(territory));
    }

    @Test
    public void removeTerritoryThrowsUnsupportedOperationException() {
        NullPlayer nullPlayer = new NullPlayer();
        Continent continent = new Continent("North America", AUSTRALIA_BONUS_ARMIES);
        Territory territory = new Territory("Alaska", continent, List.of());

        UnsupportedOperationException exception = assertThrows(
                UnsupportedOperationException.class,
                () -> nullPlayer.removeTerritory(territory));

        assertEquals("NullPlayer cannot remove territories.", exception.getMessage());
    }

    @Test
    public void getTerritoryCountReturnsZero() {
        NullPlayer nullPlayer = new NullPlayer();

        assertEquals(0, nullPlayer.getTerritoryCount());
    }

    @Test
    public void addArmiesThrowsUnsupportedOperationException() {
        NullPlayer nullPlayer = new NullPlayer();
        HashMap<ArmyType, Integer> armies = new HashMap<>();
        armies.put(ArmyType.INFANTRY, 1);

        assertThrows(
                UnsupportedOperationException.class,
                () -> nullPlayer.addArmies(armies));
    }

    @Test
    public void removeArmiesThrowsUnsupportedOperationException() {
        NullPlayer nullPlayer = new NullPlayer();
        HashMap<ArmyType, Integer> armies = new HashMap<>();
        armies.put(ArmyType.INFANTRY, 1);

        assertThrows(
                UnsupportedOperationException.class,
                () -> nullPlayer.removeArmies(armies));

    }

}
