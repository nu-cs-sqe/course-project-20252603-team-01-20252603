package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    private static final int CONTINENT_COUNT = 6;

    private static final int TERRITORY_COUNT = 42;

    private static final int DECK_CARD_COUNT = 44;

    private static final int EXPECTED_DECK_SIZE = 44;

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

        GameModel model = new GameModel(new Random(0));

        ConsoleView view = createMock(ConsoleView.class);

        GameController controller = new GameController(model, view);

        controller.startGame();

        assertEquals(DECK_CARD_COUNT, model.getDeckSize());

        assertFalse(model.isDeckEmpty());

    }

    @Test
    public void startGameDelegatesBoardInitializationToSetupController() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.initializeContinentsAndTerritories();
        expectLastCall().once();

        replay(model, view);

        GameController controller = new GameController(model, view);
        controller.startGame();

        verify(model, view);
    }

    @Test
    public void startGameInitializesBoard() {
        GameModel model = new GameModel(new Random(0));
        ConsoleView view = new ConsoleView();
        GameController controller = new GameController(model, view);

        controller.startGame();

        assertEquals(EXPECTED_DECK_SIZE, model.getDeckSize());
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
}
