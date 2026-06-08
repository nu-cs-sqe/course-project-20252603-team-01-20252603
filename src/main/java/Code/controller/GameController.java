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
        model = new GameModel();
        view = new ConsoleView();
        setupController = new SetupController(model, view);
    }
}