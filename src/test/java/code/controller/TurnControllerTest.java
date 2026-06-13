package code.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.GameModel;
import code.view.ConsoleView;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 * Tests turn flow behavior for the TurnController class.
 */
public final class TurnControllerTest {

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
}