package code.controller;

import code.model.GameModel;
import code.model.Player;
import code.model.PlayerColor;
import code.view.ConsoleView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Random;

/**
 * Controls initial game setup before gameplay begins.
 */
public class SetupController {

    private final GameModel model;

    private final ConsoleView view;

    private final Random random;

    private boolean initialized;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "SetupController intentionally stores the model and view it controls."
    )
    public SetupController(final GameModel gameModel, final ConsoleView consoleView) {
        this(gameModel, consoleView, new Random());
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "SetupController intentionally stores setup dependencies."
    )
    SetupController(
            final GameModel gameModel,
            final ConsoleView consoleView,
            final Random randomGenerator) {
        model = gameModel;
        view = consoleView;
        random = randomGenerator;
        initialized = false;
    }

    public void initializeBoard() {
        if (!initialized) {
            model.initializeContinentsAndTerritories();
            initialized = true;
        }
    }

    public void initializePlayers() {
        int playerCount = view.promptNumberOfPlayers();
        while (!model.setPlayerCount(playerCount)) {
            view.displayError("Invalid number of players.");
            playerCount = view.promptNumberOfPlayers();
        }

        for (int playerNumber = 1; playerNumber <= playerCount; playerNumber++) {
            String playerName = view.promptPlayerName(playerNumber);
            List<PlayerColor> availableColors = model.showAvailableColors();
            PlayerColor playerColor = view.promptPlayerColor(playerName, availableColors);
            model.addPlayer(playerName, playerColor);
        }

        List<Player> players = model.getPlayers();
        view.displayPlayers(players);
        model.setCurrentPlayerIndex(random.nextInt(playerCount));
        view.displayStartingPlayer(model.getCurrentPlayer());
    }
}
