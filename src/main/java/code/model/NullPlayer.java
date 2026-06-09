package code.model;

/**
 * Represents an unassigned player placeholder.
 */
public final class NullPlayer extends Player {

    public NullPlayer() {
        super("", PlayerColor.UNASSIGNED, 0);
    }
}
