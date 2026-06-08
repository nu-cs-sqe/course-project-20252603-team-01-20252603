package code.controller;

import org.junit.jupiter.api.Test;

/**
 * Tests game startup behavior for the GameController class.
 */
public final class GameControllerTest {

    @Test
    public void gameControllerConstructsWithDefaultDependencies() {
        new GameController();
    }
}