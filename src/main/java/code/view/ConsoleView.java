package code.view;

import code.model.PlayerColor;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner;

    private final PrintStream output;

    public ConsoleView() {
        this(new Scanner(System.in), System.out);
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
}
