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

    private static final int TERRITORY_INPUT_INDEX = 0;

    private static final int INFANTRY_INPUT_INDEX = 1;

    private static final int CAVALRY_INPUT_INDEX = 2;

    private static final int ARTILLERY_INPUT_INDEX = 3;

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
    public void displayCurrentPlayerTerritoriesByContinentOneOwnedTerritoryDisplaysTerritoryString() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        String territoriesByContinent = "North America: Alaska";

        view.displayCurrentPlayerTerritoriesByContinent(territoriesByContinent);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertEquals(territoriesByContinent + System.lineSeparator(), displayedText);
    }

    @Test
    public void displayCurrentPlayerTerritoriesByContinentMultipleOwnedTerritoriesDisplaysTerritoryString() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        String territoriesByContinent = "North America: Alaska, Alberta"
                + System.lineSeparator()
                + "Asia: China";

        view.displayCurrentPlayerTerritoriesByContinent(territoriesByContinent);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertEquals(territoriesByContinent + System.lineSeparator(), displayedText);
    }

    @Test
    public void getTerritoryChoiceDuringSetupReturnsEnteredTerritory() {
        ConsoleView view = createViewWithInput("Alaska\n");

        String territoryChoice = view.getTerritoryChoiceDuringSetup();

        assertEquals("Alaska", territoryChoice);
    }

    @Test
    public void promptCurrentPlayerTerritoryChoiceOwnedTerritoryEnteredReturnsTerritoryName() {
        ConsoleView view = createViewWithInput("Alaska\n");

        String territoryChoice = view.promptCurrentPlayerTerritoryChoice();

        assertEquals("Alaska", territoryChoice);
    }

    @Test
    public void promptCurrentPlayerTerritoryChoiceUnownedTerritoryEnteredReturnsTerritoryName() {
        ConsoleView view = createViewWithInput("Alberta\n");

        String territoryChoice = view.promptCurrentPlayerTerritoryChoice();

        assertEquals("Alberta", territoryChoice);
    }

    @Test
    public void displaySetupPhaseCompleteAllArmiesPlacedDisplaysSetupCompleteMessage() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        String setupCompleteMessage = "Setup is complete. The game is starting now.";

        view.displaySetupPhaseComplete();
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertEquals(setupCompleteMessage + System.lineSeparator(), displayedText);
    }

    @Test
    public void displayCurrentPlayerArmiesPrintsAvailableArmies() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        String availableArmies = "{INFANTRY=15, CAVALRY=2, ARTILLERY=3}";

        view.displayCurrentPlayerArmies(availableArmies);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertTrue(displayedText.contains(availableArmies));
    }

    @Test
    public void promptReinforcementReturnsOneInfantryPlacement() {
        ConsoleView view = createViewWithInput("Alaska 1 0 0\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Alaska", reinforcementInput.get(TERRITORY_INPUT_INDEX));
        assertEquals("1", reinforcementInput.get(INFANTRY_INPUT_INDEX));
        assertEquals("0", reinforcementInput.get(CAVALRY_INPUT_INDEX));
        assertEquals("0", reinforcementInput.get(ARTILLERY_INPUT_INDEX));
    }

    @Test
    public void promptReinforcementReturnsMixedArmyPlacement() {
        ConsoleView view = createViewWithInput("Alaska 15 2 3\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Alaska", reinforcementInput.get(TERRITORY_INPUT_INDEX));
        assertEquals("15", reinforcementInput.get(INFANTRY_INPUT_INDEX));
        assertEquals("2", reinforcementInput.get(CAVALRY_INPUT_INDEX));
        assertEquals("3", reinforcementInput.get(ARTILLERY_INPUT_INDEX));
    }

    @Test
    public void promptReinforcementReturnsZeroArmyPlacementForModelValidation() {
        ConsoleView view = createViewWithInput("Alaska 0 0 0\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Alaska", reinforcementInput.get(TERRITORY_INPUT_INDEX));
        assertEquals("0", reinforcementInput.get(INFANTRY_INPUT_INDEX));
        assertEquals("0", reinforcementInput.get(CAVALRY_INPUT_INDEX));
        assertEquals("0", reinforcementInput.get(ARTILLERY_INPUT_INDEX));
    }

    @Test
    public void promptReinforcementReturnsNegativeArmyPlacementForModelValidation() {
        ConsoleView view = createViewWithInput("Alaska -1 0 0\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Alaska", reinforcementInput.get(TERRITORY_INPUT_INDEX));
        assertEquals("-1", reinforcementInput.get(INFANTRY_INPUT_INDEX));
        assertEquals("0", reinforcementInput.get(CAVALRY_INPUT_INDEX));
        assertEquals("0", reinforcementInput.get(ARTILLERY_INPUT_INDEX));
    }

    @Test
    public void promptReinforcementReturnsMultiWordTerritoryPlacement() {
        ConsoleView view = createViewWithInput("Northwest Territory 15 2 3\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertEquals("Northwest Territory", reinforcementInput.get(TERRITORY_INPUT_INDEX));
        assertEquals("15", reinforcementInput.get(INFANTRY_INPUT_INDEX));
        assertEquals("2", reinforcementInput.get(CAVALRY_INPUT_INDEX));
        assertEquals("3", reinforcementInput.get(ARTILLERY_INPUT_INDEX));
    }

    @Test
    public void promptReinforcementRejectsNonNumericCavalryCount() {
        ConsoleView view = createViewWithInput("Alaska 10 two 3\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertTrue(reinforcementInput.isEmpty());
    }

    @Test
    public void promptReinforcementRejectsNonNumericInfantryCount() {
        ConsoleView view = createViewWithInput("Alaska ten 2 3\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertTrue(reinforcementInput.isEmpty());
    }

    @Test
    public void promptReinforcementRejectsNonNumericArtilleryCount() {
        ConsoleView view = createViewWithInput("Northwest Territory 10 2 three\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertTrue(reinforcementInput.isEmpty());
    }

    @Test
    public void displayErrorPrintsErrorMessage() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(captured);

        view.displayError("Invalid selection.");

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("Invalid selection."));
    }

    @Test
    public void promptNumberOfPlayersPrintsPromptTextBeforeReadingInput() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("3\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptNumberOfPlayers();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("number of players"));
    }

    @Test
    public void promptPlayerNamePrintsPromptTextContainingPlayerNumber() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("Alice\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptPlayerName(1);

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("player 1"));
    }

    @Test
    public void promptPlayerColorPrintsPromptTextContainingPlayerName() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("RED\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptPlayerColor("Alice", List.of(PlayerColor.values()));

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("Alice"));
    }

    @Test
    public void getTerritoryChoiceDuringSetupPrintsPromptText() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.getTerritoryChoiceDuringSetup();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("territory"));
    }

    @Test
    public void promptCurrentPlayerTerritoryChoicePrintsPromptText() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptCurrentPlayerTerritoryChoice();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("territory"));
    }

    @Test
    public void promptReinforcementPrintsPromptText() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska 1 0 0\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptReinforcement();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("armies"));
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
}
