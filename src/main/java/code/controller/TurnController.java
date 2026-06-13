package code.controller;

import code.model.GameModel;
import code.view.ConsoleView;

import java.util.List;

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
            view.displayCurrentPlayer(model.getCurrentPlayerName());
            view.displayCurrentPlayerClaimingStatus(
                    model.getCurrentPlayerTerritoriesByContinent());

            List<String> reinforcementInput = view.promptReinforcement();

            if (reinforcementInput.isEmpty()) {
                view.displayError("Invalid reinforcement input.");
            }
        }
    }
}