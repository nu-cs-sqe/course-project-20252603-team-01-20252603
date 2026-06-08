package code.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the main model for the Risk game.
 */
public class GameModel {

    private static final int NORTH_AMERICA_BONUS = 5;
    private static final int SOUTH_AMERICA_BONUS = 2;
    private static final int EUROPE_BONUS = 5;
    private static final int AFRICA_BONUS = 3;
    private static final int ASIA_BONUS = 7;
    private static final int AUSTRALIA_BONUS = 2;

    private final List<Continent> continents;

    public GameModel() {
        continents = new ArrayList<>();
    }

    public void initializeContinentsAndTerritories() {
        continents.clear();

        createNorthAmerica();
        createSouthAmerica();
        createEurope();
        createAfrica();
        createAsia();
        createAustralia();
    }

    public List<Continent> getContinents() {
        return new ArrayList<>(continents);
    }

    private void createNorthAmerica() {
        Continent continent = new Continent("North America", NORTH_AMERICA_BONUS);

        addTerritory(continent, "Alaska");
        addTerritory(continent, "Northwest Territory");
        addTerritory(continent, "Greenland");
        addTerritory(continent, "Alberta");
        addTerritory(continent, "Ontario");
        addTerritory(continent, "Quebec");
        addTerritory(continent, "Western United States");
        addTerritory(continent, "Eastern United States");
        addTerritory(continent, "Central America");

        continents.add(continent);
    }

    private void createSouthAmerica() {
        Continent continent = new Continent("South America", SOUTH_AMERICA_BONUS);

        addTerritory(continent, "Venezuela");
        addTerritory(continent, "Peru");
        addTerritory(continent, "Brazil");
        addTerritory(continent, "Argentina");

        continents.add(continent);
    }

    private void createEurope() {
        Continent continent = new Continent("Europe", EUROPE_BONUS);

        addTerritory(continent, "Iceland");
        addTerritory(continent, "Scandinavia");
        addTerritory(continent, "Ukraine");
        addTerritory(continent, "Great Britain");
        addTerritory(continent, "Northern Europe");
        addTerritory(continent, "Western Europe");
        addTerritory(continent, "Southern Europe");

        continents.add(continent);
    }

    private void createAfrica() {
        Continent continent = new Continent("Africa", AFRICA_BONUS);

        addTerritory(continent, "North Africa");
        addTerritory(continent, "Egypt");
        addTerritory(continent, "East Africa");
        addTerritory(continent, "Congo");
        addTerritory(continent, "South Africa");
        addTerritory(continent, "Madagascar");

        continents.add(continent);
    }

    private void createAsia() {
        Continent continent = new Continent("Asia", ASIA_BONUS);

        addTerritory(continent, "Ural");
        addTerritory(continent, "Siberia");
        addTerritory(continent, "Yakutsk");
        addTerritory(continent, "Kamchatka");
        addTerritory(continent, "Irkutsk");
        addTerritory(continent, "Mongolia");
        addTerritory(continent, "Japan");
        addTerritory(continent, "Afghanistan");
        addTerritory(continent, "China");
        addTerritory(continent, "Middle East");
        addTerritory(continent, "India");
        addTerritory(continent, "Siam");

        continents.add(continent);
    }

    private void createAustralia() {
        Continent continent = new Continent("Australia", AUSTRALIA_BONUS);

        addTerritory(continent, "Indonesia");
        addTerritory(continent, "New Guinea");
        addTerritory(continent, "Western Australia");
        addTerritory(continent, "Eastern Australia");

        continents.add(continent);
    }

    private void addTerritory(
            final Continent continent,
            final String territoryName) {
        Territory territory = new Territory(
                territoryName,
                continent,
                Collections.emptyList());

        continent.addTerritory(territory);
    }
}