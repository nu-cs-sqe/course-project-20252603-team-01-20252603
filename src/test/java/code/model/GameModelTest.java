package code.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests board initialization behavior for the GameModel class.
 */
public final class GameModelTest {

    private static final int CONTINENT_COUNT = 6;

    private static final int TERRITORY_COUNT = 42;

    private static final int NORTH_AMERICA_TERRITORY_COUNT = 9;

    private static final int SOUTH_AMERICA_TERRITORY_COUNT = 4;

    private static final int EUROPE_TERRITORY_COUNT = 7;

    private static final int AFRICA_TERRITORY_COUNT = 6;

    private static final int ASIA_TERRITORY_COUNT = 12;

    private static final int AUSTRALIA_TERRITORY_COUNT = 4;

    private static final int NORTH_AMERICA_BONUS = 5;

    private static final int SOUTH_AMERICA_BONUS = 2;

    private static final int EUROPE_BONUS = 5;

    private static final int AFRICA_BONUS = 3;

    private static final int ASIA_BONUS = 7;

    private static final int AUSTRALIA_BONUS = 2;

    private static final int DECK_CARD_COUNT = 44;

    private static final int BELOW_MIN_PLAYER_COUNT = 2;

    @Test
    public void gameModelConstructsWithEmptyContinents() {
        GameModel gameModel = new GameModel();

        assertTrue(gameModel.getContinents().isEmpty());
    }

    @Test
    public void initializeCreatesSixContinents() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(CONTINENT_COUNT, gameModel.getContinents().size());
    }

    @Test
    public void initializeCreatesFortyTwoTerritories() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        int territoryCount = gameModel.getContinents()
                .stream()
                .mapToInt(continent -> continent.getTerritories().size())
                .sum();

        assertEquals(TERRITORY_COUNT, territoryCount);
    }

    @Test
    public void allTerritoriesStartWithZeroArmies() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean allHaveZeroArmies = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .allMatch(territory -> territory.getArmyCount() == 0);

        assertTrue(allHaveZeroArmies);
    }

    private Continent findContinent(
            final GameModel gameModel,
            final String continentName) {
        return gameModel.getContinents()
                .stream()
                .filter(continent -> continent.getName().equals(continentName))
                .findFirst()
                .get();
    }

    @Test
    public void eachContinentHasCorrectTerritoryCount() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(
                NORTH_AMERICA_TERRITORY_COUNT,
                findContinent(gameModel, "North America").getTerritories().size());
        assertEquals(
                SOUTH_AMERICA_TERRITORY_COUNT,
                findContinent(gameModel, "South America").getTerritories().size());
        assertEquals(
                EUROPE_TERRITORY_COUNT,
                findContinent(gameModel, "Europe").getTerritories().size());
        assertEquals(
                AFRICA_TERRITORY_COUNT,
                findContinent(gameModel, "Africa").getTerritories().size());
        assertEquals(
                ASIA_TERRITORY_COUNT,
                findContinent(gameModel, "Asia").getTerritories().size());
        assertEquals(
                AUSTRALIA_TERRITORY_COUNT,
                findContinent(gameModel, "Australia").getTerritories().size());
    }

    @Test
    public void eachContinentHasCorrectBonusArmyValue() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(
                NORTH_AMERICA_BONUS,
                findContinent(gameModel, "North America").getBonusArmies());
        assertEquals(
                SOUTH_AMERICA_BONUS,
                findContinent(gameModel, "South America").getBonusArmies());
        assertEquals(
                EUROPE_BONUS,
                findContinent(gameModel, "Europe").getBonusArmies());
        assertEquals(
                AFRICA_BONUS,
                findContinent(gameModel, "Africa").getBonusArmies());
        assertEquals(
                ASIA_BONUS,
                findContinent(gameModel, "Asia").getBonusArmies());
        assertEquals(
                AUSTRALIA_BONUS,
                findContinent(gameModel, "Australia").getBonusArmies());
    }

    @Test
    public void everyTerritoryBelongsToExactlyOneContinent() {
        GameModel gameModel = new GameModel();
        Set<Territory> territories = new HashSet<>();

        gameModel.initializeContinentsAndTerritories();

        gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .forEach(territories::add);

        assertEquals(TERRITORY_COUNT, territories.size());
    }

    private Territory findTerritory(
            final GameModel gameModel,
            final String territoryName) {
        return gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .filter(territory -> territory.getName().equals(territoryName))
                .findFirst()
                .get();
    }

    private boolean hasReciprocalAdjacency(final Territory territory) {
        return territory.getAdjacentTerritories()
                .stream()
                .allMatch(adjacentTerritory -> adjacentTerritory
                        .getAdjacentTerritories()
                        .contains(territory));
    }

    @Test
    public void everyTerritoryHasAtLeastOneAdjacentTerritory() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean allHaveAdjacency = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .allMatch(territory -> territory.getAdjacentTerritories().size() > 0);

        assertTrue(allHaveAdjacency);
    }

    @Test
    public void adjacencyIsReciprocalForAllTerritories() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean allReciprocal = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .allMatch(this::hasReciprocalAdjacency);

        assertTrue(allReciprocal);
    }

    @Test
    public void alaskaIsAdjacentToKamchatka() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        Territory alaska = findTerritory(gameModel, "Alaska");
        Territory kamchatka = findTerritory(gameModel, "Kamchatka");

        assertTrue(alaska.getAdjacentTerritories().contains(kamchatka));
        assertTrue(kamchatka.getAdjacentTerritories().contains(alaska));
    }

    @Test
    public void noTerritoryIsAdjacentToItself() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean noSelfAdjacency = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .noneMatch(territory -> territory.getAdjacentTerritories()
                        .contains(territory));

        assertTrue(noSelfAdjacency);
    }

    @Test
    public void deckHasFortyFourCardsAfterBoardInitialization() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(DECK_CARD_COUNT, gameModel.getDeckSize());
    }

    @Test
    public void deckIsNotEmptyAfterBoardInitialization() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertFalse(gameModel.isDeckEmpty());
    }

    @Test
    public void setPlayerCount_BelowMinimumPlayerCount_ReturnsFalse() {
        GameModel gameModel = new GameModel();

        assertFalse(gameModel.setPlayerCount(BELOW_MIN_PLAYER_COUNT));
    }
}
