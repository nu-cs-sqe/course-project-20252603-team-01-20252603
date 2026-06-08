package code.controller;

import static org.easymock.EasyMock.createMock;

import code.model.GameModel;
import code.view.ConsoleView;

import org.junit.jupiter.api.Test;

/**
 * Tests game startup behavior for the GameController class.
 */
public final class GameControllerTest {

    @Test
    public void gameControllerConstructsWithDefaultDependencies() {
        new GameController();
    }

    @Test
    public void gameControllerConstructsWithInjectedModelAndView() {
        GameModel model = new GameModel();
        ConsoleView view = createMock(ConsoleView.class);

        new GameController(model, view);
    }
}