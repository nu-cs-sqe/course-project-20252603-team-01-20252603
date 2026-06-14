package code.model;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
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

    private static final int ZERO_ARMIES = 0;

    private static final int ONE_ARMY = 1;

    private static final int THREE_ARMIES = 3;

    private static final int FOUR_ARMIES = 4;

    private static final int FIVE_ARMIES = 5;

    private static final int FIFTEEN_ARMIES = 15;

    private static final int ONE_ARTILLERY = 10;

    private static final Integer ONE_CAVALRY = 5;

    @Test
    public void constructorMinimumSetupInfantryCreatesPlayer() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);
        String availableArmies = player.getAvailableArmies();

        assertEquals("Player 1", player.getName());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(MIN_SETUP_INFANTRY)));
    }

    @Test
    public void constructorMaximumSetupInfantryCreatesPlayer() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.BLUE,
                MAX_SETUP_INFANTRY);
        String availableArmies = player.getAvailableArmies();

        assertEquals("Player 1", player.getName());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(MAX_SETUP_INFANTRY)));
    }

    @Test
    public void getNameRegisteredPlayerReturnsRegisteredName() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);

        assertEquals("Player 1", player.getName());
    }

    @Test
    public void getAvailableArmiesMinimumSetupInfantryReturnsAvailableArmies() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(MIN_SETUP_INFANTRY)));
    }

    @Test
    public void getAvailableArmiesMaximumSetupInfantryReturnsAvailableArmies() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.BLUE,
                MAX_SETUP_INFANTRY);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(MAX_SETUP_INFANTRY)));
    }

    @Test
    public void hasAvailableArmiesSetupArmyAssignmentReturnsTrue() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

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
    public void hasAvailableArmiesReturnsTrueWhenRequiredInfantryIsAvailable() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void addArmiesAddsOneInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        HashMap<ArmyType, Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(ArmyType.INFANTRY, ONE_INFANTRY);

        player.addArmies(armiesToAdd);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("1"));
    }

    @Test
    public void addArmiesAddsMultipleInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        HashMap<ArmyType, Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(ArmyType.INFANTRY, TWENTY_INFANTRY);

        player.addArmies(armiesToAdd);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("20"));
    }

    @Test
    public void removeArmiesRemovesOneInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, TWENTY_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, ONE_INFANTRY);

        player.removeArmies(armiesToRemove);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("19"));
    }

    @Test
    public void removeArmiesRemovesLastInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ONE_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, ONE_INFANTRY);

        player.removeArmies(armiesToRemove);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("0"));
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenRequiredInfantryEqualsAvailableInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ONE_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsFalseWhenRequiredInfantryGreaterThanAvailableInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertFalse(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void getAvailableArmiesReturnsDisplayStringWhenInfantryAvailable() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, TWENTY_INFANTRY);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("20"));
    }

    @Test
    public void getAvailableArmiesReturnsDisplayStringWhenNoInfantryAvailable() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("0"));
    }

    private HashMap<ArmyType, Integer> createArmies(
            final int infantry,
            final int cavalry,
            final int artillery) {
        HashMap<ArmyType, Integer> armies = new HashMap<>();
        armies.put(ArmyType.INFANTRY, infantry);
        armies.put(ArmyType.CAVALRY, cavalry);
        armies.put(ArmyType.ARTILLERY, artillery);
        return armies;
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenExactInfantryCountAvailable() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ONE_ARMY);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenInfantryCanConvertToCavalryAndArtillery() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                FIFTEEN_ARMIES);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ZERO_ARMIES, ONE_ARMY, ONE_ARMY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenArtilleryCanConvertToCavalry() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ZERO_ARMIES);
        HashMap<ArmyType, Integer> availableArmies =
                createArmies(ZERO_ARMIES, ZERO_ARMIES, ONE_ARMY);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);

        player.addArmies(availableArmies);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsFalseWhenRequiredValueGreaterThanAvailableValue() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                FOUR_ARMIES);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);

        assertFalse(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsFalseWhenPlayerHasZeroAvailableArmyValue() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ZERO_ARMIES);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        assertFalse(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void removeArmiesRemovesOneInfantryFromOneAvailableInfantry() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ONE_ARMY);
        HashMap<ArmyType, Integer> armiesToRemove =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        player.removeArmies(armiesToRemove);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=0"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void removeArmiesRemovesCavalryUsingInfantryValue() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                FIVE_ARMIES);
        HashMap<ArmyType, Integer> armiesToRemove =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);

        player.removeArmies(armiesToRemove);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=0"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void removeArmiesRemovesCavalryFromArtilleryAndMakesChange() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ZERO_ARMIES);
        HashMap<ArmyType, Integer> availableArmies =
                createArmies(ZERO_ARMIES, ZERO_ARMIES, ONE_ARMY);
        HashMap<ArmyType, Integer> armiesToRemove =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);

        player.addArmies(availableArmies);

        player.removeArmies(armiesToRemove);

        String remainingArmies = player.getAvailableArmies();

        assertTrue(remainingArmies.contains("INFANTRY=0"));
        assertTrue(remainingArmies.contains("CAVALRY=1"));
        assertTrue(remainingArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void removeArmiesRemovesInfantryFromCavalryAndMakesChange() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ZERO_ARMIES);
        HashMap<ArmyType, Integer> availableArmies =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);
        HashMap<ArmyType, Integer> armiesToRemove =
                createArmies(THREE_ARMIES, ZERO_ARMIES, ZERO_ARMIES);

        player.addArmies(availableArmies);

        player.removeArmies(armiesToRemove);

        String remainingArmies = player.getAvailableArmies();

        assertTrue(remainingArmies.contains("INFANTRY=2"));
        assertTrue(remainingArmies.contains("CAVALRY=0"));
        assertTrue(remainingArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void removeArmiesBreaksArtilleryWhenRemovingCavalry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        HashMap<ArmyType, Integer> availableArmies = new HashMap<>();
        availableArmies.put(ArmyType.ARTILLERY, ONE_ARTILLERY);
        player.addArmies(availableArmies);

        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.CAVALRY, ONE_CAVALRY);

        player.removeArmies(armiesToRemove);

        String remainingArmies = player.getAvailableArmies();

        assertTrue(remainingArmies.contains("CAVALRY"));
        assertTrue(remainingArmies.contains("1"));
        assertTrue(remainingArmies.contains("ARTILLERY"));
        assertTrue(remainingArmies.contains("0"));
    }

    private Territory createRealTerritory() {
        Continent continent = new Continent("Asia", FIVE_ARMIES);
        return new Territory("Japan", continent, Collections.emptyList());
    }

    @Test
    public void ownsTerritoryReturnsTrueForAddedRealTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory territory = createRealTerritory();

        player.addTerritory(territory);

        assertTrue(player.ownsTerritory(territory));
    }

    @Test
    public void ownsTerritoryReturnsFalseForNonAddedRealTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Continent continent = new Continent("Asia", FIVE_ARMIES);
        Territory owned = new Territory("Japan", continent, Collections.emptyList());
        Territory unowned = new Territory("China", continent, Collections.emptyList());

        player.addTerritory(owned);

        assertFalse(player.ownsTerritory(unowned));
    }

    @Test
    public void removeArmiesExactCavalryMatchPreservesRemainingInfantry() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                FIVE_ARMIES);
        HashMap<ArmyType, Integer> cavalryToAdd =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);
        player.addArmies(cavalryToAdd);

        HashMap<ArmyType, Integer> cavalryToRemove =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);
        player.removeArmies(cavalryToRemove);

        String remainingArmies = player.getAvailableArmies();
        assertTrue(remainingArmies.contains("INFANTRY=5"));
        assertTrue(remainingArmies.contains("CAVALRY=0"));
    }

}
