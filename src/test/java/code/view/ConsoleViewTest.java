package code.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void displayCurrentPlayerPrintsCurrentPlayer() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);

        view.displayCurrentPlayer("Player 1");
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertTrue(displayedText.contains("Player 1"));
    }

    @Test
    public void displayUnclaimedTerritoriesByContinentPrintsTerritories() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        String unclaimedTerritories = "North America: Alaska, Alberta";

        view.displayUnclaimedTerritoriesByContinent(unclaimedTerritories);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertTrue(displayedText.contains(unclaimedTerritories));
    }

    @Test
    public void displayCurrentPlayerClaimingStatusPrintsStatus() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        String playerClaimingStatus = "Player 1 territories: Alaska";

        view.displayCurrentPlayerClaimingStatus(playerClaimingStatus);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertTrue(displayedText.contains(playerClaimingStatus));
    }

    @Test
    public void getTerritoryChoiceDuringSetupReturnsEnteredTerritory() {
        ConsoleView view = createViewWithInput("Alaska\n");

        String territoryChoice = view.getTerritoryChoiceDuringSetup();

        assertEquals("Alaska", territoryChoice);
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
    public void displayCurrentPlayerArmiesPrintsAvailableArmies() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner(""),
                new PrintStream(output));

        String availableArmies = "{INFANTRY=15, CAVALRY=2, ARTILLERY=3}";

        view.displayCurrentPlayerArmies(availableArmies);

        assertTrue(output.toString().contains(availableArmies));
    }

    @Test
    public void promptReinforcementReturnsOneInfantryPlacement() {
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska 1 0 0\n"),
                new PrintStream(new ByteArrayOutputStream()));

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Alaska", reinforcementInput.get(0));
        assertEquals("1", reinforcementInput.get(1));
        assertEquals("0", reinforcementInput.get(2));
        assertEquals("0", reinforcementInput.get(3));
    }

    @Test
    public void promptReinforcementReturnsMixedArmyPlacement() {
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska 15 2 3\n"),
                new PrintStream(new ByteArrayOutputStream()));

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Alaska", reinforcementInput.get(0));
        assertEquals("15", reinforcementInput.get(1));
        assertEquals("2", reinforcementInput.get(2));
        assertEquals("3", reinforcementInput.get(3));
    }

    @Test
    public void promptReinforcementReturnsZeroArmyPlacementForModelValidation() {
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska 0 0 0\n"),
                new PrintStream(new ByteArrayOutputStream()));

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Alaska", reinforcementInput.get(0));
        assertEquals("0", reinforcementInput.get(1));
        assertEquals("0", reinforcementInput.get(2));
        assertEquals("0", reinforcementInput.get(3));
    }

    @Test
    public void promptReinforcementReturnsNegativeArmyPlacementForModelValidation() {
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska -1 0 0\n"),
                new PrintStream(new ByteArrayOutputStream()));

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Alaska", reinforcementInput.get(0));
        assertEquals("-1", reinforcementInput.get(1));
        assertEquals("0", reinforcementInput.get(2));
        assertEquals("0", reinforcementInput.get(3));
    }

    @Test
    public void promptReinforcementReturnsMultiWordTerritoryPlacement() {
        ConsoleView view = new ConsoleView(
                new Scanner("Northwest Territory 15 2 3\n"),
                new PrintStream(new ByteArrayOutputStream()));

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Northwest Territory", reinforcementInput.get(0));
        assertEquals("15", reinforcementInput.get(1));
        assertEquals("2", reinforcementInput.get(2));
        assertEquals("3", reinforcementInput.get(3));
    }

    @Test
    public void promptReinforcementRejectsNonNumericCavalryCount() {
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska 10 two 3\n"),
                new PrintStream(new ByteArrayOutputStream()));

        List<String> reinforcementInput = view.promptReinforcement();

        assertTrue(reinforcementInput.isEmpty());
    }

    @Test
    public void promptReinforcementRejectsNonNumericInfantryCount() {
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska ten 2 3\n"),
                new PrintStream(new ByteArrayOutputStream()));

        List<String> reinforcementInput = view.promptReinforcement();

        assertTrue(reinforcementInput.isEmpty());
    }

    @Test
    public void promptReinforcementRejectsNonNumericArtilleryCount() {
        ConsoleView view = new ConsoleView(
                new Scanner("Northwest Territory 10 2 three\n"),
                new PrintStream(new ByteArrayOutputStream()));

        List<String> reinforcementInput = view.promptReinforcement();

        assertTrue(reinforcementInput.isEmpty());
    }


}
