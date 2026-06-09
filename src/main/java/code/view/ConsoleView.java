package code.view;

import java.io.PrintStream;
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
}
