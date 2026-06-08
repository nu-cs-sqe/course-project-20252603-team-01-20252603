package code.controller;

import static org.easymock.EasyMock.createMock;

import code.model.GameModel;
import code.view.ConsoleView;

import org.junit.jupiter.api.Test;

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
}