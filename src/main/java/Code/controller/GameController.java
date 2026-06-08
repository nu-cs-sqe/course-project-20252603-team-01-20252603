package code.controller;

import code.model.GameModel;
import code.view.ConsoleView;

/**
 * Controls the overall game flow.
 */
public class GameController {

    private final GameModel model;

    private final ConsoleView view;

    private final SetupController setupController;

    public GameController() {
        this(new GameModel(), new ConsoleView());
    }

    GameController(
            final GameModel gameModel,
            final ConsoleView consoleView) {
        model = gameModel;
        view = consoleView;
        setupController = new SetupController(model, view);
    }

    public void startGame() {
        setupController.initializeBoard();
    }
}