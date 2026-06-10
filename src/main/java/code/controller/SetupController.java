package code.controller;


import code.model.ArmyType;
import code.model.GameModel;
import code.model.NullPlayer;
import code.model.Player;
import code.model.PlayerColor;
import code.view.ConsoleView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.HashMap;
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

    private static final int SETUP_INFANTRY_COUNT = 1;

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

        List<PlayerColor> availableColors = new ArrayList<>(List.of(
                PlayerColor.RED,
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));

        for (int playerNumber = 1; playerNumber <= playerCount; playerNumber++) {
            String playerName = view.promptPlayerName(playerNumber);
            Player player = new NullPlayer();
            while (player instanceof NullPlayer) {
                PlayerColor playerColor = view.promptPlayerColor(playerName, availableColors);
                if (!availableColors.contains(playerColor)) {
                    view.displayError("Color already selected.");
                    continue;
                }

                player = model.addPlayer(playerName, playerColor);
                if (player instanceof NullPlayer) {
                    view.displayError("Color already selected.");
                } else {
                    availableColors.remove(playerColor);
                }
            }
        }

        List<Player> players = model.getPlayers();
        view.displayPlayers(players);
        model.setCurrentPlayerIndex(random.nextInt(playerCount));
        view.displayStartingPlayer(model.getCurrentPlayer());
    }

    public void handleTerritoryClaiming() {

        while (!model.areAllTerritoriesClaimed()) {
            view.displayStartingPlayer(model.getCurrentPlayerName());
            view.displayUnclaimedTerritoriesByContinent(
                    model.getUnclaimedTerritoriesByContinent());
            view.displayCurrentPlayerClaimingStatus(
                    model.getCurrentPlayerTerritoriesByContinent());
            String territoryName = view.getTerritoryChoiceDuringSetup();
            HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();
            boolean claimed = model.claimTerritoryDuringSetup(territoryName, pieces);
            if (claimed) {
                model.advanceCurrentPlayerIndex();
            } else {
                view.displayError("Invalid territory claim.");
            }
        }
    }

    private HashMap<ArmyType, Integer> createOneInfantryPiece() {
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, SETUP_INFANTRY_COUNT);
        return pieces;
    }
}
