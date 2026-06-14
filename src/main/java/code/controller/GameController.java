package code.controller;

import code.model.GameModel;
import code.view.ConsoleView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Random;

/**
 * Controls the overall game flow.
 */
public class GameController {

    private final GameModel model;

    private final ConsoleView view;

    private final SetupController setupController;

    public GameController() {
        this(new GameModel(new Random()), new ConsoleView());
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "GameController intentionally stores the model and view it controls."
    )
    GameController(
            final GameModel gameModel,
            final ConsoleView consoleView) {
        this(gameModel, consoleView, new SetupController(gameModel, consoleView));
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "GameController intentionally stores controller dependencies."
    )
    GameController(
            final GameModel gameModel,
            final ConsoleView consoleView,
            final SetupController setupController) {
        model = gameModel;
        view = consoleView;
        this.setupController = setupController;
    }

    public void startGame() {
        setupController.initializeBoard();
        setupController.initializePlayers();
        setupController.handleTerritoryClaiming();
    }
}