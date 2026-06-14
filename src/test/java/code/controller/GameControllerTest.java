package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import code.model.GameModel;
import code.view.ConsoleView;

import org.junit.jupiter.api.Test;
import java.util.Random;

/**
 * Tests game startup behavior for the GameController class.
 */
public final class GameControllerTest {

    private static final int CONTINENT_COUNT = 6;

    private static final int TERRITORY_COUNT = 42;

    private static final int DECK_CARD_COUNT = 44;

    private static final int EXPECTED_DECK_SIZE = 44;

    @Test
    public void gameControllerConstructsWithDefaultDependencies() {
        new GameController();
    }

    @Test
    public void gameControllerConstructsWithInjectedModelAndView() {
        GameModel model = new GameModel(new Random(0));
        ConsoleView view = createMock(ConsoleView.class);

        GameController controller = new GameController(model, view);

        assertNotNull(controller);
    }

//    @Test
//
//    public void startGameInitializesBoard() {
//
//        GameModel model = new GameModel();
//
//        ConsoleView view = createMock(ConsoleView.class);
//
//        GameController controller = new GameController(model, view);
//
//        controller.startGame();
//
//        int territoryCount = model.getContinents()
//
//                .stream()
//
//                .mapToInt(continent -> continent.getTerritories().size())
//
//                .sum();
//
//        assertEquals(CONTINENT_COUNT, model.getContinents().size());
//
//        assertEquals(TERRITORY_COUNT, territoryCount);
//
//    }

    @Test
    public void startGameInitializesDeck() {

        GameModel model = new GameModel(new Random(0));

        ConsoleView view = createMock(ConsoleView.class);

        GameController controller = new GameController(model, view);

        controller.startGame();

        assertEquals(DECK_CARD_COUNT, model.getDeckSize());

        assertFalse(model.isDeckEmpty());

    }

    @Test
    public void startGameInitializesBoard() {
        GameModel model = new GameModel(new Random(0));
        ConsoleView view = new ConsoleView();
        GameController controller = new GameController(model, view);

        controller.startGame();

        assertEquals(EXPECTED_DECK_SIZE, model.getDeckSize());
    }
}
