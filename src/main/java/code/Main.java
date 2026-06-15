package code;

import code.controller.GameController;

/**
 * Starts the Risk game application.
 */
public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        GameController controller = new GameController();
        controller.startGame();
    }
}
