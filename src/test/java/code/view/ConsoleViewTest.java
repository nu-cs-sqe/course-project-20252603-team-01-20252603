package code.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import code.model.HumanPlayer;
import code.model.Player;
import code.model.PlayerColor;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
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

    @Test
    public void promptPlayerColor_AllColorsAvailable_ReturnsSelectedColor() {
        ConsoleView view = createViewWithInput("RED\n");

        assertEquals(
                PlayerColor.RED,
                view.promptPlayerColor("Alice", List.of(PlayerColor.values())));
    }

    @Test
    public void promptPlayerColor_OneColorAvailable_ReturnsSelectedColor() {
        ConsoleView view = createViewWithInput("PURPLE\n");

        assertEquals(
                PlayerColor.PURPLE,
                view.promptPlayerColor("Frank", List.of(PlayerColor.PURPLE)));
    }

    @Test
    public void promptPlayerColor_UnavailableColorEntered_ReturnsSelectedColor() {
        ConsoleView view = createViewWithInput("RED\n");

        assertEquals(
                PlayerColor.RED,
                view.promptPlayerColor("Bob", List.of(PlayerColor.BLUE)));
    }

    @Test
    public void displayPlayers_MinimumRegisteredPlayers_DisplaysPlayers() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        List<Player> players = List.of(
                new HumanPlayer("Alice", PlayerColor.RED, 35),
                new HumanPlayer("Bob", PlayerColor.BLUE, 35),
                new HumanPlayer("Cara", PlayerColor.GREEN, 35));

        view.displayPlayers(players);
        String displayedText = output.toString();

        assertTrue(displayedText.contains("Alice"));
        assertTrue(displayedText.contains("RED"));
        assertTrue(displayedText.contains("Bob"));
        assertTrue(displayedText.contains("BLUE"));
        assertTrue(displayedText.contains("Cara"));
        assertTrue(displayedText.contains("GREEN"));
    }

    @Test
    public void displayPlayers_MaximumRegisteredPlayers_DisplaysPlayers() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        List<Player> players = List.of(
                new HumanPlayer("Alice", PlayerColor.RED, 20),
                new HumanPlayer("Bob", PlayerColor.BLUE, 20),
                new HumanPlayer("Cara", PlayerColor.GREEN, 20),
                new HumanPlayer("Dan", PlayerColor.YELLOW, 20),
                new HumanPlayer("Eli", PlayerColor.BLACK, 20),
                new HumanPlayer("Frank", PlayerColor.PURPLE, 20));

        view.displayPlayers(players);
        String displayedText = output.toString();

        assertTrue(displayedText.contains("Alice"));
        assertTrue(displayedText.contains("RED"));
        assertTrue(displayedText.contains("Bob"));
        assertTrue(displayedText.contains("BLUE"));
        assertTrue(displayedText.contains("Cara"));
        assertTrue(displayedText.contains("GREEN"));
        assertTrue(displayedText.contains("Dan"));
        assertTrue(displayedText.contains("YELLOW"));
        assertTrue(displayedText.contains("Eli"));
        assertTrue(displayedText.contains("BLACK"));
        assertTrue(displayedText.contains("Frank"));
        assertTrue(displayedText.contains("PURPLE"));
    }

    private ConsoleView createViewWithInput(final String input) {
        return new ConsoleView(
                new Scanner(input),
                new PrintStream(new ByteArrayOutputStream()));
    }

    private ConsoleView createViewWithOutput(final ByteArrayOutputStream output) {
        return new ConsoleView(new Scanner(""), new PrintStream(output));
    }
}
