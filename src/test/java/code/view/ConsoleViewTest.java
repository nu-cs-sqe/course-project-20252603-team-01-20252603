package code.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the ConsoleView class.
 */
public final class ConsoleViewTest {

    private static final int MIN_PLAYER_COUNT = 3;

    private static final int MAX_PLAYER_COUNT = 6;

    private static final int BELOW_MIN_PLAYER_COUNT = 2;

    private static final int ABOVE_MAX_PLAYER_COUNT = 7;

    @Test
    public void promptNumberOfPlayers_MinimumPlayerCount_ReturnsPlayerCount() {
        ConsoleView view = createViewWithInput("3\n");

        assertEquals(MIN_PLAYER_COUNT, view.promptNumberOfPlayers());
    }

    @Test
    public void promptNumberOfPlayers_MaximumPlayerCount_ReturnsPlayerCount() {
        ConsoleView view = createViewWithInput("6\n");

        assertEquals(MAX_PLAYER_COUNT, view.promptNumberOfPlayers());
    }

    @Test
    public void promptNumberOfPlayers_BelowMinimumPlayerCount_ReturnsPlayerCount() {
        ConsoleView view = createViewWithInput("2\n");

        assertEquals(BELOW_MIN_PLAYER_COUNT, view.promptNumberOfPlayers());
    }

    @Test
    public void promptNumberOfPlayers_AboveMaximumPlayerCount_ReturnsPlayerCount() {
        ConsoleView view = createViewWithInput("7\n");

        assertEquals(ABOVE_MAX_PLAYER_COUNT, view.promptNumberOfPlayers());
    }

    @Test
    public void promptPlayerName_FirstPlayer_ReturnsPlayerName() {
        ConsoleView view = createViewWithInput("Alice\n");

        assertEquals("Alice", view.promptPlayerName(1));
    }

    @Test
    public void promptPlayerName_SixthPlayer_ReturnsPlayerName() {
        ConsoleView view = createViewWithInput("Frank\n");

        assertEquals("Frank", view.promptPlayerName(6));
    }

    private ConsoleView createViewWithInput(final String input) {
        return new ConsoleView(
                new Scanner(input),
                new PrintStream(new ByteArrayOutputStream()));
    }
}
