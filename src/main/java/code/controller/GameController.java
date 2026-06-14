package code.controller;

import code.model.GameModel;
import code.view.ConsoleView;
import java.util.Random;

/**
 * Controls the overall game flow.
 */
public class GameController {

    private final GameModel model;

    private final ConsoleView view;

    private final SetupController setupController;

    private final TurnController turnController;

    public GameController() {
        this(new GameModel(new Random()), new ConsoleView());
    }

    GameController(
            final GameModel gameModel,
            final ConsoleView consoleView) {
        this(
                gameModel,
                consoleView,
                new SetupController(gameModel, consoleView),
                new TurnController(gameModel, consoleView));
    }

    GameController(
            final GameModel gameModel,
            final ConsoleView consoleView,
            final SetupController setup,
            final TurnController turns) {
        model = gameModel;
        view = consoleView;
        setupController = setup;
        turnController = turns;
    }

    GameController(
            final GameModel gameModel,
            final ConsoleView consoleView,
            final SetupController setup) {
        model = gameModel;
        view = consoleView;
        setupController = setup;
    }

    public void startGame() {
        setupController.initializeBoard();

        setupController.initializePlayers();
        setupController.handleTerritoryClaiming();
        runGameLoopUntilWinner();
    }

    private void runGameLoopUntilWinner() {
        while (true) {
            if (model.currentPlayerIsEliminated()) {
                model.advanceToNextActivePlayer();
                continue;
            }

            turnController.runPlayerTurn();

            if (model.currentPlayerHasWon()) {
                view.displayWinner(model.getCurrentPlayerName());
                return;
            }

            model.advanceToNextActivePlayer();
        }
    }
}
