package code.model;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the NullPlayer class.
 */
public final class NullPlayerTest {

    private static final int ONE_INFANTRY = 1;

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
    public void ownsTerritoryUnassignedOwnerReturnsFalse() {
        NullPlayer player = new NullPlayer();
        Territory territory = createMock(Territory.class);

        assertFalse(player.ownsTerritory(territory));
    }

}
