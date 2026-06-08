package code.model;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the Territory class.
 */
public final class TerritoryTest {

    private static final int NORTH_AMERICA_BONUS_ARMIES = 5;

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
    public void newlyConstructedTerritoryStartsUnclaimed() {
        Continent northAmerica = createMock(Continent.class);
        Territory northwestTerritory = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);
        Territory kamchatka = createMock(Territory.class);
        List<Territory> neighbours = Arrays.asList(
                northwestTerritory,
                alberta,
                kamchatka);

        Territory alaska = new Territory("Alaska", northAmerica, neighbours);

        assertTrue(alaska.isUnclaimed());
    }
}