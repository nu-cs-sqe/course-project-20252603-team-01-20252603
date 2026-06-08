package code.model;

/**
 * Represents a continent in the Risk game.
 */
public final class Continent {

    private static final int MIN_BONUS_ARMIES = 2;
    private static final int MAX_BONUS_ARMIES = 7;

    private final String name;
    private final int bonusArmies;

    public Continent(final String continentName, final int continentBonusArmies) {
        validateBonusArmies(continentBonusArmies);

        name = continentName;
        bonusArmies = continentBonusArmies;
    }

    public String getName() {
        return name;
    }

    public int getBonusArmies() {
        return bonusArmies;
    }

    private void validateBonusArmies(final int continentBonusArmies) {
        if (continentBonusArmies < MIN_BONUS_ARMIES
                || continentBonusArmies > MAX_BONUS_ARMIES) {
            throw new IllegalArgumentException("Invalid continent bonus armies.");
        }
    }

}