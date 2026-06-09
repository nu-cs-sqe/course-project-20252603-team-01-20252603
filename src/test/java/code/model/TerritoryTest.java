package code.model;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import code.model.ArmyType;
import code.model.Continent;
import code.model.NullPlayer;
import code.model.Player;
import code.model.Territory;

/**
 * Tests boundary values and core behavior for the Territory class.
 */
public final class TerritoryTest {

    private static final int NORTH_AMERICA_BONUS_ARMIES = 5;

    private static final int CONTINENT_BONUS = 5;

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
    public void constructorStoresContinent() {
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

        assertEquals(northAmerica, alaska.getContinent());
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

}
