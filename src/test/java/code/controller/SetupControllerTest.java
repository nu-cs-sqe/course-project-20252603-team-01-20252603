package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.*;
import code.view.ConsoleView;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests setup behavior for the SetupController class.
 */
public final class SetupControllerTest {

    private static final int MINIMUM_PLAYER_COUNT = 3;

    private static final int MAXIMUM_PLAYER_COUNT = 6;

    private static final int STARTING_ARMIES_THREE_PLAYERS = 35;

    private static final int STARTING_ARMIES_SIX_PLAYERS = 20;

    private static final int INVALID_PLAYER_COUNT = 2;

    private static final int FIRST_PLAYER_INDEX = 0;

    private static final int SECOND_PLAYER_INDEX = 1;

    private static final int THIRD_PLAYER_INDEX = 2;

    private static final int FOURTH_PLAYER_INDEX = 3;

    private static final int FIFTH_PLAYER_INDEX = 4;

    private static final int SIXTH_PLAYER_INDEX = 5;

    private static final int HIGHEST_PLAYER_INDEX = 2;

    private static final int ZERO_INFANTRY = 0;

    private static final int ONE_INFANTRY = 1;

    private static final int TWO_INFANTRY = 2;

    private static final int SETUP_INFANTRY_COUNT = 1;

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
    public void initializePlayersMinimumPlayerCountRegistersPlayers() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        final int startingArmies = STARTING_ARMIES_THREE_PLAYERS;
        final Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, startingArmies);
        final Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, startingArmies);
        final Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, startingArmies);
        final List<Player> players = List.of(firstPlayer, secondPlayer, thirdPlayer);

        int playerCount = MINIMUM_PLAYER_COUNT;
        expect(view.promptNumberOfPlayers()).andReturn(playerCount);
        expect(model.setPlayerCount(playerCount)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
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
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1)).andReturn("Cara");
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
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model,
                view,
                new FixedRandom(FIRST_PLAYER_INDEX));
        controller.initializePlayers();

        verify(model, view);
    }

    @Test
    public void initializePlayersMaximumPlayerCountRegistersPlayers() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        int startingArmies = STARTING_ARMIES_SIX_PLAYERS;
        final Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, startingArmies);
        final Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, startingArmies);
        final Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, startingArmies);
        final Player fourthPlayer = new HumanPlayer("Dan", PlayerColor.YELLOW, startingArmies);
        final Player fifthPlayer = new HumanPlayer("Eli", PlayerColor.BLACK, startingArmies);
        final Player sixthPlayer = new HumanPlayer("Frank", PlayerColor.PURPLE, startingArmies);
        final List<Player> players = List.of(
                firstPlayer,
                secondPlayer,
                thirdPlayer,
                fourthPlayer,
                fifthPlayer,
                sixthPlayer);

        expect(view.promptNumberOfPlayers()).andReturn(MAXIMUM_PLAYER_COUNT);
        expect(model.setPlayerCount(MAXIMUM_PLAYER_COUNT)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
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
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1)).andReturn("Cara");
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
        expect(view.promptPlayerName(FOURTH_PLAYER_INDEX + 1)).andReturn("Dan");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Dan", List.of(
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.YELLOW);
        expect(model.addPlayer("Dan", PlayerColor.YELLOW)).andReturn(fourthPlayer);
        expect(view.promptPlayerName(FIFTH_PLAYER_INDEX + 1)).andReturn("Eli");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Eli", List.of(
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLACK);
        expect(model.addPlayer("Eli", PlayerColor.BLACK)).andReturn(fifthPlayer);
        expect(view.promptPlayerName(SIXTH_PLAYER_INDEX + 1)).andReturn("Frank");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Frank", List.of(PlayerColor.PURPLE)))
                .andReturn(PlayerColor.PURPLE);
        expect(model.addPlayer("Frank", PlayerColor.PURPLE)).andReturn(sixthPlayer);
        expect(model.getPlayers()).andReturn(players);
        view.displayPlayers(players);
        expectLastCall().once();
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model,
                view,
                new FixedRandom(FIRST_PLAYER_INDEX));
        controller.initializePlayers();

        verify(model, view);
    }

    @Test
    public void initializePlayersInvalidPlayerCountRePromptsForPlayerCount() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        int startingArmies = STARTING_ARMIES_THREE_PLAYERS;
        final Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, startingArmies);
        final Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, startingArmies);
        final Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, startingArmies);
        final List<Player> players = List.of(firstPlayer, secondPlayer, thirdPlayer);

        expect(view.promptNumberOfPlayers()).andReturn(INVALID_PLAYER_COUNT);
        expect(model.setPlayerCount(INVALID_PLAYER_COUNT)).andReturn(false);
        view.displayError("Invalid number of players.");
        expectLastCall().once();
        expect(view.promptNumberOfPlayers()).andReturn(MINIMUM_PLAYER_COUNT);
        expect(model.setPlayerCount(MINIMUM_PLAYER_COUNT)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
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
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1)).andReturn("Cara");
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
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model, view, new FixedRandom(FIRST_PLAYER_INDEX));
        controller.initializePlayers();

        verify(model, view);
    }

    @Test
    public void initializePlayersDuplicatePlayerColorRePromptsForPlayerColor() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        int startingArmies = STARTING_ARMIES_THREE_PLAYERS;
        final Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, startingArmies);
        final Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, startingArmies);
        final Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, startingArmies);
        final List<Player> players = List.of(firstPlayer, secondPlayer, thirdPlayer);

        expect(view.promptNumberOfPlayers()).andReturn(MINIMUM_PLAYER_COUNT);
        expect(model.setPlayerCount(MINIMUM_PLAYER_COUNT)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW,
                PlayerColor.BLACK, PlayerColor.PURPLE)))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Bob", PlayerColor.RED)).andReturn(new NullPlayer());
        view.displayError("Color already selected.");
        expectLastCall().once();
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW,
                PlayerColor.BLACK, PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW,
                PlayerColor.BLACK, PlayerColor.PURPLE)))
                .andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1)).andReturn("Cara");
        expect(model.showAvailableColors()).andReturn(List.of(
                PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.BLACK, PlayerColor.PURPLE));
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.BLACK, PlayerColor.PURPLE)))
                .andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        expect(model.getPlayers()).andReturn(players);
        view.displayPlayers(players);
        expectLastCall().once();
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model, view, new FixedRandom(FIRST_PLAYER_INDEX));
        controller.initializePlayers();

        verify(model, view);
    }

    @Test
    public void initializePlayersLowestRandomIndexSetsCurrentPlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        int startingArmies = STARTING_ARMIES_THREE_PLAYERS;
        final Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, startingArmies);
        final Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, startingArmies);
        final Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, startingArmies);
        final List<Player> players = List.of(firstPlayer, secondPlayer, thirdPlayer);

        expectThreePlayerRegistration(model, view, players);
        expect(model.getPlayers()).andReturn(players);
        view.displayPlayers(players);
        expectLastCall().once();
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(firstPlayer);
        view.displayStartingPlayer(firstPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model,
                view,
                new FixedRandom(FIRST_PLAYER_INDEX));
        controller.initializePlayers();

        verify(model, view);
    }

    @Test
    public void initializePlayersHighestRandomIndexSetsCurrentPlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        int startingArmies = STARTING_ARMIES_THREE_PLAYERS;
        final Player firstPlayer = new HumanPlayer("Alice", PlayerColor.RED, startingArmies);
        final Player secondPlayer = new HumanPlayer("Bob", PlayerColor.BLUE, startingArmies);
        final Player thirdPlayer = new HumanPlayer("Cara", PlayerColor.GREEN, startingArmies);
        final List<Player> players = List.of(firstPlayer, secondPlayer, thirdPlayer);

        expectThreePlayerRegistration(model, view, players);
        expect(model.getPlayers()).andReturn(players);
        view.displayPlayers(players);
        expectLastCall().once();
        model.setCurrentPlayerIndex(HIGHEST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayer()).andReturn(thirdPlayer);
        view.displayStartingPlayer(thirdPlayer);
        expectLastCall().once();

        replay(model, view);

        SetupController controller = new SetupController(
                model,
                view,
                new FixedRandom(HIGHEST_PLAYER_INDEX));
        controller.initializePlayers();

        verify(model, view);
    }

    private void expectThreePlayerRegistration(
            final GameModel model,
            final ConsoleView view,
            final List<Player> players) {
        expect(view.promptNumberOfPlayers()).andReturn(MINIMUM_PLAYER_COUNT);
        expect(model.setPlayerCount(MINIMUM_PLAYER_COUNT)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(model.showAvailableColors()).andReturn(List.of(PlayerColor.values()));
        expect(view.promptPlayerColor("Alice", List.of(PlayerColor.values())))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(players.get(0));
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
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
        expect(model.addPlayer("Bob", PlayerColor.BLUE))
                .andReturn(players.get(1));
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1))
                .andReturn("Cara");
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
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(players.get(2));
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

    private HashMap<ArmyType, Integer> createOneInfantryPiece() {
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, SETUP_INFANTRY_COUNT);

        return pieces;
    }
    @Test
    public void handleTerritoryClaimingSuccessfulClaimAdvancesToNextPlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayStartingPlayer("Player 1");
        expectLastCall().once();

        expect(model.getUnclaimedTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayUnclaimedTerritoriesByContinent("North America: Alaska");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("Player 1 territories:");
        view.displayCurrentPlayerClaimingStatus("Player 1 territories:");
        expectLastCall().once();

        expect(view.getTerritoryChoiceDuringSetup()).andReturn("Alaska");
        expect(model.claimTerritoryDuringSetup("Alaska", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.areAllTerritoriesClaimed()).andReturn(true);

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaimingSuccessfulClaimsAdvanceEachTurn() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayStartingPlayer("Player 1");
        expectLastCall().once();

        expect(model.getUnclaimedTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayUnclaimedTerritoriesByContinent("North America: Alaska");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("Player 1 territories:");
        view.displayCurrentPlayerClaimingStatus("Player 1 territories:");
        expectLastCall().once();

        expect(view.getTerritoryChoiceDuringSetup()).andReturn("Alaska");
        expect(model.claimTerritoryDuringSetup("Alaska", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 2");
        view.displayStartingPlayer("Player 2");
        expectLastCall().once();

        expect(model.getUnclaimedTerritoriesByContinent())
                .andReturn("North America: Alberta");
        view.displayUnclaimedTerritoriesByContinent("North America: Alberta");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("Player 2 territories:");
        view.displayCurrentPlayerClaimingStatus("Player 2 territories:");
        expectLastCall().once();

        expect(view.getTerritoryChoiceDuringSetup()).andReturn("Alberta");
        expect(model.claimTerritoryDuringSetup("Alberta", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.areAllTerritoriesClaimed()).andReturn(true);

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

}
