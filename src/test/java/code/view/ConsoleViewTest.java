package code.view;

import code.model.PlayerColor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

    private static final int FIRST_CARD_INDEX = 1;

    private static final int SECOND_CARD_INDEX = 2;

    private static final int THIRD_CARD_INDEX = 3;

    private static final int THREE = 3;

    private static final int FOUR = 4;

    private static final int DEFAULT_PLAYER_COUNT = 3;

    private static final int MALFORMED_CARD_INPUT_SENTINEL = Integer.MIN_VALUE;

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
    public void displayCurrentPlayerCardsPrintsCards() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);

        String cards = "1: Infantry, 2: Cavalry, 3: Artillery";

        view.displayCurrentPlayerCards(cards);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        assertTrue(displayedText.contains(cards));
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
    public void promptReinforcementRejectsTooFewTokens() {
        ConsoleView view = createViewWithInput("Alaska 1 0\n");

        List<String> reinforcementInput = view.promptReinforcement();

        assertTrue(reinforcementInput.isEmpty());
    }

    @Test
    public void promptChooseCardsToTradeInReturnsThreeSelectedCardIndices() {
        ConsoleView view = createViewWithInput("1 2 3\n");

        List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

        assertEquals(FIRST_CARD_INDEX, cardIndices.get(0));
        assertEquals(SECOND_CARD_INDEX, cardIndices.get(1));
        assertEquals(THIRD_CARD_INDEX, cardIndices.get(2));
    }

    @Test
    public void promptChooseCardsToTradeInReturnsIndicesInEnteredOrder() {
        ConsoleView view = createViewWithInput("3 1 2\n");

        List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

        assertEquals(THIRD_CARD_INDEX, cardIndices.get(0));
        assertEquals(FIRST_CARD_INDEX, cardIndices.get(1));
        assertEquals(SECOND_CARD_INDEX, cardIndices.get(2));
    }

    @Test
    public void promptChooseCardsToTradeInWithEmptyInputReturnsEmptyList() {
        ConsoleView view = createViewWithInput("\n");

        List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

        assertTrue(cardIndices.isEmpty());
    }

    @Test
    public void promptChooseCardsToTradeInReturnsLowInvalidIndexForValidation() {
        ConsoleView view = createViewWithInput("0 1 2\n");

        List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

        assertEquals(0, cardIndices.get(0));
        assertEquals(FIRST_CARD_INDEX, cardIndices.get(1));
        assertEquals(SECOND_CARD_INDEX, cardIndices.get(2));
    }

    @Test
    public void promptChooseCardsToTradeInReturnsDuplicateIndicesForValidation() {
        ConsoleView view = createViewWithInput("1 1 2\n");

        List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

        assertEquals(FIRST_CARD_INDEX, cardIndices.get(0));
        assertEquals(FIRST_CARD_INDEX, cardIndices.get(1));
        assertEquals(SECOND_CARD_INDEX, cardIndices.get(2));
    }

    @Test
    public void promptChooseCardsToTradeInReturnsFewerThanThreeIndicesForValidation() {
        ConsoleView view = createViewWithInput("1 2\n");

        List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

        assertEquals(2, cardIndices.size());
        assertEquals(FIRST_CARD_INDEX, cardIndices.get(0));
        assertEquals(SECOND_CARD_INDEX, cardIndices.get(1));
    }

    @Test
    public void promptChooseCardsToTradeInReturnsMoreThanThreeIndicesForValidation() {
        ConsoleView view = createViewWithInput("1 2 3 4\n");

        List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

        assertEquals(FOUR, cardIndices.size());
        assertEquals(FIRST_CARD_INDEX, cardIndices.get(0));
        assertEquals(SECOND_CARD_INDEX, cardIndices.get(1));
        assertEquals(THIRD_CARD_INDEX, cardIndices.get(2));
        assertEquals(FOUR, cardIndices.get(THREE));
    }

    @Test
    public void promptChooseCardsToTradeInRejectsNonNumericInput() {
        ConsoleView view = createViewWithInput("1 two 3\n");

        List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

        assertEquals(List.of(MALFORMED_CARD_INPUT_SENTINEL), cardIndices);
    }

    @Test
    public void promptTerritoriesToAttackReturnsSingleWordTerritoryNames() {
        ConsoleView view = createViewWithInput("Alaska\nAlberta\n");

        List<String> territoryChoices = view.promptTerritoriesToAttack();

        assertEquals("Alaska", territoryChoices.get(0));
        assertEquals("Alberta", territoryChoices.get(1));
    }

    @Test
    public void getTerritoryChoiceDuringSetupTrimsInput() {
        ConsoleView view = createViewWithInput("  Alaska  \n");

        String territoryChoice = view.getTerritoryChoiceDuringSetup();

        assertEquals("Alaska", territoryChoice);
    }

    @Test
    public void promptFortifySourceTerritoryTrimsInput() {
        ConsoleView view = createViewWithInput("  Northwest Territory  \n");

        String sourceTerritory = view.promptFortifySourceTerritory();

        assertEquals("Northwest Territory", sourceTerritory);
    }

    @Test
    public void promptPlayerColorPrintsAllAvailableColors() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("RED\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptPlayerColor("Alice", List.of(PlayerColor.RED, PlayerColor.BLUE));

        String output = captured.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("Available colors"));
        assertTrue(output.contains("Red"));
        assertTrue(output.contains("Blue"));
    }

    @Test
    public void promptPlayerColorDoesNotPrintUnavailableColors() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("BLUE\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptPlayerColor("Bob", List.of(PlayerColor.BLUE));

        String output = captured.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("Available colors"));
        assertTrue(output.contains("Blue"));
        assertFalse(output.contains("Red"));
    }

    @Test
    public void promptPlayerColorPrintsLocalizedAvailableColorNames() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("RED\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptPlayerColor("Alice", List.of(PlayerColor.RED, PlayerColor.BLUE));

        String output = captured.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("Available colors"));
        assertTrue(output.contains("Red"));
        assertTrue(output.contains("Blue"));
    }

    @Test
    public void promptPlayerColorDoesNotPrintUnavailableLocalizedColorNames() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("BLUE\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptPlayerColor("Bob", List.of(PlayerColor.BLUE));

        String output = captured.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("Available colors"));
        assertTrue(output.contains("Blue"));
        assertFalse(output.contains("Red"));
        assertFalse(output.contains("Green"));
        assertFalse(output.contains("Yellow"));
        assertFalse(output.contains("Black"));
        assertFalse(output.contains("Purple"));
    }

    @Test
    public void promptPlayerColorReturnsEnumWhenUserTypesEnumName() {
        ConsoleView view = new ConsoleView(
                new Scanner("RED\n"),
                new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));

        PlayerColor selectedColor = view.promptPlayerColor(
                "Alice",
                List.of(PlayerColor.RED, PlayerColor.BLUE));

        assertEquals(PlayerColor.RED, selectedColor);
    }

    @Test
    public void promptPlayerColorInvalidColorReturnsUnassigned() {
        ConsoleView view = new ConsoleView(
                new Scanner("FD\n"),
                new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8));

        PlayerColor selectedColor = view.promptPlayerColor(
                "Alice",
                List.of(PlayerColor.RED, PlayerColor.BLUE));

        assertEquals(PlayerColor.UNASSIGNED, selectedColor);
    }

    @Test
    public void promptTerritoriesToAttackReturnsMultiWordTerritoryNames() {
        ConsoleView view = createViewWithInput(
                "Western United States\nEastern United States\n");

        List<String> territoryChoices = view.promptTerritoriesToAttack();

        assertEquals("Western United States", territoryChoices.get(0));
        assertEquals("Eastern United States", territoryChoices.get(1));
    }

    @Test
    public void promptTerritoriesToAttackBlankAttackingTerritoryReprompts() {
        ConsoleView view = createViewWithInput("\nAlaska\nAlberta\n");

        List<String> territoryChoices = view.promptTerritoriesToAttack();

        assertEquals("Alaska", territoryChoices.get(0));
        assertEquals("Alberta", territoryChoices.get(1));
    }

    @Test
    public void promptTerritoriesToAttackBlankDefendingTerritoryReprompts() {
        ConsoleView view = createViewWithInput("Alaska\n\nAlberta\n");

        List<String> territoryChoices = view.promptTerritoriesToAttack();

        assertEquals("Alaska", territoryChoices.get(0));
        assertEquals("Alberta", territoryChoices.get(1));
    }

    @Test
    public void promptNumberOfDiceReturnsMinimumValidAttackerAndDefenderDiceCounts() {
        ConsoleView view = createViewWithInput("1\n1\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(FIRST_CARD_INDEX, diceCounts.get(0));
        assertEquals(FIRST_CARD_INDEX, diceCounts.get(1));
    }

    @Test
    public void promptNumberOfDiceReturnsIntermediateValidAttackerAndDefenderDiceCounts() {
        ConsoleView view = createViewWithInput("2\n1\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(SECOND_CARD_INDEX, diceCounts.get(0));
        assertEquals(FIRST_CARD_INDEX, diceCounts.get(1));
    }

    @Test
    public void promptNumberOfDiceReturnsMaximumValidAttackerAndDefenderDiceCounts() {
        ConsoleView view = createViewWithInput("3\n2\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(THREE, diceCounts.get(0));
        assertEquals(SECOND_CARD_INDEX, diceCounts.get(1));
    }

    @Test
    public void promptNumberOfDiceReturnsBelowMinimumAttackerDiceCountForValidation() {
        ConsoleView view = createViewWithInput("0\n1\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(0, diceCounts.get(0));
        assertEquals(FIRST_CARD_INDEX, diceCounts.get(1));
    }

    @Test
    public void promptNumberOfDiceReturnsAboveMaximumAttackerDiceCountForValidation() {
        ConsoleView view = createViewWithInput("4\n1\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(FOUR, diceCounts.get(0));
        assertEquals(FIRST_CARD_INDEX, diceCounts.get(1));
    }

    @Test
    public void promptNumberOfDiceReturnsBelowMinimumDefenderDiceCountForValidation() {
        ConsoleView view = createViewWithInput("1\n0\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(FIRST_CARD_INDEX, diceCounts.get(0));
        assertEquals(0, diceCounts.get(1));
    }

    @Test
    public void promptNumberOfDiceReturnsAboveMaximumDefenderDiceCountForValidation() {
        ConsoleView view = createViewWithInput("1\n3\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(FIRST_CARD_INDEX, diceCounts.get(0));
        assertEquals(THREE, diceCounts.get(1));
    }

    @Test
    public void promptNumberOfDiceReturnsMalformedSentinelForNonNumericAttackerInput() {
        ConsoleView view = createViewWithInput("one\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(List.of(MALFORMED_CARD_INPUT_SENTINEL), diceCounts);
    }

    @Test
    public void promptNumberOfDiceReturnsMalformedSentinelForNonNumericDefenderInput() {
        ConsoleView view = createViewWithInput("1\ntwo\n");

        List<Integer> diceCounts = view.promptNumberOfDice("Attacker", "Defender");

        assertEquals(List.of(MALFORMED_CARD_INPUT_SENTINEL), diceCounts);
    }

    @Test
    public void displayBattleResultPrintsBattleResult() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(output);
        List<String> battleResult = List.of(
                "Attacker dice: [6, 5, 2]",
                "Defender dice: [6, 3]",
                "Attacker loses 1 army",
                "Defender loses 1 army",
                "Attacking territory armies: 4",
                "Defending territory armies: 2");

        view.displayBattleResult(battleResult);
        String displayedText = output.toString(StandardCharsets.UTF_8);

        for (String battleResultLine : battleResult) {
            assertTrue(displayedText.contains(battleResultLine));
        }
    }

    @Test
    public void promptFortifyChoiceYesChoiceReturnsChoice() {
        ConsoleView view = createViewWithInput("yes\n");

        String fortifyChoice = view.promptFortifyChoice();

        assertEquals("yes", fortifyChoice);
    }

    @Test
    public void promptFortifyChoiceNoChoiceReturnsChoice() {
        ConsoleView view = createViewWithInput("no\n");

        String fortifyChoice = view.promptFortifyChoice();

        assertEquals("no", fortifyChoice);
    }

    @Test
    public void promptFortifyChoiceInvalidChoiceReturnsChoice() {
        ConsoleView view = createViewWithInput("maybe\n");

        String fortifyChoice = view.promptFortifyChoice();

        assertEquals("maybe", fortifyChoice);
    }

    @Test
    public void promptFortifySourceTerritorySingleWordTerritoryReturnsTerritory() {
        ConsoleView view = createViewWithInput("Alaska\n");

        String sourceTerritory = view.promptFortifySourceTerritory();

        assertEquals("Alaska", sourceTerritory);
    }

    @Test
    public void promptFortifySourceTerritoryMultiWordTerritoryReturnsTerritory() {
        ConsoleView view = createViewWithInput("Northwest Territory\n");

        String sourceTerritory = view.promptFortifySourceTerritory();

        assertEquals("Northwest Territory", sourceTerritory);
    }

    @Test
    public void promptFortifyDestinationTerritorySingleWordTerritoryReturnsTerritory() {
        ConsoleView view = createViewWithInput("Alberta\n");

        String destinationTerritory = view.promptFortifyDestinationTerritory();

        assertEquals("Alberta", destinationTerritory);
    }

    @Test
    public void promptFortifyDestinationTerritoryMultiWordTerritoryReturnsTerritory() {
        ConsoleView view = createViewWithInput("Western United States\n");

        String destinationTerritory = view.promptFortifyDestinationTerritory();

        assertEquals("Western United States", destinationTerritory);
    }

    @Test
    public void promptFortifyArmyCountZeroArmyCountReturnsArmyCount() {
        ConsoleView view = createViewWithInput("0\n");

        String armyCount = view.promptFortifyArmyCount();

        assertEquals("0", armyCount);
    }

    @Test
    public void promptFortifyArmyCountOneArmyCountReturnsArmyCount() {
        ConsoleView view = createViewWithInput("1\n");

        String armyCount = view.promptFortifyArmyCount();

        assertEquals("1", armyCount);
    }

    @Test
    public void promptFortifyArmyCountMultipleArmyCountReturnsArmyCount() {
        ConsoleView view = createViewWithInput("3\n");

        String armyCount = view.promptFortifyArmyCount();

        assertEquals("3", armyCount);
    }

    @Test
    public void promptFortifyArmyCountNegativeArmyCountReturnsArmyCount() {
        ConsoleView view = createViewWithInput("-1\n");

        String armyCount = view.promptFortifyArmyCount();

        assertEquals("-1", armyCount);
    }

    @Test
    public void promptFortifyArmyCountNonNumericArmyCountReturnsArmyCount() {
        ConsoleView view = createViewWithInput("two\n");

        String armyCount = view.promptFortifyArmyCount();

        assertEquals("two", armyCount);
    }

    @Test
    public void promptAttackChoiceYesReturnsYes() {
        ConsoleView view = createViewWithInput("yes\n");

        String attackChoice = view.promptAttackChoice();

        assertEquals("yes", attackChoice);
    }

    @Test
    public void promptAttackChoiceNoReturnsNo() {
        ConsoleView view = createViewWithInput("no\n");

        String attackChoice = view.promptAttackChoice();

        assertEquals("no", attackChoice);
    }

    @Test
    public void promptAttackChoiceInvalidChoiceReturnsEnteredChoice() {
        ConsoleView view = createViewWithInput("maybe\n");

        String attackChoice = view.promptAttackChoice();

        assertEquals("maybe", attackChoice);
    }

    @Test
    public void promptCaptureArmyCountZeroReturnsArmyCount() {
        ConsoleView view = createViewWithInput("0\n");

        String armyCount = view.promptCaptureArmyCount("Alaska", "Alberta");

        assertEquals("0", armyCount);
    }

    @Test
    public void promptCaptureArmyCountOneReturnsArmyCount() {
        ConsoleView view = createViewWithInput("1\n");

        String armyCount = view.promptCaptureArmyCount("Alaska", "Alberta");

        assertEquals("1", armyCount);
    }

    @Test
    public void promptCaptureArmyCountMultipleReturnsArmyCount() {
        ConsoleView view = createViewWithInput("3\n");

        String armyCount = view.promptCaptureArmyCount("Alaska", "Alberta");

        assertEquals("3", armyCount);
    }

    @Test
    public void promptCaptureArmyCountNegativeReturnsArmyCount() {
        ConsoleView view = createViewWithInput("-1\n");

        String armyCount = view.promptCaptureArmyCount("Alaska", "Alberta");

        assertEquals("-1", armyCount);
    }

    @Test
    public void promptCaptureArmyCountNonNumericReturnsArmyCount() {
        ConsoleView view = createViewWithInput("two\n");

        String armyCount = view.promptCaptureArmyCount("Alaska", "Alberta");

        assertEquals("two", armyCount);
    }

    @Test
    public void displayNoValidAttacksPrintsNoValidAttacksMessage() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(captured);

        view.displayNoValidAttacks();

        assertTrue(captured.toString(StandardCharsets.UTF_8)
                .contains("No valid attacks available."));
    }

    @Test
    public void displayTerritoryCapturedWithOneMovedArmyPrintsCaptureDetails() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(captured);

        view.displayTerritoryCaptured("Alaska", "Alberta", 1);

        String displayedText = captured.toString(StandardCharsets.UTF_8);
        assertTrue(displayedText.contains("Alaska"));
        assertTrue(displayedText.contains("Alberta"));
        assertTrue(displayedText.contains("1"));
    }

    @Test
    public void displayTerritoryCapturedWithMultipleMovedArmiesPrintsCaptureDetails() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(captured);

        view.displayTerritoryCaptured("Alaska", "Alberta", THREE);

        String displayedText = captured.toString(StandardCharsets.UTF_8);
        assertTrue(displayedText.contains("Alaska"));
        assertTrue(displayedText.contains("Alberta"));
        assertTrue(displayedText.contains(String.valueOf(THREE)));
    }

    @Test
    public void displayRiskCardAwardedPrintsPlayerNameAndAwardMessage() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = createViewWithOutput(captured);

        view.displayRiskCardAwarded("Player 1");

        String displayedText = captured.toString(StandardCharsets.UTF_8);
        assertTrue(displayedText.contains("Player 1"));
        assertTrue(displayedText.contains("Risk card"));
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

    @Test
    public void promptFortifyChoicePrintsPromptText() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("yes\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptFortifyChoice();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("fortify"));
    }

    @Test
    public void promptFortifySourceTerritoryPrintsPromptText() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("Alaska\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptFortifySourceTerritory();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("source territory"));
    }

    @Test
    public void promptFortifyDestinationTerritoryPrintsPromptText() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("Alberta\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptFortifyDestinationTerritory();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("destination territory"));
    }

    @Test
    public void promptFortifyArmyCountPrintsPromptText() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("1\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptFortifyArmyCount();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("armies to move"));
    }

    @Test
    public void promptChooseCardsToTradeInPrintsPromptText() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("1 2 3\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8));

        view.promptChooseCardsToTradeIn();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("card indices"));
    }

    @Test
    public void promptNumberOfPlayersSpanishLocalePrintsSpanishPrompt() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner("3\n"),
                new PrintStream(captured, true, StandardCharsets.UTF_8),
                new Locale("es"));

        view.promptNumberOfPlayers();

        assertTrue(captured.toString(StandardCharsets.UTF_8).contains("Ingrese el numero de jugadores"));
    }

    @Test
    public void defaultConstructorReadsFromStandardInput() {
        InputStream originalIn = System.in;

        try {
            System.setIn(new ByteArrayInputStream("3\n".getBytes(StandardCharsets.UTF_8)));

            ConsoleView view = new ConsoleView();

            assertEquals(DEFAULT_PLAYER_COUNT, view.promptNumberOfPlayers());
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void promptNumberOfPlayersLocaleConstructorUsesProvidedLocale() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream("3\n".getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));

            ConsoleView view = new ConsoleView(new Locale("es"));

            view.promptNumberOfPlayers();

            assertTrue(captured.toString(StandardCharsets.UTF_8)
                    .contains("Ingrese el numero de jugadores"));
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    @Test
    public void displaySetupPhaseCompleteSpanishLocalePrintsSpanishMessage() {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(
                new Scanner(""),
                new PrintStream(captured, true, StandardCharsets.UTF_8),
                new Locale("es"));

        view.displaySetupPhaseComplete();

        assertTrue(captured.toString(StandardCharsets.UTF_8)
                .contains("La configuracion esta completa. El juego comienza ahora."));
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

    private ConsoleView createViewWithLocaleAndOutput(
            final Locale locale,
            final ByteArrayOutputStream output,
            final String input) {
        return new ConsoleView(
                new Scanner(input),
                new PrintStream(output, true, StandardCharsets.UTF_8),
                locale);
    }
}
