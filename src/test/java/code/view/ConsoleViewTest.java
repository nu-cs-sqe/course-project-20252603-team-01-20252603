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

    private ConsoleView createViewWithInput(final String input) {
        return new ConsoleView(
                new Scanner(input),
                new PrintStream(new ByteArrayOutputStream()));
    }
}
