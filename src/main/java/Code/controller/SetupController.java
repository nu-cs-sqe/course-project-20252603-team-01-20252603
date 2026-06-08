package code.controller;

import code.model.GameModel;
import code.view.ConsoleView;

/**
 * Controls initial game setup before gameplay begins.
 */
public class SetupController {

    private final GameModel model;

    private boolean initialized;

    public SetupController(final GameModel gameModel, final ConsoleView consoleView) {
        model = gameModel;
        initialized = false;
    }

    public void initializeBoard() {
        if (!initialized) {
            model.initializeContinentsAndTerritories();
            initialized = true;
        }
    }
}