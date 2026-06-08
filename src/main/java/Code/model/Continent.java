package code.model;

/**
 * Represents a continent in the Risk game.
 */
public final class Continent {

    private final String name;
    private final int bonusArmies;

    public Continent(final String continentName, final int continentBonusArmies) {
        name = continentName;
        bonusArmies = continentBonusArmies;
    }

    public String getName() {
        return name;
    }

    public int getBonusArmies() {
        return bonusArmies;
    }
}