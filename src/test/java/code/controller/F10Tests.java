package code.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import code.model.ArmyType;
import code.model.GameModel;
import code.model.Player;
import code.model.PlayerColor;
import code.view.ConsoleView;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for F10 - Win Condition.
 */
public final class F10Tests {

    private static final int ONE_INFANTRY = 1;

    private static final int THREE_PLAYER_COUNT = 3;

    private static final int EXTRA_INFANTRY_FOR_FULL_BOARD_CLAIM = 7;

    private static final List<String> TERRITORY_NAMES = List.of(
            "Alaska", "Northwest Territory", "Greenland", "Alberta", "Ontario",
            "Quebec", "Western United States", "Eastern United States", "Central America",
            "Venezuela", "Peru", "Brazil", "Argentina",
            "Iceland", "Scandinavia", "Ukraine", "Great Britain", "Northern Europe",
            "Western Europe", "Southern Europe",
            "North Africa", "Egypt", "East Africa", "Congo", "South Africa", "Madagascar",
            "Ural", "Siberia", "Yakutsk", "Kamchatka", "Irkutsk", "Mongolia", "Japan",
            "Afghanistan", "China", "Middle East", "India", "Siam",
            "Indonesia", "New Guinea", "Western Australia", "Eastern Australia");

    private static final class NoOpSetupController extends SetupController {

        NoOpSetupController(final GameModel model, final ConsoleView view) {
            super(model, view);
        }

        @Override
        public void initializeBoard() {
        }

        @Override
        public void initializePlayers() {
        }

        @Override
        public void handleTerritoryClaiming() {
        }
    }

    private static final class NoOpTurnController extends TurnController {

        NoOpTurnController(final GameModel model, final ConsoleView view) {
            super(model, view);
        }

        @Override
        public void runPlayerTurn() {
        }
    }

    private static final class RecordingWinnerView extends ConsoleView {

        private String winnerName;

        @Override
        public void displayWinner(final String playerName) {
            winnerName = playerName;
        }

        private String getWinnerName() {
            return winnerName;
        }
    }

    private HashMap<ArmyType, Integer> createInfantryPieces(final int infantryCount) {
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, infantryCount);
        return pieces;
    }

    private GameModel createGameWherePlayerOneOwnsAllTerritories() {
        GameModel model = new GameModel(new Random(0));
        model.initializeContinentsAndTerritories();
        model.setPlayerCount(THREE_PLAYER_COUNT);

        Player winner = model.addPlayer("Player 1", PlayerColor.RED);
        model.addPlayer("Player 2", PlayerColor.BLUE);
        model.addPlayer("Player 3", PlayerColor.GREEN);
        winner.addArmies(createInfantryPieces(EXTRA_INFANTRY_FOR_FULL_BOARD_CLAIM));

        for (String territoryName : TERRITORY_NAMES) {
            model.setCurrentPlayerIndex(0);
            model.claimTerritoryDuringSetup(
                    territoryName,
                    createInfantryPieces(ONE_INFANTRY));
        }

        model.setCurrentPlayerIndex(0);
        return model;
    }

    @Test
    public void gameControllerDisplaysWinnerAfterCompletedTurnWhenCurrentPlayerOwnsAllTerritories() {
        GameModel model = createGameWherePlayerOneOwnsAllTerritories();
        RecordingWinnerView view = new RecordingWinnerView();
        GameController controller = new GameController(
                model,
                view,
                new NoOpSetupController(model, view),
                new NoOpTurnController(model, view));

        controller.startGame();

        assertEquals("Player 1", view.getWinnerName());
    }
}
