package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.ArmyType;
import code.model.GameModel;
import code.model.TradeInPossibility;
import code.view.ConsoleView;
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

    private static final int FIFTEEN_ARMIES = 15;

    private static final int FIRST_CARD_INDEX = 1;

    private static final int SECOND_CARD_INDEX = 2;

    private static final int THIRD_CARD_INDEX = 3;

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
