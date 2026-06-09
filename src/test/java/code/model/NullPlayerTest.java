package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the NullPlayer class.
 */
public final class NullPlayerTest {

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
    public void getColorUnassignedOwnershipReturnsUnassignedColor() {
        NullPlayer player = new NullPlayer();

        assertEquals(PlayerColor.UNASSIGNED, player.getColor());
    }

    @Test
    public void getAvailableArmiesUnassignedOwnershipReturnsEmptyArmyPool() {
        NullPlayer player = new NullPlayer();
        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();

        assertEquals(0, availableArmies.get(ArmyType.INFANTRY));
        assertEquals(0, availableArmies.get(ArmyType.CAVALRY));
        assertEquals(0, availableArmies.get(ArmyType.ARTILLERY));
    }

    @Test
    public void hasAvailableArmiesUnassignedOwnershipReturnsFalse() {
        NullPlayer player = new NullPlayer();

        assertFalse(player.hasAvailableArmies());
    }
}
