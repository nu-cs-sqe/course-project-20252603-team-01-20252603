package code.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.GameModel;
import code.view.ConsoleView;
import org.junit.jupiter.api.Test;

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
}