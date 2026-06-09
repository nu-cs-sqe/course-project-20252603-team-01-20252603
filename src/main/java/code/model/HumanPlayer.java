package code.model;

/**
 * Represents a human player in the Risk game.
 */
public final class HumanPlayer extends Player {

    public HumanPlayer(
            final String playerName,
            final PlayerColor playerColor,
            final int startingInfantry) {
        super(playerName, playerColor, startingInfantry);
    }
}
