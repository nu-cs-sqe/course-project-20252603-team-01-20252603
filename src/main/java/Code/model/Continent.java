package code.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a continent in the Risk game.
 */
public class Continent {

    private static final int MIN_BONUS_ARMIES = 2;
    private static final int MAX_BONUS_ARMIES = 7;

    private final String name;
    private final int bonusArmies;
    private final List<Territory> territories;

    public Continent(final String continentName, final int continentBonusArmies) {
        validateName(continentName);
        validateBonusArmies(continentBonusArmies);

        name = continentName;
        bonusArmies = continentBonusArmies;
        territories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getBonusArmies() {
        return bonusArmies;
    }

    public void addTerritory(final Territory territory) {
        if (territory != null) {
            territories.add(territory);
        }
    }

    public boolean containsTerritory(final Territory territory) {
        return territory != null && territories.contains(territory);
    }

    private void validateName(final String continentName) {
        if (continentName == null || continentName.isEmpty()) {
            throw new IllegalArgumentException("Continent name cannot be empty.");
        }
    }

    private void validateBonusArmies(final int continentBonusArmies) {
        if (continentBonusArmies < MIN_BONUS_ARMIES
                || continentBonusArmies > MAX_BONUS_ARMIES) {
            throw new IllegalArgumentException("Invalid continent bonus armies.");
        }
    }
}