package code.view;

import code.model.Player;
import code.model.PlayerColor;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

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
        output.print("Enter number of players: ");
        return scanner.nextInt();
    }

    public String promptPlayerName(final int playerNumber) {
        output.print("Enter player " + playerNumber + " name: ");
        return scanner.nextLine();
    }

    public PlayerColor promptPlayerColor(
            final String playerName,
            final List<PlayerColor> availableColors) {
        output.print("Enter color for " + playerName + ": ");
        return PlayerColor.valueOf(scanner.nextLine().trim().toUpperCase());
    }

    public void displayPlayers(final List<Player> players) {
        output.println("Registered players:");
        for (Player player : players) {
            output.println(player.getName() + " - " + player.getColor());
        }
    }

    public void displayStartingPlayer(final Player player) {
        output.println("Starting player: " + player.getName() + " - " + player.getColor());
    }

    public void displayError(final String message) {
        output.println(message);
    }

    public void displayStartingPlayer(final String startingPlayer) {
        output.println(startingPlayer);
    }

    public void displayUnclaimedTerritoriesByContinent(final String unclaimedTerritories) {
        output.println(unclaimedTerritories);
    }

    public void displayCurrentPlayerClaimingStatus(final String playerClaimingStatus) {
        output.println(playerClaimingStatus);
    }
}
