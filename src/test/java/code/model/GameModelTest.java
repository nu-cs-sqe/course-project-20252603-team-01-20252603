package code.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests board initialization behavior for the GameModel class.
 */
public final class GameModelTest {

    @Test
    public void gameModelConstructsWithEmptyContinents() {
        GameModel gameModel = new GameModel();

        assertTrue(gameModel.getContinents().isEmpty());
    }
}