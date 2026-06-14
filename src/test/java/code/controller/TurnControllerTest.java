package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.ArmyType;
import code.model.GameModel;
import code.model.TradeInPossibility;
import code.view.ConsoleView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests turn flow behavior for the TurnController class.
 */
public final class TurnControllerTest {

    private static final int MALFORMED_CARD_INPUT_SENTINEL = Integer.MIN_VALUE;

    private static final int ZERO_ARMIES = 0;

    private static final int ONE_ARMY = 1;

    private static final int TWO_ARMIES = 2;

    private static final int THREE_ARMIES = 3;

    private static final int FOUR_ARMIES = 4;

    private static final int FIFTEEN_ARMIES = 15;

    private static final int FIRST_CARD_INDEX = 1;

    private static final int SECOND_CARD_INDEX = 2;

    private static final int THIRD_CARD_INDEX = 3;

    private static final class RecordingTurnController extends TurnController {

        private final List<String> calls = new ArrayList<>();

        RecordingTurnController() {
            super(createMock(GameModel.class), createMock(ConsoleView.class));
        }

        @Override
        public void handleArmiesToAdd() {
            calls.add("handleArmiesToAdd");
        }

        @Override
        public void handleReinforcement() {
            calls.add("handleReinforcement");
        }

        @Override
        public void handleAttackPhase(final Object player) {
            calls.add("handleAttackPhase");
        }

        @Override
        public void handleFortifyPhase() {
            calls.add("handleFortifyPhase");
        }

        private List<String> getCalls() {
            return calls;
        }
    }

    private HashMap<ArmyType, Integer> createArmies(
            final int infantry,
            final int cavalry,
            final int artillery) {
        HashMap<ArmyType, Integer> armies = new HashMap<>();
        armies.put(ArmyType.INFANTRY, infantry);
        armies.put(ArmyType.CAVALRY, cavalry);
        armies.put(ArmyType.ARTILLERY, artillery);
        return armies;
    }

    @Test
    public void constructorCreatesTurnController() {
        GameModel model = new GameModel(new Random(0));
        ConsoleView view = new ConsoleView();

        TurnController controller = new TurnController(model, view);

        assertNotNull(controller);
    }

    @Test
    public void runPlayerTurnRunsFullPlayerTurnPhasesInOrder() {
        RecordingTurnController controller = new RecordingTurnController();

        controller.runPlayerTurn();

        assertEquals(
                List.of(
                        "handleArmiesToAdd",
                        "handleReinforcement",
                        "handleAttackPhase",
                        "handleFortifyPhase"),
                controller.getCalls());
    }

    @Test
    public void runPlayerTurnDoesNotAdvanceCurrentPlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();
        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();
        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.NOT_ALLOWED);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("0 Infantry");
        view.displayCurrentPlayerArmies("0 Infantry");
        expectLastCall().once();

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        view.displayNoValidAttacks();
        expectLastCall().once();
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();
        expect(view.promptFortifyChoice()).andReturn("no");

        replay(model, view);

        controller.runPlayerTurn();

        verify(model, view);
    }

    @Test
    public void handleReinforcementStopsWhenNoArmiesAvailable() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        replay(model, view);

        controller.handleReinforcement();

        verify(model, view);
    }

    @Test
    public void handleReinforcementDisplaysCurrentPlayerAndTerritories() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(view.promptReinforcement()).andReturn(List.of());

        view.displayError("Invalid reinforcement input.");
        expectLastCall().once();

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        replay(model, view);

        controller.handleReinforcement();

        verify(model, view);
    }

    @Test
    public void handleReinforcementPassesOneInfantryPlacementToModel() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        HashMap<ArmyType, Integer> pieces =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(view.promptReinforcement()).andReturn(List.of(
                "Alaska",
                "1",
                "0",
                "0"));

        expect(model.placeArmiesDuringReinforcement("Alaska", pieces))
                .andReturn(true);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        replay(model, view);

        controller.handleReinforcement();

        verify(model, view);
    }

    @Test
    public void handleReinforcementDisplaysErrorWhenPlacementFails() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        HashMap<ArmyType, Integer> invalidPieces =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(view.promptReinforcement()).andReturn(List.of(
                "Fake Territory",
                "1",
                "0",
                "0"));

        expect(model.placeArmiesDuringReinforcement(
                "Fake Territory",
                invalidPieces)).andReturn(false);

        view.displayError("Invalid reinforcement placement.");
        expectLastCall().once();

        expect(model.currentPlayerHasAvailableArmies()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(view.promptReinforcement()).andReturn(List.of(
                "Alaska",
                "1",
                "0",
                "0"));

        HashMap<ArmyType, Integer> validPieces =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        expect(model.placeArmiesDuringReinforcement("Alaska", validPieces))
                .andReturn(true);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleReinforcement();

        verify(model, view);
    }

    @Test
    public void handleReinforcementPassesMixedPlacementToModel() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        HashMap<ArmyType, Integer> pieces =
                createArmies(FIFTEEN_ARMIES, TWO_ARMIES, THREE_ARMIES);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(view.promptReinforcement()).andReturn(List.of(
                "Alaska",
                "15",
                "2",
                "3"));

        expect(model.placeArmiesDuringReinforcement("Alaska", pieces))
                .andReturn(true);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleReinforcement();

        verify(model, view);
    }

    @Test
    public void handleFortifyPhasePlayerSkipsFortification() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(view.promptFortifyChoice()).andReturn("no");

        replay(model, view);

        controller.handleFortifyPhase();

        verify(model, view);
    }

    @Test
    public void handleFortifyPhasePlayerSkipsFortificationWithSingleLetterChoice() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(view.promptFortifyChoice()).andReturn("n");

        replay(model, view);

        controller.handleFortifyPhase();

        verify(model, view);
    }

    @Test
    public void handleFortifyPhasePlayerChoosesToFortify() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(view.promptFortifyChoice()).andReturn("yes");
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();

        expect(view.promptFortifySourceTerritory()).andReturn("Alaska");
        expect(view.promptFortifyDestinationTerritory()).andReturn("Alberta");
        expect(view.promptFortifyArmyCount()).andReturn("2");
        expect(model.fortifyTerritory("Alaska", "Alberta", TWO_ARMIES)).andReturn(true);

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();

        replay(model, view);

        controller.handleFortifyPhase();

        verify(model, view);
    }

    @Test
    public void handleFortifyPhasePlayerChoosesToFortifyWithSingleLetterChoice() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(view.promptFortifyChoice()).andReturn("y");
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();

        expect(view.promptFortifySourceTerritory()).andReturn("Alaska");
        expect(view.promptFortifyDestinationTerritory()).andReturn("Alberta");
        expect(view.promptFortifyArmyCount()).andReturn("2");
        expect(model.fortifyTerritory("Alaska", "Alberta", TWO_ARMIES)).andReturn(true);

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();

        replay(model, view);

        controller.handleFortifyPhase();

        verify(model, view);
    }

    @Test
    public void handleFortifyPhaseInvalidFortifyChoiceRepromptsPlayer() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(view.promptFortifyChoice()).andReturn("maybe");
        view.displayError("Invalid fortify choice.");
        expectLastCall().once();
        expect(view.promptFortifyChoice()).andReturn("no");

        replay(model, view);

        controller.handleFortifyPhase();

        verify(model, view);
    }

    @Test
    public void handleFortifyPhaseNonNumericArmyCountRepromptsMoveInput() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(view.promptFortifyChoice()).andReturn("yes");
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();
        expect(view.promptFortifySourceTerritory()).andReturn("Alaska");
        expect(view.promptFortifyDestinationTerritory()).andReturn("Alberta");
        expect(view.promptFortifyArmyCount()).andReturn("two");
        view.displayError("Invalid army count.");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();
        expect(view.promptFortifySourceTerritory()).andReturn("Alaska");
        expect(view.promptFortifyDestinationTerritory()).andReturn("Alberta");
        expect(view.promptFortifyArmyCount()).andReturn("2");
        expect(model.fortifyTerritory("Alaska", "Alberta", TWO_ARMIES)).andReturn(true);

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();

        replay(model, view);

        controller.handleFortifyPhase();

        verify(model, view);
    }

    @Test
    public void handleFortifyPhaseModelRejectsInvalidFortifyMoveRepromptsMoveInput() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(view.promptFortifyChoice()).andReturn("yes");
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();
        expect(view.promptFortifySourceTerritory()).andReturn("Alaska");
        expect(view.promptFortifyDestinationTerritory()).andReturn("Alberta");
        expect(view.promptFortifyArmyCount()).andReturn("2");
        expect(model.fortifyTerritory("Alaska", "Alberta", TWO_ARMIES)).andReturn(false);
        view.displayError("Invalid fortify move.");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();
        expect(view.promptFortifySourceTerritory()).andReturn("Alaska");
        expect(view.promptFortifyDestinationTerritory()).andReturn("Alberta");
        expect(view.promptFortifyArmyCount()).andReturn("1");
        expect(model.fortifyTerritory("Alaska", "Alberta", ONE_ARMY)).andReturn(true);

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();

        replay(model, view);

        controller.handleFortifyPhase();

        verify(model, view);
    }

    @Test
    public void handleFortifyPhaseValidMoveAfterInvalidMoveEndsFortifyPhase() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(view.promptFortifyChoice()).andReturn("yes");
        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();
        expect(view.promptFortifySourceTerritory()).andReturn("Alaska");
        expect(view.promptFortifyDestinationTerritory()).andReturn("Alberta");
        expect(view.promptFortifyArmyCount()).andReturn("4");
        expect(model.fortifyTerritory("Alaska", "Alberta", FOUR_ARMIES)).andReturn(false);
        view.displayError("Invalid fortify move.");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();
        expect(view.promptFortifySourceTerritory()).andReturn("Alaska");
        expect(view.promptFortifyDestinationTerritory()).andReturn("Alberta");
        expect(view.promptFortifyArmyCount()).andReturn("1");
        expect(model.fortifyTerritory("Alaska", "Alberta", ONE_ARMY)).andReturn(true);

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska, Alberta");
        view.displayCurrentPlayerTerritoriesByContinent(
                "North America: Alaska, Alberta");
        expectLastCall().once();

        replay(model, view);

        controller.handleFortifyPhase();

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseValidAttackResolvesBattleAndDisplaysResult() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of(
                "Attacker dice: [6]",
                "Defender dice: [3]",
                "Attacker losses: 0",
                "Defender losses: 1",
                "Captured: false");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);

        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseInvalidAttackingTerritoryRepromptsForTerritories() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> invalidTerritoryChoices = List.of("Alberta", "Alaska");
        List<String> validTerritoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(invalidTerritoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alberta",
                "Alaska")).andThrow(new IllegalArgumentException(
                        "Current player must own the attacking territory."));
        view.displayError("Current player must own the attacking territory.");
        expectLastCall().once();

        expect(view.promptTerritoriesToAttack()).andReturn(validTerritoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseInvalidDefendingTerritoryRepromptsForTerritories() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> invalidTerritoryChoices = List.of("Alaska", "Ontario");
        List<String> validTerritoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(invalidTerritoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Ontario")).andThrow(new IllegalArgumentException(
                        "Attacking and defending territories must be adjacent."));
        view.displayError("Attacking and defending territories must be adjacent.");
        expectLastCall().once();

        expect(view.promptTerritoriesToAttack()).andReturn(validTerritoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseMalformedDiceInputRepromptsForDice() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> malformedDiceCounts = List.of(MALFORMED_CARD_INPUT_SENTINEL);
        List<Integer> validDiceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");

        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(malformedDiceCounts);
        view.displayError("Invalid dice input.");
        expectLastCall().once();
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(validDiceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);

        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseIncorrectNumberOfDiceEntriesRepromptsForDice() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> incompleteDiceCounts = List.of(ONE_ARMY);
        List<Integer> validDiceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");

        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(incompleteDiceCounts);
        view.displayError("Invalid dice input.");
        expectLastCall().once();
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(validDiceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseInvalidDiceCountRepromptsForDice() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> invalidDiceCounts = List.of(TWO_ARMIES, ONE_ARMY);
        List<Integer> validDiceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");

        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(invalidDiceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", TWO_ARMIES, ONE_ARMY))
                .andThrow(new IllegalArgumentException(
                        "Attacker cannot roll more dice than attacking territory armies minus one."));
        view.displayError("Attacker cannot roll more dice than attacking territory armies minus one.");
        expectLastCall().once();

        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(validDiceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseDisplaysBattleResultExactlyOnceAfterSuccessfulExecution() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseSkipsImmediatelyWhenPlayerChoosesNo() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("no");
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseDisplaysNoValidAttacksWhenNoneAvailable() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        view.displayNoValidAttacks();
        expectLastCall().once();
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseInvalidAttackChoiceReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("maybe");
        view.displayError("Invalid attack choice.");
        expectLastCall().once();
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("no");
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseValidAttackWithoutCapturePromptsAgainAndAwardsNoCard() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("no");
        expect(model.awardRiskCardIfCaptured(false)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseCapturedTerritoryPromptsForMovementAndCapturesTerritory() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(true);
        expect(view.promptCaptureArmyCount("Alaska", "Alberta")).andReturn("1");
        expect(model.captureTerritory("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn("Player 2");
        view.displayTerritoryCaptured("Alaska", "Alberta", ONE_ARMY);
        expectLastCall().once();
        expect(model.handlePlayerElimination("Player 2")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(true)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseNonNumericCaptureMovementReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(true);
        expect(view.promptCaptureArmyCount("Alaska", "Alberta")).andReturn("two");
        view.displayError("Invalid capture movement input.");
        expectLastCall().once();
        expect(view.promptCaptureArmyCount("Alaska", "Alberta")).andReturn("1");
        expect(model.captureTerritory("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn("Player 2");
        view.displayTerritoryCaptured("Alaska", "Alberta", ONE_ARMY);
        expectLastCall().once();
        expect(model.handlePlayerElimination("Player 2")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(true)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseInvalidCaptureMovementFromModelReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(true);
        expect(view.promptCaptureArmyCount("Alaska", "Alberta")).andReturn("1");
        expect(model.captureTerritory("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andThrow(new IllegalArgumentException(
                        "Must move at least as many armies as attacker dice."));
        view.displayError("Must move at least as many armies as attacker dice.");
        expectLastCall().once();
        expect(view.promptCaptureArmyCount("Alaska", "Alberta")).andReturn("2");
        expect(model.captureTerritory("Alaska", "Alberta", TWO_ARMIES, ONE_ARMY))
                .andReturn("Player 2");
        view.displayTerritoryCaptured("Alaska", "Alberta", TWO_ARMIES);
        expectLastCall().once();
        expect(model.handlePlayerElimination("Player 2")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(true)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseDisplaysDefenderEliminationAfterCapture() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(true);
        expect(view.promptCaptureArmyCount("Alaska", "Alberta")).andReturn("1");
        expect(model.captureTerritory("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn("Player 2");
        view.displayTerritoryCaptured("Alaska", "Alberta", ONE_ARMY);
        expectLastCall().once();
        expect(model.handlePlayerElimination("Player 2")).andReturn(true);
        view.displayPlayerElimination("Player 2");
        expectLastCall().once();
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(true)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseDoesNotDisplayDefenderEliminationWhenDefenderRemainsActive() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(true);
        expect(view.promptCaptureArmyCount("Alaska", "Alberta")).andReturn("1");
        expect(model.captureTerritory("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn("Player 2");
        view.displayTerritoryCaptured("Alaska", "Alberta", ONE_ARMY);
        expectLastCall().once();
        expect(model.handlePlayerElimination("Player 2")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(true)).andReturn(false);

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseAwardsRiskCardAfterAtLeastOneCapture() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> territoryChoices = List.of("Alaska", "Alberta");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> battleResult = List.of("Battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(territoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(battleResult);
        view.displayBattleResult(battleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(true);
        expect(view.promptCaptureArmyCount("Alaska", "Alberta")).andReturn("1");
        expect(model.captureTerritory("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn("Player 2");
        view.displayTerritoryCaptured("Alaska", "Alberta", ONE_ARMY);
        expectLastCall().once();
        expect(model.handlePlayerElimination("Player 2")).andReturn(false);
        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(true)).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayRiskCardAwarded("Player 1");
        expectLastCall().once();

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleAttackPhaseExecutesMultipleAttacksAndAwardsAtMostOneRiskCard() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
        List<String> firstTerritoryChoices = List.of("Alaska", "Alberta");
        List<String> secondTerritoryChoices = List.of("Alaska", "Ontario");
        List<Integer> diceCounts = List.of(ONE_ARMY, ONE_ARMY);
        List<String> firstBattleResult = List.of("First battle resolved");
        List<String> secondBattleResult = List.of("Second battle resolved");

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(firstTerritoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Alberta")).andReturn("Alberta");
        expect(view.promptNumberOfDice("Alaska", "Alberta")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Alberta", ONE_ARMY, ONE_ARMY))
                .andReturn(firstBattleResult);
        view.displayBattleResult(firstBattleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Alberta")).andReturn(false);

        expect(model.currentPlayerHasValidAttack()).andReturn(true);
        expect(view.promptAttackChoice()).andReturn("yes");
        expect(view.promptTerritoriesToAttack()).andReturn(secondTerritoryChoices);
        expect(model.validateTerritoriesForAttackAndReturnDefenderName(
                "Alaska",
                "Ontario")).andReturn("Ontario");
        expect(view.promptNumberOfDice("Alaska", "Ontario")).andReturn(diceCounts);
        expect(model.validateNumberOfDice("Alaska", "Ontario", ONE_ARMY, ONE_ARMY))
                .andReturn(true);
        expect(model.executeBattleAndReturnWinner("Alaska", "Ontario", ONE_ARMY, ONE_ARMY))
                .andReturn(secondBattleResult);
        view.displayBattleResult(secondBattleResult);
        expectLastCall().once();
        expect(model.isTerritoryCaptured("Ontario")).andReturn(true);
        expect(view.promptCaptureArmyCount("Alaska", "Ontario")).andReturn("1");
        expect(model.captureTerritory("Alaska", "Ontario", ONE_ARMY, ONE_ARMY))
                .andReturn("Player 2");
        view.displayTerritoryCaptured("Alaska", "Ontario", ONE_ARMY);
        expectLastCall().once();
        expect(model.handlePlayerElimination("Player 2")).andReturn(false);

        expect(model.currentPlayerHasValidAttack()).andReturn(false);
        expect(model.awardRiskCardIfCaptured(true)).andReturn(true);
        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayRiskCardAwarded("Player 1");
        expectLastCall().once();

        replay(model, view);

        controller.handleAttackPhase(null);

        verify(model, view);
    }

    @Test
    public void handleReinforcementRepromptsAfterMalformedInput() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(view.promptReinforcement()).andReturn(List.of());

        view.displayError("Invalid reinforcement input.");
        expectLastCall().once();

        expect(model.currentPlayerHasAvailableArmies()).andReturn(true);

        expect(model.getCurrentPlayerName()).andReturn("Player 1");
        view.displayCurrentPlayer("Player 1");
        expectLastCall().once();

        expect(model.getCurrentPlayerTerritoriesByContinent())
                .andReturn("North America: Alaska");
        view.displayCurrentPlayerClaimingStatus("North America: Alaska");
        expectLastCall().once();

        expect(view.promptReinforcement()).andReturn(List.of(
                "Alaska",
                "1",
                "0",
                "0"));

        HashMap<ArmyType, Integer> pieces =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        expect(model.placeArmiesDuringReinforcement("Alaska", pieces))
                .andReturn(true);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleReinforcement();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithTradeInNotAllowedAddsArmiesAndDisplaysAvailableArmies() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.NOT_ALLOWED);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=5}");

        view.displayCurrentPlayerArmies("{INFANTRY=5}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithNullTradeInPossibilityDoesNothingAfterAddingArmies() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(null);

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithOptionalValidTradeInProcessesTradeAndDisplaysAvailableArmies() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.ALLOWED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX))).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=9}");

        view.displayCurrentPlayerArmies("{INFANTRY=9}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithSingleNumericOptionalTradeInProcessesSelection() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.ALLOWED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(FIRST_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(FIRST_CARD_INDEX))).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=5}");

        view.displayCurrentPlayerArmies("{INFANTRY=5}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithOptionalTradeInSkipDisplaysAvailableArmies() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.ALLOWED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of());
        expect(model.handleCardTradeIn(List.of())).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=5}");

        view.displayCurrentPlayerArmies("{INFANTRY=5}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithMalformedOptionalTradeInReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.ALLOWED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(MALFORMED_CARD_INPUT_SENTINEL));
        view.displayError("Invalid card trade-in input.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of());
        expect(model.handleCardTradeIn(List.of())).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=5}");

        view.displayCurrentPlayerArmies("{INFANTRY=5}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithNumericInvalidOptionalTradeInReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.ALLOWED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX))).andReturn(false);
        view.displayError("Invalid card trade-in selection.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of());
        expect(model.handleCardTradeIn(List.of())).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=5}");

        view.displayCurrentPlayerArmies("{INFANTRY=5}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithOptionalInvalidThenMalformedTradeInReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.ALLOWED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX))).andReturn(false);
        view.displayError("Invalid card trade-in selection.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(MALFORMED_CARD_INPUT_SENTINEL));
        view.displayError("Invalid card trade-in input.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of());
        expect(model.handleCardTradeIn(List.of())).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=5}");

        view.displayCurrentPlayerArmies("{INFANTRY=5}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithRequiredValidTradeInProcessesTradeAndDisplaysAvailableArmies() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.REQUIRED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX))).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=11}");

        view.displayCurrentPlayerArmies("{INFANTRY=11}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithRequiredTradeInSkipReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.REQUIRED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of());
        view.displayError("Card trade-in is required.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX))).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=11}");

        view.displayCurrentPlayerArmies("{INFANTRY=11}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithRequiredMalformedTradeInReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.REQUIRED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(MALFORMED_CARD_INPUT_SENTINEL));
        view.displayError("Invalid card trade-in input.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX))).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=11}");

        view.displayCurrentPlayerArmies("{INFANTRY=11}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithRequiredNumericInvalidTradeInReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.REQUIRED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX))).andReturn(false);
        view.displayError("Invalid card trade-in selection.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX))).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=11}");

        view.displayCurrentPlayerArmies("{INFANTRY=11}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddWithRequiredInvalidThenMalformedTradeInReprompts() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.REQUIRED);
        expect(model.getCurrentPlayerCards()).andReturn("1: Infantry, 2: Cavalry, 3: Artillery");

        view.displayCurrentPlayerCards("1: Infantry, 2: Cavalry, 3: Artillery");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX))).andReturn(false);
        view.displayError("Invalid card trade-in selection.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(MALFORMED_CARD_INPUT_SENTINEL));
        view.displayError("Invalid card trade-in input.");
        expectLastCall().once();

        expect(view.promptChooseCardsToTradeIn()).andReturn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX));
        expect(model.handleCardTradeIn(List.of(
                FIRST_CARD_INDEX,
                SECOND_CARD_INDEX,
                THIRD_CARD_INDEX))).andReturn(true);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=11}");

        view.displayCurrentPlayerArmies("{INFANTRY=11}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }

    @Test
    public void handleArmiesToAddDisplaysUpdatedAvailableArmiesAfterProcessing() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        expectLastCall().once();

        model.addArmiesToCurrentPlayerBasedOnContinents();
        expectLastCall().once();

        expect(model.checkCardTradeInPossibility()).andReturn(TradeInPossibility.NOT_ALLOWED);
        expect(model.getCurrentPlayerAvailableArmies()).andReturn("{INFANTRY=5}");

        view.displayCurrentPlayerArmies("{INFANTRY=5}");
        expectLastCall().once();

        replay(model, view);

        TurnController controller = new TurnController(model, view);
        controller.handleArmiesToAdd();

        verify(model, view);
    }
}
