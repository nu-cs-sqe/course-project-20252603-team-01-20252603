package code.model;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the Territory class.
 */
public final class TerritoryTest {

    private static final int NORTH_AMERICA_BONUS_ARMIES = 5;

    private static final int CONTINENT_BONUS = 5;

    private static final int ONE_INFANTRY = 1;

    private static final int TWO_INFANTRY = 2;

    @Test
    public void constructorStoresValidName() {
        Continent northAmerica = new Continent(
                "North America",
                NORTH_AMERICA_BONUS_ARMIES);
        Territory northwestTerritory = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);
        Territory kamchatka = createMock(Territory.class);
        List<Territory> neighbours = Arrays.asList(
                northwestTerritory,
                alberta,
                kamchatka);

        Territory alaska = new Territory("Alaska", northAmerica, neighbours);

        assertEquals("Alaska", alaska.getName());
    }


    @Test
    public void constructorStoresAdjacentTerritories() {
        Continent northAmerica = new Continent(
                "North America",
                NORTH_AMERICA_BONUS_ARMIES);
        Territory northwestTerritory = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);
        Territory kamchatka = createMock(Territory.class);
        List<Territory> neighbours = Arrays.asList(
                northwestTerritory,
                alberta,
                kamchatka);

        Territory alaska = new Territory("Alaska", northAmerica, neighbours);

        assertEquals(neighbours.size(), alaska.getAdjacentTerritories().size());
        assertTrue(alaska.getAdjacentTerritories().contains(northwestTerritory));
        assertTrue(alaska.getAdjacentTerritories().contains(alberta));
        assertTrue(alaska.getAdjacentTerritories().contains(kamchatka));
    }

    @Test
    public void constructorAcceptsEmptyAdjacentTerritoryList() {
        Continent northAmerica = new Continent(
                "North America",
                NORTH_AMERICA_BONUS_ARMIES);
        List<Territory> neighbours = Collections.emptyList();

        Territory alaska = new Territory("Alaska", northAmerica, neighbours);

        assertTrue(alaska.getAdjacentTerritories().isEmpty());
    }

    private Territory createTerritoryWithNoAdjacencies() {
        Continent continent = new Continent("North America", CONTINENT_BONUS);

        return new Territory("Alaska", continent, List.of());
    }

    @Test
    public void territoryStartsOwnedByNullPlayer() {
        Territory territory = createTerritoryWithNoAdjacencies();

        assertTrue(territory.isUnclaimed());
    }

    @Test
    public void setOwnerFromNullPlayerToHumanPlayerUpdatesOwner() {
        Territory territory = createTerritoryWithNoAdjacencies();
        Player player = createMock(Player.class);

        territory.setOwner(player);

        assertFalse(territory.isUnclaimed());
        assertTrue(territory.isOwnedBy(player));
    }

    @Test
    public void setOwnerFromHumanPlayerToAnotherHumanPlayerUpdatesOwner() {
        Territory territory = createTerritoryWithNoAdjacencies();
        Player playerOne = createMock(Player.class);
        Player playerTwo = createMock(Player.class);

        territory.setOwner(playerOne);
        territory.setOwner(playerTwo);

        assertFalse(territory.isOwnedBy(playerOne));
        assertTrue(territory.isOwnedBy(playerTwo));
    }

    @Test
    public void setOwnerToNullPlayerRaisesExceptionAndKeepsCurrentOwner() {
        Territory territory = createTerritoryWithNoAdjacencies();
        Player player = createMock(Player.class);
        Player nullPlayer = new NullPlayer();

        territory.setOwner(player);

        assertThrows(
                IllegalArgumentException.class,
                () -> territory.setOwner(nullPlayer));

        assertTrue(territory.isOwnedBy(player));
    }

    @Test
    public void isOwnedByReturnsFalseWhenTerritoryIsUnclaimed() {
        Territory territory = createTerritoryWithNoAdjacencies();
        Player player = createMock(Player.class);

        assertFalse(territory.isOwnedBy(player));
    }

    @Test
    public void placeArmiesAddsOneInfantryToEmptyTerritory() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, ONE_INFANTRY);

        boolean placed = territory.placeArmies(pieces);

        assertTrue(placed);
    }

    @Test
    public void placeArmiesAddsOneInfantryToExistingInfantry() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, ONE_INFANTRY);

        territory.placeArmies(pieces);
        boolean placed = territory.placeArmies(pieces);

        assertTrue(placed);
    }

    @Test
    public void placeArmiesOneInfantryIncreasesArmyCountByOne() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, ONE_INFANTRY);

        territory.placeArmies(pieces);

        assertEquals(ONE_INFANTRY, territory.getArmyCount());
    }

    @Test
    public void addArmiesZeroArmiesReturnsTrue() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(ArmyType.INFANTRY, 0);

        boolean added = territory.addArmies(armiesToAdd);

        assertTrue(added);
        assertEquals(0, territory.getArmyCount());
    }

    @Test
    public void addArmiesOneArmyToEmptyTerritoryReturnsTrue() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(ArmyType.INFANTRY, ONE_INFANTRY);

        boolean added = territory.addArmies(armiesToAdd);

        assertTrue(added);
        assertEquals(ONE_INFANTRY, territory.getArmyCount());
    }

    @Test
    public void addArmiesMultipleArmiesToOccupiedTerritoryReturnsTrue() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> initialArmies = new HashMap<>();
        initialArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);
        HashMap<ArmyType, Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(ArmyType.INFANTRY, TWO_INFANTRY);

        territory.addArmies(initialArmies);
        boolean added = territory.addArmies(armiesToAdd);

        assertTrue(added);
        final int EXPECTED_RESULT = 3; 
        assertEquals(EXPECTED_RESULT, territory.getArmyCount());
    }

    @Test
    public void removeArmiesZeroArmiesReturnsFalse() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> initialArmies = new HashMap<>();
        initialArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, 0);

        territory.addArmies(initialArmies);
        boolean removed = territory.removeArmies(armiesToRemove);

        assertFalse(removed);
        assertEquals(ONE_INFANTRY, territory.getArmyCount());
    }

    @Test
    public void removeArmiesNegativeArmyCountReturnsFalse() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> initialArmies = new HashMap<>();
        initialArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, -ONE_INFANTRY);

        territory.addArmies(initialArmies);
        boolean removed = territory.removeArmies(armiesToRemove);

        assertFalse(removed);
        assertEquals(ONE_INFANTRY, territory.getArmyCount());
    }

    @Test
    public void removeArmiesOneArmyFromOneArmyTerritoryReturnsTrue() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> initialArmies = new HashMap<>();
        initialArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, ONE_INFANTRY);

        territory.addArmies(initialArmies);
        boolean removed = territory.removeArmies(armiesToRemove);

        assertTrue(removed);
        assertEquals(0, territory.getArmyCount());
    }

    @Test
    public void removeArmiesOneArmyWhileArmiesRemainReturnsTrue() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> initialArmies = new HashMap<>();
        initialArmies.put(ArmyType.INFANTRY, TWO_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, ONE_INFANTRY);

        territory.addArmies(initialArmies);
        boolean removed = territory.removeArmies(armiesToRemove);

        assertTrue(removed);
        assertEquals(ONE_INFANTRY, territory.getArmyCount());
    }

    @Test
    public void removeArmiesMultipleArmiesWhileArmiesRemainReturnsTrue() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> initialArmies = new HashMap<>();
        final int INITIAL_ARMIES = 3; 
        initialArmies.put(ArmyType.INFANTRY, INITIAL_ARMIES);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, TWO_INFANTRY);

        territory.addArmies(initialArmies);
        boolean removed = territory.removeArmies(armiesToRemove);

        assertTrue(removed);
        assertEquals(ONE_INFANTRY, territory.getArmyCount());
    }

    @Test
    public void removeArmiesExactlyAllArmiesReturnsTrue() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> initialArmies = new HashMap<>();
        initialArmies.put(ArmyType.INFANTRY, TWO_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, TWO_INFANTRY);

        territory.addArmies(initialArmies);
        boolean removed = territory.removeArmies(armiesToRemove);

        assertTrue(removed);
        assertEquals(0, territory.getArmyCount());
    }

    @Test
    public void removeArmiesMoreArmiesThanPresentReturnsFalse() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> initialArmies = new HashMap<>();
        initialArmies.put(ArmyType.INFANTRY, TWO_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        final int ARMIES_TO_REMOVE = 3; 
        armiesToRemove.put(ArmyType.INFANTRY, ARMIES_TO_REMOVE);

        territory.addArmies(initialArmies);
        boolean removed = territory.removeArmies(armiesToRemove);

        assertFalse(removed);
        assertEquals(TWO_INFANTRY, territory.getArmyCount());
    }

    @Test
    public void placeArmiesTwoSequentialPlacementsAccumulatesArmyCount() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, ONE_INFANTRY);

        territory.placeArmies(pieces);
        territory.placeArmies(pieces);

        assertEquals(TWO_INFANTRY, territory.getArmyCount());
    }

    @Test
    public void getAdjacentTerritoriesReturnsAllConstructorAdjacentTerritories() {
        Continent northAmerica = new Continent(
                "North America",
                NORTH_AMERICA_BONUS_ARMIES);
        Continent asia = new Continent(
                "Asia",
                NORTH_AMERICA_BONUS_ARMIES);
        Territory alberta = new Territory(
                "Alberta",
                northAmerica,
                Collections.emptyList());
        Territory kamchatka = new Territory(
                "Kamchatka",
                asia,
                Collections.emptyList());
        List<Territory> neighbours = Arrays.asList(alberta, kamchatka);

        Territory alaska = new Territory("Alaska", northAmerica, neighbours);
        List<Territory> adjacent = alaska.getAdjacentTerritories();

        assertEquals(2, adjacent.size());
        assertTrue(adjacent.contains(alberta));
        assertTrue(adjacent.contains(kamchatka));
    }

    @Test
    public void placeArmiesOneInfantryIncreasesInfantryPieceCountByOne() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, ONE_INFANTRY);

        territory.placeArmies(pieces);

        assertEquals(
                ONE_INFANTRY,
                territory.getArmiesOfType(ArmyType.INFANTRY));
    }

    @Test
    public void placeArmiesTwoInfantryIncreasesPieceCountByTwo() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, TWO_INFANTRY);

        territory.placeArmies(pieces);

        assertEquals(
                TWO_INFANTRY,
                territory.getArmiesOfType(ArmyType.INFANTRY));
    }

    @Test
    public void placeArmiesTwoSequentialPlacementsAccumulatesPieceCount() {
        Territory territory = createTerritoryWithNoAdjacencies();
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, ONE_INFANTRY);

        territory.placeArmies(pieces);
        territory.placeArmies(pieces);

        assertEquals(
                TWO_INFANTRY,
                territory.getArmiesOfType(ArmyType.INFANTRY));
    }

}
