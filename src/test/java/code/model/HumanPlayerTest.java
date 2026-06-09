package code.model;
import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the HumanPlayer class.
 */
public final class HumanPlayerTest {

    private static final int MIN_SETUP_INFANTRY = 20;

    private static final int MAX_SETUP_INFANTRY = 35;

    private static final int STARTING_INFANTRY = 20;

    private static final int ZERO_INFANTRY = 0;

    private static final int ONE_INFANTRY = 1;

    private static final int TWENTY_INFANTRY = 20;

//    @Test
//    public void constructorMinimumSetupInfantryCreatesPlayer() {
//        HumanPlayer player = new HumanPlayer(
//                "Player 1",
//                PlayerColor.RED,
//                MIN_SETUP_INFANTRY);
//        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();
//
//        assertEquals("Player 1", player.getName());
//        assertEquals(PlayerColor.RED, player.getColor());
//        assertEquals(MIN_SETUP_INFANTRY, availableArmies.get(ArmyType.INFANTRY));
//    }
//
//    @Test
//    public void constructorMaximumSetupInfantryCreatesPlayer() {
//        HumanPlayer player = new HumanPlayer(
//                "Player 1",
//                PlayerColor.BLUE,
//                MAX_SETUP_INFANTRY);
//        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();
//
//        assertEquals("Player 1", player.getName());
//        assertEquals(PlayerColor.BLUE, player.getColor());
//        assertEquals(MAX_SETUP_INFANTRY, availableArmies.get(ArmyType.INFANTRY));
//    }
//
//    @Test
//    public void getNameRegisteredPlayerReturnsRegisteredName() {
//        HumanPlayer player = new HumanPlayer(
//                "Player 1",
//                PlayerColor.RED,
//                MIN_SETUP_INFANTRY);
//
//        assertEquals("Player 1", player.getName());
//    }
//
//    @Test
//    public void getColorRegisteredPlayerReturnsRegisteredColor() {
//        HumanPlayer player = new HumanPlayer(
//                "Player 1",
//                PlayerColor.RED,
//                MIN_SETUP_INFANTRY);
//
//        assertEquals(PlayerColor.RED, player.getColor());
//    }
//
//    @Test
//    public void getAvailableArmiesMinimumSetupInfantryReturnsAvailableArmies() {
//        HumanPlayer player = new HumanPlayer(
//                "Player 1",
//                PlayerColor.RED,
//                MIN_SETUP_INFANTRY);
//        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();
//
//        assertEquals(MIN_SETUP_INFANTRY, availableArmies.get(ArmyType.INFANTRY));
//        assertEquals(0, availableArmies.get(ArmyType.CAVALRY));
//        assertEquals(0, availableArmies.get(ArmyType.ARTILLERY));
//    }
//
//    @Test
//    public void getAvailableArmiesMaximumSetupInfantryReturnsAvailableArmies() {
//        HumanPlayer player = new HumanPlayer(
//                "Player 1",
//                PlayerColor.BLUE,
//                MAX_SETUP_INFANTRY);
//        HashMap<ArmyType, Integer> availableArmies = player.getAvailableArmies();
//
//        assertEquals(MAX_SETUP_INFANTRY, availableArmies.get(ArmyType.INFANTRY));
//        assertEquals(0, availableArmies.get(ArmyType.CAVALRY));
//        assertEquals(0, availableArmies.get(ArmyType.ARTILLERY));
//    }
//
//    @Test
//    public void hasAvailableArmiesSetupArmyAssignmentReturnsTrue() {
//        HumanPlayer player = new HumanPlayer(
//                "Player 1",
//                PlayerColor.RED,
//                MIN_SETUP_INFANTRY);
//
//        assertTrue(player.hasAvailableArmies());
//    }
    @Test
    public void addTerritoryAddsFirstClaimedTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, MIN_SETUP_INFANTRY);
        Territory territory = createMock(Territory.class);

        player.addTerritory(territory);

        assertEquals(1, player.getTerritoryCount());
        assertTrue(player.ownsTerritory(territory));
    }

    @Test
    public void addTerritoryAddsAnotherClaimedTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory alaska = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);

        player.addTerritory(alaska);
        player.addTerritory(alberta);

        assertEquals(2, player.getTerritoryCount());
        assertTrue(player.ownsTerritory(alaska));
        assertTrue(player.ownsTerritory(alberta));
    }

    @Test
    public void ownsTerritoryReturnsFalseForUnownedTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory alaska = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);

        player.addTerritory(alaska);

        assertTrue(player.ownsTerritory(alaska));
        assertFalse(player.ownsTerritory(alberta));
    }

    @Test
    public void getTerritoryCountReturnsZeroBeforeTerritoryClaimed() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);

        assertEquals(0, player.getTerritoryCount());
    }

    @Test
    public void setAvailableArmiesSetsOneInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        HashMap<ArmyType, Integer> availableArmies = new HashMap<>();
        availableArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        player.setAvailableArmies(availableArmies);

        assertTrue(player.getAvailableArmies().contains("INFANTRY"));
        assertTrue(player.getAvailableArmies().contains("1"));
    }

    @Test
    public void setAvailableArmiesSetsMultipleInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        HashMap<ArmyType, Integer> availableArmies = new HashMap<>();
        availableArmies.put(ArmyType.INFANTRY, TWENTY_INFANTRY);

        player.setAvailableArmies(availableArmies);

        assertTrue(player.getAvailableArmies().contains("INFANTRY"));
        assertTrue(player.getAvailableArmies().contains("20"));
    }

    @Test
    public void setAvailableArmiesSetsZeroInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        HashMap<ArmyType, Integer> availableArmies = new HashMap<>();
        availableArmies.put(ArmyType.INFANTRY, ZERO_INFANTRY);

        player.setAvailableArmies(availableArmies);

        assertTrue(player.getAvailableArmies().contains("INFANTRY"));
        assertTrue(player.getAvailableArmies().contains("0"));
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenRequiredInfantryIsAvailable() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenRequiredInfantryEqualsAvailableInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        HashMap<ArmyType, Integer> availableArmies = new HashMap<>();
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();

        availableArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        player.setAvailableArmies(availableArmies);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

}
