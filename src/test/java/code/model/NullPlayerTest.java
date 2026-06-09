package code.model;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the NullPlayer class.
 */
public final class NullPlayerTest {

    @Test
    public void constructor_UnassignedOwnership_CreatesPlayerPlaceholder() {
        NullPlayer player = new NullPlayer();

        assertInstanceOf(Player.class, player);
    }
}
