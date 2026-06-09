package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the HumanPlayer class.
 */
public final class HumanPlayerTest {

    private static final int MIN_SETUP_INFANTRY = 20;

    private static final int MAX_SETUP_INFANTRY = 35;

    @Test
    public void constructor_MinimumSetupInfantry_CreatesPlayer() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);
        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();

        assertEquals("Player 1", player.getName());
        assertEquals(PlayerColor.RED, player.getColor());
        assertEquals(MIN_SETUP_INFANTRY, availableArmies.get(ArmyType.INFANTRY));
    }

    @Test
    public void constructor_MaximumSetupInfantry_CreatesPlayer() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.BLUE,
                MAX_SETUP_INFANTRY);
        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();

        assertEquals("Player 1", player.getName());
        assertEquals(PlayerColor.BLUE, player.getColor());
        assertEquals(MAX_SETUP_INFANTRY, availableArmies.get(ArmyType.INFANTRY));
    }

    @Test
    public void getName_RegisteredPlayer_ReturnsRegisteredName() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);

        assertEquals("Player 1", player.getName());
    }

    @Test
    public void getColor_RegisteredPlayer_ReturnsRegisteredColor() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);

        assertEquals(PlayerColor.RED, player.getColor());
    }

    @Test
    public void getAvailableArmies_MinimumSetupInfantry_ReturnsAvailableArmies() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);
        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();

        assertEquals(MIN_SETUP_INFANTRY, availableArmies.get(ArmyType.INFANTRY));
        assertEquals(0, availableArmies.get(ArmyType.CAVALRY));
        assertEquals(0, availableArmies.get(ArmyType.ARTILLERY));
    }

    @Test
    public void getAvailableArmies_MaximumSetupInfantry_ReturnsAvailableArmies() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.BLUE,
                MAX_SETUP_INFANTRY);
        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();

        assertEquals(MAX_SETUP_INFANTRY, availableArmies.get(ArmyType.INFANTRY));
        assertEquals(0, availableArmies.get(ArmyType.CAVALRY));
        assertEquals(0, availableArmies.get(ArmyType.ARTILLERY));
    }
}
