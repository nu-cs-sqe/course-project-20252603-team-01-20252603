package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.ArmyType;
import code.model.GameModel;
import code.model.HumanPlayer;
import code.model.Player;
import code.model.PlayerColor;
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

    private final List<PlayerColor> allPlayableColors = List.of(
            PlayerColor.RED,
            PlayerColor.BLUE,
            PlayerColor.GREEN,
            PlayerColor.YELLOW,
            PlayerColor.BLACK,
            PlayerColor.PURPLE);

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

        int playerCount = MINIMUM_PLAYER_COUNT;
        expect(view.promptNumberOfPlayers()).andReturn(playerCount);
        expect(model.setPlayerCount(playerCount)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(view.promptPlayerColor("Alice", allPlayableColors))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1)).andReturn("Cara");
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayerName()).andReturn("Alice");
        view.displayCurrentPlayer("Alice");
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

        expect(view.promptNumberOfPlayers()).andReturn(MAXIMUM_PLAYER_COUNT);
        expect(model.setPlayerCount(MAXIMUM_PLAYER_COUNT)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(view.promptPlayerColor("Alice", allPlayableColors))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1)).andReturn("Cara");
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        expect(view.promptPlayerName(FOURTH_PLAYER_INDEX + 1)).andReturn("Dan");
        expect(view.promptPlayerColor("Dan", List.of(
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.YELLOW);
        expect(model.addPlayer("Dan", PlayerColor.YELLOW)).andReturn(fourthPlayer);
        expect(view.promptPlayerName(FIFTH_PLAYER_INDEX + 1)).andReturn("Eli");
        expect(view.promptPlayerColor("Eli", List.of(
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLACK);
        expect(model.addPlayer("Eli", PlayerColor.BLACK)).andReturn(fifthPlayer);
        expect(view.promptPlayerName(SIXTH_PLAYER_INDEX + 1)).andReturn("Frank");
        expect(view.promptPlayerColor("Frank", List.of(PlayerColor.PURPLE)))
                .andReturn(PlayerColor.PURPLE);
        expect(model.addPlayer("Frank", PlayerColor.PURPLE)).andReturn(sixthPlayer);
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayerName()).andReturn("Alice");
        view.displayCurrentPlayer("Alice");
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

        expect(view.promptNumberOfPlayers()).andReturn(INVALID_PLAYER_COUNT);
        expect(model.setPlayerCount(INVALID_PLAYER_COUNT)).andReturn(false);
        view.displayError("Invalid number of players.");
        expectLastCall().once();
        expect(view.promptNumberOfPlayers()).andReturn(MINIMUM_PLAYER_COUNT);
        expect(model.setPlayerCount(MINIMUM_PLAYER_COUNT)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(view.promptPlayerColor("Alice", allPlayableColors))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE,
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1)).andReturn("Cara");
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN,
                PlayerColor.YELLOW,
                PlayerColor.BLACK,
                PlayerColor.PURPLE))).andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayerName()).andReturn("Alice");
        view.displayCurrentPlayer("Alice");
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

        expect(view.promptNumberOfPlayers()).andReturn(MINIMUM_PLAYER_COUNT);
        expect(model.setPlayerCount(MINIMUM_PLAYER_COUNT)).andReturn(true);
        expect(view.promptPlayerName(FIRST_PLAYER_INDEX + 1)).andReturn("Alice");
        expect(view.promptPlayerColor("Alice", allPlayableColors))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(firstPlayer);
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW,
                PlayerColor.BLACK, PlayerColor.PURPLE)))
                .andReturn(PlayerColor.RED);
        view.displayError("Color already selected.");
        expectLastCall().once();
        expect(view.promptPlayerColor("Bob", List.of(
                PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.YELLOW,
                PlayerColor.BLACK, PlayerColor.PURPLE)))
                .andReturn(PlayerColor.BLUE);
        expect(model.addPlayer("Bob", PlayerColor.BLUE)).andReturn(secondPlayer);
        expect(view.promptPlayerName(THIRD_PLAYER_INDEX + 1)).andReturn("Cara");
        expect(view.promptPlayerColor("Cara", List.of(
                PlayerColor.GREEN, PlayerColor.YELLOW, PlayerColor.BLACK, PlayerColor.PURPLE)))
                .andReturn(PlayerColor.GREEN);
        expect(model.addPlayer("Cara", PlayerColor.GREEN)).andReturn(thirdPlayer);
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayerName()).andReturn("Alice");
        view.displayCurrentPlayer("Alice");
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
        model.setCurrentPlayerIndex(FIRST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayerName()).andReturn("Alice");
        view.displayCurrentPlayer("Alice");
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
        model.setCurrentPlayerIndex(HIGHEST_PLAYER_INDEX);
        expectLastCall().once();
        expect(model.getCurrentPlayerName()).andReturn("Cara");
        view.displayCurrentPlayer("Cara");
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
        expect(view.promptPlayerColor("Alice", allPlayableColors))
                .andReturn(PlayerColor.RED);
        expect(model.addPlayer("Alice", PlayerColor.RED)).andReturn(players.get(0));
        expect(view.promptPlayerName(SECOND_PLAYER_INDEX + 1)).andReturn("Bob");
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

    private void expectNoRemainingArmyPlacementComplete(
            final GameModel model,
            final ConsoleView view,
            final String currentPlayerName) {
        expect(model.getCurrentPlayerName()).andReturn(currentPlayerName);
        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn(currentPlayerName);
        view.displaySetupPhaseComplete();
        expectLastCall().once();
    }

    @Test
    public void handleTerritoryClaimingSuccessfulClaimAdvancesToNextPlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
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
        expect(model.getCurrentPlayerName()).andReturn("Player 2");
        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 2");
        view.displaySetupPhaseComplete();
        expectLastCall().once();

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaimingSuccessfulClaimsAdvanceEachTurn() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
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
        view.displayCurrentPlayer("Player 2");
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
        expectNoRemainingArmyPlacementComplete(model, view, "Player 3");
        SetupController controller = new SetupController(model, view);
        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaimingAlreadyClaimedTerritoryRepromptsSamePlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getUnclaimedTerritoriesByContinent())
                .andReturn("North America: Alberta");
        view.displayUnclaimedTerritoriesByContinent("North America: Alberta");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("Player 1 territories:");
        view.displayCurrentPlayerClaimingStatus("Player 1 territories:");
        expectLastCall().once();

        expect(view.getTerritoryChoiceDuringSetup()).andReturn("Alaska");
        expect(model.claimTerritoryDuringSetup("Alaska", pieces)).andReturn(false);

        view.displayError("Invalid territory claim.");
        expectLastCall().once();

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getUnclaimedTerritoriesByContinent())
                .andReturn("North America: Alberta");
        view.displayUnclaimedTerritoriesByContinent("North America: Alberta");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("Player 1 territories:");
        view.displayCurrentPlayerClaimingStatus("Player 1 territories:");
        expectLastCall().once();

        expect(view.getTerritoryChoiceDuringSetup()).andReturn("Alberta");
        expect(model.claimTerritoryDuringSetup("Alberta", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.areAllTerritoriesClaimed()).andReturn(true);
        expectNoRemainingArmyPlacementComplete(model, view, "Player 2");

        replay(model, view);
        SetupController controller = new SetupController(model, view);
        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaimingStopsWhenAllTerritoriesClaimed() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);

        expect(model.areAllTerritoriesClaimed()).andReturn(true);
        expectNoRemainingArmyPlacementComplete(model, view, "Player 1");

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaimingContinuesWhenOneTerritoryRemainsUnclaimed() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 3");
        view.displayCurrentPlayer("Player 3");
        expectLastCall().once();

        expect(model.getUnclaimedTerritoriesByContinent())
                .andReturn("Australia: Eastern Australia");
        view.displayUnclaimedTerritoriesByContinent("Australia: Eastern Australia");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("Player 3 territories:");
        view.displayCurrentPlayerClaimingStatus("Player 3 territories:");
        expectLastCall().once();

        expect(view.getTerritoryChoiceDuringSetup()).andReturn("Eastern Australia");
        expect(model.claimTerritoryDuringSetup("Eastern Australia", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.areAllTerritoriesClaimed()).andReturn(true);
        expectNoRemainingArmyPlacementComplete(model, view, "Player 2");

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaiming_TerritoryStillUnclaimed_DoesNotPromptForRemainingArmyPlacement() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
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
        expectNoRemainingArmyPlacementComplete(model, view, "Player 2");

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaiming_CurrentPlayerHasOneRemainingArmy_PlacesArmyAndCompletesSetup() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(true);
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerTerritoriesByContinent("North America: Alaska");
        expectLastCall().once();
        expect(view.promptCurrentPlayerTerritoryChoice()).andReturn("Alaska");
        expect(model.addArmiesDuringSetup("Alaska", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displaySetupPhaseComplete();
        expectLastCall().once();

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaiming_CurrentPlayerHasMultipleRemainingArmies_PlacesOneAndContinuesLater() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");

        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(true);
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerTerritoriesByContinent("North America: Alaska");
        expectLastCall().once();
        expect(view.promptCurrentPlayerTerritoryChoice()).andReturn("Alaska");
        expect(model.addArmiesDuringSetup("Alaska", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 2");
        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");

        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(true);
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerTerritoriesByContinent("North America: Alaska");
        expectLastCall().once();
        expect(view.promptCurrentPlayerTerritoryChoice()).andReturn("Alaska");
        expect(model.addArmiesDuringSetup("Alaska", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 2");
        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");

        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displaySetupPhaseComplete();
        expectLastCall().once();

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaiming_CurrentPlayerHasZeroArmies_SkipsToNextAvailablePlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");

        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 2");

        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(true);
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alberta");
        view.displayCurrentPlayerTerritoriesByContinent("North America: Alberta");
        expectLastCall().once();
        expect(view.promptCurrentPlayerTerritoryChoice()).andReturn("Alberta");
        expect(model.addArmiesDuringSetup("Alberta", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displaySetupPhaseComplete();
        expectLastCall().once();

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

    @Test
    public void handleTerritoryClaiming_MultiplePlayersHaveZeroArmies_SkipsToNextAvailablePlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController controller = new SetupController(model, view);
        HashMap<ArmyType, Integer> pieces = createOneInfantryPiece();

        expect(model.areAllTerritoriesClaimed()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");

        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 2");

        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 3");

        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(true);
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("Asia: China");
        view.displayCurrentPlayerTerritoriesByContinent("Asia: China");
        expectLastCall().once();
        expect(view.promptCurrentPlayerTerritoryChoice()).andReturn("China");
        expect(model.addArmiesDuringSetup("China", pieces)).andReturn(true);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        expect(model.hasCurrentPlayerAvailableArmies()).andReturn(false);
        expect(model.advanceCurrentPlayerIndex()).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displaySetupPhaseComplete();
        expectLastCall().once();

        replay(model, view);

        controller.handleTerritoryClaiming();

        verify(model, view);
    }

}
