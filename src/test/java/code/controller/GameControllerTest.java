package code.controller;

import static org.easymock.EasyMock.createMock;

import code.model.GameModel;
import code.view.ConsoleView;

import org.junit.jupiter.api.Test;

/**
 * Tests game startup behavior for the GameController class.
 */
public final class GameControllerTest {

    private static final int CONTINENT_COUNT = 6;

    private static final int TERRITORY_COUNT = 42;

    private static final int DECK_CARD_COUNT = 44;

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