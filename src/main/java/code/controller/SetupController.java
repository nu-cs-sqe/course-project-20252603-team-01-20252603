package code.controller;

import code.model.GameModel;
import code.view.ConsoleView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Controls initial game setup before gameplay begins.
 */
public class SetupController {

    private final GameModel model;

    private boolean initialized;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "SetupController intentionally stores the game model it controls."
    )
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
