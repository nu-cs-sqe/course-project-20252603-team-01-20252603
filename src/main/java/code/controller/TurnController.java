package code.controller;

import code.model.GameModel;
import code.view.ConsoleView;

/**
 * Coordinates the phases of a player's turn.
 */
public class TurnController {

    private final GameModel model;

    private final ConsoleView view;

    public TurnController(final GameModel gameModel, final ConsoleView consoleView) {
        model = gameModel;
        view = consoleView;
    }

    public void handleReinforcement() {
        while (model.currentPlayerHasAvailableArmies()) {
            // Reinforcement loop will be added through TDD.
        }
    }
}