package code.controller;

import static org.easymock.EasyMock.createMock;

import code.model.GameModel;
import code.view.ConsoleView;

import org.junit.jupiter.api.Test;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

/**
 * Tests setup behavior for the SetupController class.
 */
public final class SetupControllerTest {

    @Test
    public void setupControllerConstructsWithModelAndView() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);

        new SetupController(model, view);
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
}
