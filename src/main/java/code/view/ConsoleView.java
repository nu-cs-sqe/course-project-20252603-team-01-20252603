package code.view;

import code.model.PlayerColor;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private static final int REINFORCEMENT_INPUT_LENGTH = 4;

    private static final int INFANTRY_INDEX_FROM_END = 3;

    private static final int CAVALRY_INDEX_FROM_END = 2;

    private static final int ARTILLERY_INDEX_FROM_END = 1;

    private static final int MALFORMED_CARD_INPUT_SENTINEL = Integer.MIN_VALUE;

    private final Scanner scanner;

    private final PrintStream output;

    public ConsoleView() {
        this(new Scanner(System.in, StandardCharsets.UTF_8), System.out);
    }

    ConsoleView(final Scanner inputScanner, final PrintStream outputStream) {
        scanner = inputScanner;
        output = outputStream;
    }

    public int promptNumberOfPlayers() {
        output.println("Enter number of players: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public String promptPlayerName(final int playerNumber) {
        output.println("Enter player " + playerNumber + " name: ");
        return scanner.nextLine();
    }

    public PlayerColor promptPlayerColor(
            final String playerName,
            final List<PlayerColor> availableColors) {
        output.println("Available colors: " + availableColors);
        output.print("Enter color for " + playerName + ": ");
        return PlayerColor.valueOf(scanner.nextLine().trim().toUpperCase());
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
        output.print("Enter territory to claim: ");

        return scanner.nextLine();
    }

    public String promptCurrentPlayerTerritoryChoice() {
        output.print("Enter territory to place army: ");

        return scanner.nextLine();
    }

    public void displaySetupPhaseComplete() {
        output.println("Setup is complete. The game is starting now.");
    }

    public String promptFortifyChoice() {
        output.print("Do you want to fortify? (yes/no): ");

        return scanner.nextLine().trim();
    }

    public String promptFortifySourceTerritory() {
        output.print("Enter source territory: ");

        return scanner.nextLine();
    }

    public String promptFortifyDestinationTerritory() {
        output.print("Enter destination territory: ");

        return scanner.nextLine();
    }

    public String promptFortifyArmyCount() {
        output.print("Enter number of armies to move: ");

        return scanner.nextLine();
    }

    public void displayCurrentPlayerArmies(final String availableArmies) {
        output.println(availableArmies);
    }

    public void displayCurrentPlayerCards(final String cards) {
        output.println(cards);
    }

    public List<Integer> promptChooseCardsToTradeIn() {
        output.print("Enter card indices to trade in: ");
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
        output.print("Enter territory and armies to place: ");
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

    public List<String> promptTerritoriesToAttack() {
        String attackingTerritory = "";

        while (attackingTerritory.isBlank()) {
            output.print("Enter attacking territory: ");
            attackingTerritory = scanner.nextLine();
        }

        String defendingTerritory = "";

        while (defendingTerritory.isBlank()) {
            output.print("Enter defending territory: ");
            defendingTerritory = scanner.nextLine();
        }

        return List.of(attackingTerritory, defendingTerritory);
    }

    public List<Integer> promptNumberOfDice(
            final String attackerName,
            final String defenderName) {
        output.print("Enter number of dice for " + attackerName + ": ");
        String attackerDiceInput = scanner.nextLine();

        if (!isInteger(attackerDiceInput)) {
            return List.of(MALFORMED_CARD_INPUT_SENTINEL);
        }

        output.print("Enter number of dice for " + defenderName + ": ");
        String defenderDiceInput = scanner.nextLine();

        if (!isInteger(defenderDiceInput)) {
            return List.of(MALFORMED_CARD_INPUT_SENTINEL);
        }

        int attackerDice = Integer.parseInt(attackerDiceInput);
        int defenderDice = Integer.parseInt(defenderDiceInput);

        return List.of(attackerDice, defenderDice);
    }

    public void displayBattleResult(final List<String> battleResult) {
        for (String battleResultLine : battleResult) {
            output.println(battleResultLine);
        }
    }

    private boolean isInteger(final String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

}
