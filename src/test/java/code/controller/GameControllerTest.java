package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import code.model.GameModel;
import code.view.ConsoleView;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tests game startup behavior for the GameController class.
 */
public final class GameControllerTest {

    private static final int DECK_CARD_COUNT = 44;

    private static final int WINNER_FOUND_ON_THIRD_CHECK = 3;

    private static final class RecordingSetupController extends SetupController {

        private final List<String> calls;

        RecordingSetupController(final List<String> recordedCalls) {
            super(new GameModel(new Random(0)), new ConsoleView());
            calls = recordedCalls;
        }

        @Override
        public void initializeBoard() {
            calls.add("setup");
        }
    }

    private static final class InitializingSetupController extends SetupController {

        private final GameModel model;

        InitializingSetupController(final GameModel gameModel) {
            super(gameModel, new ConsoleView());
            model = gameModel;
        }

        @Override
        public void initializeBoard() {
            model.initializeContinentsAndTerritories();
        }
    }

    private static final class RecordingTurnController extends TurnController {

        private final List<String> calls;

        RecordingTurnController(final List<String> recordedCalls) {
            super(new GameModel(new Random(0)), new ConsoleView());
            calls = recordedCalls;
        }

        @Override
        public void runPlayerTurn() {
            calls.add("turn");
        }
    }

    private static final class WinningAfterOneTurnGameModel extends GameModel {

        private final List<String> calls;

        WinningAfterOneTurnGameModel(final List<String> recordedCalls) {
            super(new Random(0));
            calls = recordedCalls;
        }

        @Override
        public boolean currentPlayerIsEliminated() {
            calls.add("check eliminated");
            return false;
        }

        @Override
        public boolean currentPlayerHasWon() {
            calls.add("check winner");
            return true;
        }

        @Override
        public String getCurrentPlayerName() {
            return "Player 1";
        }
    }

    private static final class ErrorIfWinnerCheckedBeforeTurnGameModel extends GameModel {

        private final List<String> calls;

        ErrorIfWinnerCheckedBeforeTurnGameModel(final List<String> recordedCalls) {
            super(new Random(0));
            calls = recordedCalls;
        }

        @Override
        public boolean currentPlayerIsEliminated() {
            calls.add("check eliminated");
            return false;
        }

        @Override
        public boolean currentPlayerHasWon() {
            if (!calls.contains("turn")) {
                throw new AssertionError("Winner checked before first completed turn.");
            }

            calls.add("check winner");
            return true;
        }

        @Override
        public String getCurrentPlayerName() {
            return "Player 1";
        }
    }

    private static final class WinnerAfterTurnNoAdvanceGameModel extends GameModel {

        private final List<String> calls;

        WinnerAfterTurnNoAdvanceGameModel(final List<String> recordedCalls) {
            super(new Random(0));
            calls = recordedCalls;
        }

        @Override
        public boolean currentPlayerIsEliminated() {
            calls.add("check eliminated");
            return false;
        }

        @Override
        public boolean currentPlayerHasWon() {
            calls.add("check winner");
            return true;
        }

        @Override
        public String getCurrentPlayerName() {
            calls.add("get winner name");
            return "Player 1";
        }

        @Override
        public boolean advanceToNextActivePlayer() {
            calls.add("advance");
            return true;
        }
    }

    private static final class WinnerAfterSecondTurnGameModel extends GameModel {

        private final List<String> calls;

        private int winnerChecks;

        WinnerAfterSecondTurnGameModel(final List<String> recordedCalls) {
            super(new Random(0));
            calls = recordedCalls;
            winnerChecks = 0;
        }

        @Override
        public boolean currentPlayerIsEliminated() {
            calls.add("check eliminated");
            return false;
        }

        @Override
        public boolean currentPlayerHasWon() {
            calls.add("check winner");
            winnerChecks++;
            return winnerChecks == 2;
        }

        @Override
        public boolean advanceToNextActivePlayer() {
            calls.add("advance");
            return true;
        }

        @Override
        public String getCurrentPlayerName() {
            return "Player 2";
        }
    }

    private static final class FirstPlayerEliminatedThenWinnerGameModel extends GameModel {

        private final List<String> calls;

        private int eliminatedChecks;

        FirstPlayerEliminatedThenWinnerGameModel(final List<String> recordedCalls) {
            super(new Random(0));
            calls = recordedCalls;
            eliminatedChecks = 0;
        }

        @Override
        public boolean currentPlayerIsEliminated() {
            calls.add("check eliminated");
            eliminatedChecks++;
            return eliminatedChecks == 1;
        }

        @Override
        public boolean advanceToNextActivePlayer() {
            calls.add("advance");
            return true;
        }

        @Override
        public boolean currentPlayerHasWon() {
            calls.add("check winner");
            return true;
        }

        @Override
        public String getCurrentPlayerName() {
            return "Player 2";
        }
    }

    private static final class WrapToFirstPlayerGameModel extends GameModel {

        private final List<String> calls;

        private int winnerChecks;

        WrapToFirstPlayerGameModel(final List<String> recordedCalls) {
            super(new Random(0));
            calls = recordedCalls;
            winnerChecks = 0;
        }

        @Override
        public boolean currentPlayerIsEliminated() {
            calls.add("check eliminated");
            return false;
        }

        @Override
        public boolean currentPlayerHasWon() {
            calls.add("check winner");
            winnerChecks++;
            return winnerChecks == 2;
        }

        @Override
        public boolean advanceToNextActivePlayer() {
            calls.add("wrap advance");
            return true;
        }

        @Override
        public String getCurrentPlayerName() {
            return "Player 1";
        }
    }

    private static final class WinnerAfterThirdTurnGameModel extends GameModel {

        private final List<String> calls;

        private int winnerChecks;

        WinnerAfterThirdTurnGameModel(final List<String> recordedCalls) {
            super(new Random(0));
            calls = recordedCalls;
            winnerChecks = 0;
        }

        @Override
        public boolean currentPlayerIsEliminated() {
            calls.add("check eliminated");
            return false;
        }

        @Override
        public boolean currentPlayerHasWon() {
            calls.add("check winner");
            winnerChecks++;
            return winnerChecks == WINNER_FOUND_ON_THIRD_CHECK;
        }

        @Override
        public boolean advanceToNextActivePlayer() {
            calls.add("advance");
            return true;
        }

        @Override
        public String getCurrentPlayerName() {
            return "Player 3";
        }
    }

    private static final class RecordingConsoleView extends ConsoleView {

        private final List<String> calls;

        RecordingConsoleView(final List<String> recordedCalls) {
            calls = recordedCalls;
        }

        @Override
        public void displayWinner(final String playerName) {
            calls.add("display winner");
        }
    }

    private static final class RecordingWinnerNameConsoleView extends ConsoleView {

        private final List<String> calls;

        RecordingWinnerNameConsoleView(final List<String> recordedCalls) {
            calls = recordedCalls;
        }

        @Override
        public void displayWinner(final String playerName) {
            calls.add("display winner " + playerName);
        }
    }

    private static final class FailsOnSecondTurnController extends TurnController {

        private final List<String> calls;

        private int turnCount;

        FailsOnSecondTurnController(final List<String> recordedCalls) {
            super(new GameModel(new Random(0)), new ConsoleView());
            calls = recordedCalls;
            turnCount = 0;
        }

        @Override
        public void runPlayerTurn() {
            turnCount++;

            if (turnCount > 1) {
                throw new AssertionError("Turn ran after winner was displayed.");
            }

            calls.add("turn");
        }
    }

    @Test
    public void gameControllerConstructsWithDefaultDependencies() {
        new GameController();
    }

    @Test
    public void gameControllerConstructsWithInjectedModelAndView() {
        GameModel model = new GameModel(new Random(0));
        ConsoleView view = createMock(ConsoleView.class);

        GameController controller = new GameController(model, view);

        assertNotNull(controller);
    }

//    @Test
//
//    public void startGameInitializesBoard() {
//
//        GameModel model = new GameModel();
//
//        ConsoleView view = createMock(ConsoleView.class);
//
//        GameController controller = new GameController(model, view);
//
//        controller.startGame();
//
//        int territoryCount = model.getContinents()
//
//                .stream()
//
//                .mapToInt(continent -> continent.getTerritories().size())
//
//                .sum();
//
//        assertEquals(CONTINENT_COUNT, model.getContinents().size());
//
//        assertEquals(TERRITORY_COUNT, territoryCount);
//
//    }

    @Test
    public void startGameInitializesDeck() {

        List<String> calls = new ArrayList<>();

        GameModel model = new WinningAfterOneTurnGameModel(calls);

        ConsoleView view = new RecordingConsoleView(calls);

        GameController controller = new GameController(
                model,
                view,
                new RecordingSetupController(calls),
                new RecordingTurnController(calls));

        controller.startGame();

        assertEquals(DECK_CARD_COUNT, model.getDeckSize());

        assertFalse(model.isDeckEmpty());

    }

    @Test
    public void startGameDelegatesBoardInitializationToSetupController() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WinningAfterOneTurnGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);

        GameController controller = new GameController(
                model,
                view,
                new RecordingSetupController(calls),
                new RecordingTurnController(calls));
        controller.startGame();

        assertEquals("setup", calls.get(0));
    }

    @Test
    public void startGameInitializesBoard() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WinningAfterOneTurnGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        GameController controller = new GameController(
                model,
                view,
                new InitializingSetupController(model),
                new RecordingTurnController(calls));

        controller.startGame();

        String unclaimedTerritories = model.getUnclaimedTerritoriesByContinent();
        assertTrue(unclaimedTerritories.contains("North America"));
        assertTrue(unclaimedTerritories.contains("Alaska"));
    }

    @Test
    public void startGameRunsSetupBeforeFirstPlayerTurn() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WinningAfterOneTurnGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new RecordingTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of("setup", "check eliminated", "turn", "check winner", "display winner"),
                calls);
    }

    @Test
    public void startGameChecksWinnerOnlyAfterFirstCompletedTurn() {
        List<String> calls = new ArrayList<>();
        GameModel model = new ErrorIfWinnerCheckedBeforeTurnGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new RecordingTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of("setup", "check eliminated", "turn", "check winner", "display winner"),
                calls);
    }

    @Test
    public void startGameDisplaysWinnerAndStopsWithoutAdvancing() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WinnerAfterTurnNoAdvanceGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new RecordingTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of(
                        "setup",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "get winner name",
                        "display winner"),
                calls);
    }

    @Test
    public void startGameAdvancesToNextActivePlayerWhenNoWinnerAfterTurn() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WinnerAfterSecondTurnGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new RecordingTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of(
                        "setup",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "advance",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "display winner"),
                calls);
    }

    @Test
    public void startGameSkipsEliminatedCurrentPlayerBeforeRunningTurn() {
        List<String> calls = new ArrayList<>();
        GameModel model = new FirstPlayerEliminatedThenWinnerGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new RecordingTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of(
                        "setup",
                        "check eliminated",
                        "advance",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "display winner"),
                calls);
    }

    @Test
    public void startGameDelegatesTurnOrderWrapToModelAdvancement() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WrapToFirstPlayerGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new RecordingTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of(
                        "setup",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "wrap advance",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "display winner"),
                calls);
    }

    @Test
    public void startGameContinuesAcrossMultipleNonWinningTurns() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WinnerAfterThirdTurnGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new RecordingTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of(
                        "setup",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "advance",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "advance",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "display winner"),
                calls);
    }

    @Test
    public void startGameDisplaysWinnerUsingCurrentPlayerName() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WinningAfterOneTurnGameModel(calls);
        ConsoleView view = new RecordingWinnerNameConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new RecordingTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of(
                        "setup",
                        "check eliminated",
                        "turn",
                        "check winner",
                        "display winner Player 1"),
                calls);
    }

    @Test
    public void startGameStopsLoopAfterWinnerIsDisplayed() {
        List<String> calls = new ArrayList<>();
        GameModel model = new WinningAfterOneTurnGameModel(calls);
        ConsoleView view = new RecordingConsoleView(calls);
        SetupController setupController = new RecordingSetupController(calls);
        TurnController turnController = new FailsOnSecondTurnController(calls);
        GameController controller = new GameController(
                model,
                view,
                setupController,
                turnController);

        controller.startGame();

        assertEquals(
                List.of("setup", "check eliminated", "turn", "check winner", "display winner"),
                calls);
    }
}
