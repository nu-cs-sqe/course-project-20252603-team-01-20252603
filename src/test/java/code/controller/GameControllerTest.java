package code.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import code.model.GameModel;
import code.view.ConsoleView;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests game startup behavior for the GameController class.
 */
public final class GameControllerTest {

    @Test
    public void gameControllerConstructsWithDefaultDependencies() {
        GameController controller = new GameController();

        assertNotNull(controller);
    }

    @Test
    public void gameControllerConstructsWithInjectedModelAndView() {
        GameModel model = new GameModel(new Random(0));
        ConsoleView view = createMock(ConsoleView.class);

        GameController controller = new GameController(model, view);

        assertNotNull(controller);
    }

    @Test
    public void gameControllerConstructsWithInjectedSetupController() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController setupController = createMock(SetupController.class);

        GameController controller = new GameController(model, view, setupController);

        assertNotNull(controller);
    }

    @Test
    public void startGameRunsFullSetupFlow() {
        GameModel model = createMock(GameModel.class);
        ConsoleView view = createMock(ConsoleView.class);
        SetupController setupController = createMock(SetupController.class);

        setupController.initializeBoard();
        expectLastCall().once();

        setupController.initializePlayers();
        expectLastCall().once();

        setupController.handleTerritoryClaiming();
        expectLastCall().once();

        replay(model, view, setupController);

        GameController controller = new GameController(model, view, setupController);
        controller.startGame();

        verify(model, view, setupController);
    }
}