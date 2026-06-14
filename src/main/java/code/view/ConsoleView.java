package code.view;

import code.model.PlayerColor;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.text.MessageFormat;
import java.util.stream.Collectors;

public class ConsoleView {

    private static final int REINFORCEMENT_INPUT_LENGTH = 4;

    private static final int INFANTRY_INDEX_FROM_END = 3;

    private static final int CAVALRY_INDEX_FROM_END = 2;

    private static final int ARTILLERY_INDEX_FROM_END = 1;

    private static final int MALFORMED_CARD_INPUT_SENTINEL = Integer.MIN_VALUE;

    private final Scanner scanner;

    private final PrintStream output;

    private final ResourceBundle messages;

    public ConsoleView() {
        this(new Scanner(System.in, StandardCharsets.UTF_8), System.out, Locale.ENGLISH);
    }

    public ConsoleView(final Locale locale) {
        this(new Scanner(System.in, StandardCharsets.UTF_8), System.out, locale);
    }

    ConsoleView(final Scanner inputScanner, final PrintStream outputStream) {
        this(inputScanner, outputStream, Locale.ENGLISH);
    }

    ConsoleView(
            final Scanner inputScanner,
            final PrintStream outputStream,
            final Locale locale) {
        scanner = inputScanner;
        output = outputStream;
        messages = ResourceBundle.getBundle("messages", locale);
    }

    private String displayColors(final List<PlayerColor> availableColors) {
        return availableColors.stream()
                .filter(color -> color != PlayerColor.UNASSIGNED)
                .map(color -> message("color." + color.name()))
                .collect(Collectors.joining(", "));
    }

    public int promptNumberOfPlayers() {
        output.print(message("prompt.numberOfPlayers"));
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String promptPlayerName(final int playerNumber) {
        output.print(message("prompt.playerName", playerNumber));
        return scanner.nextLine();
    }

    public PlayerColor promptPlayerColor(
            final String playerName,
            final List<PlayerColor> availableColors) {
        output.println(message("prompt.availableColors", displayColors(availableColors)));
        output.print(message("prompt.playerColor", playerName));

        String colorInput = scanner.nextLine().trim().toUpperCase();

        try {
            return PlayerColor.valueOf(colorInput);
        } catch (IllegalArgumentException exception) {
            return PlayerColor.UNASSIGNED;
        }
    }

    public void displayError(final String message) {
        output.println(message);
    }

    public void displayCurrentPlayer(final String currentPlayerName) {
        output.println(currentPlayerName);
    }

    public void displayUnclaimedTerritoriesByContinent(final String unclaimedTerritories) {
        output.println(unclaimedTerritories);
    }

    public void displayCurrentPlayerClaimingStatus(final String playerClaimingStatus) {
        output.println(playerClaimingStatus);
    }

    public void displayCurrentPlayerTerritoriesByContinent(
            final String territoriesByContinent) {
        output.println(territoriesByContinent);
    }

    public String getTerritoryChoiceDuringSetup() {
        output.print(message("prompt.claimTerritory"));

        return scanner.nextLine().trim();
    }

    public String promptCurrentPlayerTerritoryChoice() {
        output.print(message("prompt.placeArmyTerritory"));

        return scanner.nextLine().trim();
    }

    public void displaySetupPhaseComplete() {
        output.println(message("message.setupComplete"));
    }

    public String promptFortifyChoice() {
        output.print(message("prompt.fortifyChoice"));

        return scanner.nextLine().trim();
    }

    public String promptFortifySourceTerritory() {
        output.print(message("prompt.fortifySource"));

        return scanner.nextLine().trim();
    }

    public String promptFortifyDestinationTerritory() {
        output.print(message("prompt.fortifyDestination"));

        return scanner.nextLine().trim();
    }

    public String promptFortifyArmyCount() {
        output.print(message("prompt.fortifyArmyCount"));

        return scanner.nextLine();
    }

    public void displayCurrentPlayerArmies(final String availableArmies) {
        output.println(availableArmies);
    }

    public void displayCurrentPlayerCards(final String cards) {
        output.println(cards);
    }

    public List<Integer> promptChooseCardsToTradeIn() {
        output.print(message("prompt.tradeInCards"));
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return List.of();
        }

        String[] tokens = input.split("\\s+");
        List<Integer> cardIndices = new ArrayList<>();

        for (String token : tokens) {
            if (!isInteger(token)) {
                return List.of(MALFORMED_CARD_INPUT_SENTINEL);
            }

            cardIndices.add(Integer.parseInt(token));
        }

        return cardIndices;
    }

    public List<String> promptReinforcement() {
        output.print(message("prompt.reinforcement"));
        String input = scanner.nextLine().trim();

        String[] tokens = input.split("\\s+");

        if (tokens.length < REINFORCEMENT_INPUT_LENGTH) {
            return List.of();
        }

        int infantryIndex = tokens.length - INFANTRY_INDEX_FROM_END;
        int cavalryIndex = tokens.length - CAVALRY_INDEX_FROM_END;
        int artilleryIndex = tokens.length - ARTILLERY_INDEX_FROM_END;

        if (!isInteger(tokens[infantryIndex])
                || !isInteger(tokens[cavalryIndex])
                || !isInteger(tokens[artilleryIndex])) {
            return List.of();
        }

        String territoryName = String.join(
                " ",
                Arrays.copyOfRange(tokens, 0, infantryIndex));

        return List.of(
                territoryName,
                tokens[infantryIndex],
                tokens[cavalryIndex],
                tokens[artilleryIndex]);
    }

    public String promptAttackChoice() {
        output.print("Do you want to attack? (yes/no): ");

        return scanner.nextLine().trim();
    }

    public List<String> promptTerritoriesToAttack() {
        String attackingTerritory = "";

        while (attackingTerritory.isBlank()) {
            output.print(message("prompt.attackingTerritory"));
            attackingTerritory = scanner.nextLine();
        }

        String defendingTerritory = "";

        while (defendingTerritory.isBlank()) {
            output.print(message("prompt.defendingTerritory"));
            defendingTerritory = scanner.nextLine();
        }

        return List.of(attackingTerritory, defendingTerritory);
    }

    public List<Integer> promptNumberOfDice(
            final String attackerName,
            final String defenderName) {
        output.print(message("prompt.attackerDice", attackerName));
        String attackerDiceInput = scanner.nextLine();

        if (!isInteger(attackerDiceInput)) {
            return List.of(MALFORMED_CARD_INPUT_SENTINEL);
        }

        output.print(message("prompt.defenderDice", defenderName));
        String defenderDiceInput = scanner.nextLine();

        if (!isInteger(defenderDiceInput)) {
            return List.of(MALFORMED_CARD_INPUT_SENTINEL);
        }

        int attackerDice = Integer.parseInt(attackerDiceInput);
        int defenderDice = Integer.parseInt(defenderDiceInput);

        return List.of(attackerDice, defenderDice);
    }

    public String promptCaptureArmyCount(
            final String attackerName,
            final String defenderName) {
        output.print("Enter armies to move from " + attackerName + " to " + defenderName + ": ");

        return scanner.nextLine().trim();
    }

    public void displayBattleResult(final List<String> battleResult) {
        for (String battleResultLine : battleResult) {
            output.println(battleResultLine);
        }
    }

    public void displayNoValidAttacks() {
        output.println("No valid attacks available.");
    }

    public void displayTerritoryCaptured(
            final String attackerName,
            final String defenderName,
            final int movedArmies) {
        output.println(attackerName + " captured " + defenderName
                + " and moved " + movedArmies + " armies.");
    }

    public void displayRiskCardAwarded(final String playerName) {
        output.println(playerName + " received a Risk card.");
    }

    public void displayPlayerElimination(final String playerName) {
        output.println(playerName + " has been eliminated.");
    }

    private boolean isInteger(final String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private String message(final String key, final Object... arguments) {
        return MessageFormat.format(messages.getString(key), arguments);
    }

}
