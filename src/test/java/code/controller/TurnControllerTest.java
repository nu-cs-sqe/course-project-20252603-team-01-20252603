package code.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.ArmyType;
import code.model.GameModel;
import code.view.ConsoleView;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.expectLastCall;

/**
 * Tests turn flow behavior for the TurnController class.
 */
public final class TurnControllerTest {

    private static final int ZERO_ARMIES = 0;

    private static final int ONE_ARMY = 1;

    private static final int TWO_ARMIES = 2;

    private static final int THREE_ARMIES = 3;

    private static final int FIFTEEN_ARMIES = 15;

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
        GameModel model = new GameModel();
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
        TurnController controller = new TurnController(model, view);
        HashMap<ArmyType, Integer> invalidPieces =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);
        HashMap<ArmyType, Integer> validPieces =
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

        expect(model.placeArmiesDuringReinforcement("Alaska", validPieces))
                .andReturn(true);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        replay(model, view);

        controller.handleReinforcement();

        verify(model, view);
    }

    @Test
    public void handleReinforcementPassesMixedPlacementToModel() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        TurnController controller = new TurnController(model, view);
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

        controller.handleReinforcement();

        verify(model, view);
    }

    @Test
    public void handleReinforcementRepromptsAfterMalformedInput() {
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

        expect(model.placeArmiesDuringReinforcement("Alaska", pieces))
                .andReturn(true);

        expect(model.currentPlayerHasAvailableArmies()).andReturn(false);

        replay(model, view);

        controller.handleReinforcement();

        verify(model, view);
    }
}