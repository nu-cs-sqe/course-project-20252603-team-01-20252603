package code.model;

/**
 * Represents a continent in the Risk game.
 */
public final class Continent {

    private static final int MIN_BONUS_ARMIES = 2;

    private final String name;
    private final int bonusArmies;

    public Continent(final String continentName, final int continentBonusArmies) {
        validateMinimumBonusArmies(continentBonusArmies);

        name = continentName;
        bonusArmies = continentBonusArmies;
    }

    public String getName() {
        return name;
    }

    public int getBonusArmies() {
        return bonusArmies;
    }

    private void validateMinimumBonusArmies(final int continentBonusArmies) {
        if (continentBonusArmies < MIN_BONUS_ARMIES) {
            throw new IllegalArgumentException("Bonus armies are below minimum.");
        }
    }
}