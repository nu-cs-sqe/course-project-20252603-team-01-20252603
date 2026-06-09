package code.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import code.model.HumanPlayer;
import code.model.Player;
import code.model.PlayerColor;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
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

    private static final int SIXTH_PLAYER_POSITION = 6;

    private static final int STARTING_ARMIES_THREE_PLAYERS = 35;

    private static final int STARTING_ARMIES_SIX_PLAYERS = 20;

    @Test
    public void promptNumberOfPlayersMinimumPlayerCountReturnsPlayerCount() {
        ConsoleView view = createViewWithInput("3\n");

        assertEquals(MIN_PLAYER_COUNT, view.promptNumberOfPlayers());
    }

    @Test
    public void promptNumberOfPlayersMaximumPlayerCountReturnsPlayerCount() {
        ConsoleView view = createViewWithInput("6\n");

        assertEquals(MAX_PLAYER_COUNT, view.promptNumberOfPlayers());
    }

    @Test
    public void promptNumberOfPlayersBelowMinimumPlayerCountReturnsPlayerCount() {
        ConsoleView view = createViewWithInput("2\n");

        assertEquals(BELOW_MIN_PLAYER_COUNT, view.promptNumberOfPlayers());
    }

    @Test
    public void promptNumberOfPlayersAboveMaximumPlayerCountReturnsPlayerCount() {
        ConsoleView view = createViewWithInput("7\n");

        assertEquals(ABOVE_MAX_PLAYER_COUNT, view.promptNumberOfPlayers());
    }

    @Test
    public void promptPlayerNameFirstPlayerReturnsPlayerName() {
        ConsoleView view = createViewWithInput("Alice\n");

        assertEquals("Alice", view.promptPlayerName(1));
    }

    @Test
    public void promptPlayerNameSixthPlayerReturnsPlayerName() {
        ConsoleView view = createViewWithInput("Frank\n");

        assertEquals("Frank", view.promptPlayerName(SIXTH_PLAYER_POSITION));
    }

    @Test
    public void promptPlayerColorAllColorsAvailableReturnsSelectedColor() {
        ConsoleView view = createViewWithInput("RED\n");

        assertEquals(
                PlayerColor.RED,
                view.promptPlayerColor("Alice", List.of(PlayerColor.values())));
    }

    @Test
    public void promptPlayerColorOneColorAvailableReturnsSelectedColor() {
        ConsoleView view = createViewWithInput("PURPLE\n");

        assertEquals(
                PlayerColor.PURPLE,
                view.promptPlayerColor("Frank", List.of(PlayerColor.PURPLE)));
    }

    @Test
    public void promptPlayerColorUnavailableColorEnteredReturnsSelectedColor() {
        ConsoleView view = createViewWithInput("RED\n");

        assertEquals(
                PlayerColor.RED,
                view.promptPlayerColor("Bob", List.of(PlayerColor.BLUE)));
    }

    @Test
    public void displayPlayersMinimumRegisteredPlayersDisplaysPlayers() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        List<Player> players = List.of(
                new HumanPlayer("Alice", PlayerColor.RED, STARTING_ARMIES_THREE_PLAYERS),
                new HumanPlayer("Bob", PlayerColor.BLUE, STARTING_ARMIES_THREE_PLAYERS),
                new HumanPlayer("Cara", PlayerColor.GREEN, STARTING_ARMIES_THREE_PLAYERS));

        view.displayPlayers(players);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertTrue(displayedText.contains("Alice"));
        assertTrue(displayedText.contains("RED"));
        assertTrue(displayedText.contains("Bob"));
        assertTrue(displayedText.contains("BLUE"));
        assertTrue(displayedText.contains("Cara"));
        assertTrue(displayedText.contains("GREEN"));
    }

    @Test
    public void displayPlayersMaximumRegisteredPlayersDisplaysPlayers() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        List<Player> players = List.of(
                new HumanPlayer("Alice", PlayerColor.RED, STARTING_ARMIES_SIX_PLAYERS),
                new HumanPlayer("Bob", PlayerColor.BLUE, STARTING_ARMIES_SIX_PLAYERS),
                new HumanPlayer("Cara", PlayerColor.GREEN, STARTING_ARMIES_SIX_PLAYERS),
                new HumanPlayer("Dan", PlayerColor.YELLOW, STARTING_ARMIES_SIX_PLAYERS),
                new HumanPlayer("Eli", PlayerColor.BLACK, STARTING_ARMIES_SIX_PLAYERS),
                new HumanPlayer("Frank", PlayerColor.PURPLE, STARTING_ARMIES_SIX_PLAYERS));

        view.displayPlayers(players);
        String displayedText = output.toString(StandardCharsets.UTF_8);

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

    @Test
    public void displayStartingPlayerSelectedStartingPlayerDisplaysStartingPlayer() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        Player player = new HumanPlayer("Alice", PlayerColor.RED, STARTING_ARMIES_THREE_PLAYERS);

        view.displayStartingPlayer(player);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertTrue(displayedText.contains("Alice"));
        assertTrue(displayedText.contains("RED"));
    }

    private ConsoleView createViewWithInput(final String input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        return new ConsoleView(
                new Scanner(input),
                new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    private ConsoleView createViewWithOutput(final ByteArrayOutputStream output) {
        return new ConsoleView(
                new Scanner(""),
                new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @Test
    public void displayStartingPlayerPrintsStartingPlayer() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(new Scanner(""), new PrintStream(output));

        view.displayStartingPlayer("Player 1 RED");

        assertTrue(output.toString().contains("Player 1 RED"));
    }

    @Test
    public void displayUnclaimedTerritoriesByContinentPrintsTerritories() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(new Scanner(""), new PrintStream(output));
        String unclaimedTerritories = "North America: Alaska, Alberta";

        view.displayUnclaimedTerritoriesByContinent(unclaimedTerritories);

        assertTrue(output.toString().contains(unclaimedTerritories));
    }

    @Test
    public void displayCurrentPlayerClaimingStatusPrintsStatus() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(new Scanner(""), new PrintStream(output));
        String playerClaimingStatus = "Player 1 territories: Alaska";

        view.displayCurrentPlayerClaimingStatus(playerClaimingStatus);

        assertTrue(output.toString().contains(playerClaimingStatus));
    }

}
