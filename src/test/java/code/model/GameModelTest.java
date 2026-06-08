package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests board initialization behavior for the GameModel class.
 */
public final class GameModelTest {

    private static final int CONTINENT_COUNT = 6;
    private static final int TERRITORY_COUNT = 42;

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
    public void allTerritoriesStartUnclaimed() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean allUnclaimed = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .allMatch(Territory::isUnclaimed);

        assertTrue(allUnclaimed);
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
}