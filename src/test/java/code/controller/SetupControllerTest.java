package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.GameModel;
import code.model.HumanPlayer;
import code.model.NullPlayer;
import code.model.Player;
import code.model.PlayerColor;
import code.view.ConsoleView;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests setup behavior for the SetupController class.
 */
public final class SetupControllerTest {

    @Test
    public void setupControllerConstructsWithModelAndView() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        SetupController controller = new SetupController(model, view);

        assertNotNull(controller);
    }

    @Test
    public void initializeBoardDelegatesToModel() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.initializeContinentsAndTerritories();
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(model, view);
        controller.initializeBoard();

        verify(model, view);
    }

    @Test
    public void initializeBoardOnlyDelegatesOnce() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.initializeContinentsAndTerritories();
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(model, view);
        controller.initializeBoard();
        controller.initializeBoard();

        verify(model, view);
    }

    @Test
    public void initializePlayers_MinimumPlayerCount_RegistersPlayers() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, 35);
        Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, 35);
        Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, 35);
        List<Player> players = List.of(firstPlayer, secondPlayer, thirdPlayer);

        expect(view.promptNumberOfPlayers()).andReturn(3);
        expect(model.setPlayerCount(3)).andReturn(true);
        expect(view.promptPlayerName(1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(2)).andReturn("Bob");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(3)).andReturn("Cara");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        expect(model.getPlayers()).andReturn(players);
        view.displayPlayers(players);
        expectLastCall().once();
        model.setCurrentPlayerIndex(0);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model,
                view,
                new FixedRandom(0));
        controller.initializePlayers();

        verify(model, view);
    }

    @Test
    public void initializePlayers_MaximumPlayerCount_RegistersPlayers() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, 20);
        Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, 20);
        Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, 20);
        Player fourthPlayer = new HumanPlayer("Dan", PlayerColor.YELLOW, 20);
        Player fifthPlayer = new HumanPlayer("Eli", PlayerColor.BLACK, 20);
        Player sixthPlayer = new HumanPlayer("Frank", PlayerColor.PURPLE, 20);
        List<Player> players = List.of(
                firstPlayer,
                secondPlayer,
                thirdPlayer,
                fourthPlayer,
                fifthPlayer,
                sixthPlayer);

        expect(view.promptNumberOfPlayers()).andReturn(6);
        expect(model.setPlayerCount(6)).andReturn(true);
        expect(view.promptPlayerName(1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(2)).andReturn("Bob");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(3)).andReturn("Cara");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        expect(view.promptPlayerName(4)).andReturn("Dan");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Dan", List.of(
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.YELLOW);
        expect(model.addPlayer("Dan", PlayerColor.YELLOW)).andReturn(fourthPlayer);
        expect(view.promptPlayerName(5)).andReturn("Eli");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Eli", List.of(
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLACK);
        expect(model.addPlayer("Eli", PlayerColor.BLACK)).andReturn(fifthPlayer);
        expect(view.promptPlayerName(6)).andReturn("Frank");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Frank", List.of(PlayerColor.PURPLE)))
                .andReturn(PlayerColor.PURPLE);
        expect(model.addPlayer("Frank", PlayerColor.PURPLE)).andReturn(sixthPlayer);
        expect(model.getPlayers()).andReturn(players);
        view.displayPlayers(players);
        expectLastCall().once();
        model.setCurrentPlayerIndex(0);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model,
                view,
                new FixedRandom(0));
        controller.initializePlayers();

        verify(model, view);
    }

    @Test
    public void initializePlayers_InvalidPlayerCount_RePromptsForPlayerCount() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, 35);
        Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, 35);
        Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, 35);
        List<Player> players = List.of(firstPlayer, secondPlayer, thirdPlayer);

        expect(view.promptNumberOfPlayers()).andReturn(2);
        expect(model.setPlayerCount(2)).andReturn(false);
        view.displayError("Invalid number of players.");
        expectLastCall().once();
        expect(view.promptNumberOfPlayers()).andReturn(3);
        expect(model.setPlayerCount(3)).andReturn(true);
        expect(view.promptPlayerName(1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(2)).andReturn("Bob");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(3)).andReturn("Cara");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        expect(model.getPlayers()).andReturn(players);
        view.displayPlayers(players);
        expectLastCall().once();
        model.setCurrentPlayerIndex(0);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model,
                view,
                new FixedRandom(0));
        controller.initializePlayers();

        verify(model, view);
    }

    @Test
    public void initializePlayers_DuplicatePlayerColor_RePromptsForPlayerColor() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, 35);
        Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, 35);
        Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, 35);
        List<Player> players = List.of(firstPlayer, secondPlayer, thirdPlayer);

        expect(view.promptNumberOfPlayers()).andReturn(3);
        expect(model.setPlayerCount(3)).andReturn(true);
        expect(view.promptPlayerName(1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(2)).andReturn("Bob");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.RED);
        expect(model.addPlayer("Bob", PlayerColor.RED)).andReturn(new NullPlayer());
        view.displayError("Color already selected.");
        expectLastCall().once();
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(3)).andReturn("Cara");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        expect(model.getPlayers()).andReturn(players);
        view.displayPlayers(players);
        expectLastCall().once();
        model.setCurrentPlayerIndex(0);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model,
                view,
                new FixedRandom(0));
        controller.initializePlayers();

        verify(model, view);
    }

    private static final class FixedRandom extends Random {

        private final int value;

        private FixedRandom(final int fixedValue) {
            value = fixedValue;
        }

        @Override
        public int nextInt(final int bound) {
            return value;
        }
    }
}
