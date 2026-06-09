package code.model;

import java.util.*;
import java.util.stream.Collectors;

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

    private static final int MIN_PLAYER_COUNT = 3;

    private static final int FOUR_PLAYER_COUNT = 4;

    private static final int FIVE_PLAYER_COUNT = 5;

    private static final int MAX_PLAYER_COUNT = 6;

    private static final int THREE_PLAYER_STARTING_INFANTRY = 35;

    private static final int FOUR_PLAYER_STARTING_INFANTRY = 30;

    private static final int FIVE_PLAYER_STARTING_INFANTRY = 25;

    private static final int SIX_PLAYER_STARTING_INFANTRY = 20;

    private static final int SETUP_INFANTRY_COUNT = 1;

    private final List<Continent> continents;

    private final List<Player> players;

    private static final int TOTAL_TERRITORY_COUNT = 42;

    private int playerCount;

    private Deck deck;

    private int currentPlayerIndex;

    private final List<Territory> territories;

    public GameModel() {
        continents = new ArrayList<>();
        territories = new ArrayList<>();
        players = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
    }

    public void initializeContinentsAndTerritories() {
        continents.clear();
        territories.clear();

        createNorthAmerica();
        createSouthAmerica();
        createEurope();
        createAfrica();
        createAsia();
        createAustralia();
        initializeAdjacencies();
        initializeDeck();
    }

    public int getDeckSize() {
        return deck.size();
    }

    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }

    public boolean setPlayerCount(final int count) {
        if (count < MIN_PLAYER_COUNT || count > MAX_PLAYER_COUNT) {
            return false;
        }

        playerCount = count;
        return true;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public Player addPlayer(final String name, final PlayerColor color) {
        if (players.size() >= playerCount || isColorAlreadyChosen(color)) {
            return new NullPlayer();
        }

        Player player = new HumanPlayer(name, color, calculateStartingInfantry());

        players.add(player);
        return player;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public List<PlayerColor> showAvailableColors() {
        return Arrays.stream(PlayerColor.values())
                .filter(color -> color != PlayerColor.UNASSIGNED)
                .filter(color -> !isColorAlreadyChosen(color))
                .collect(Collectors.toList());
    }

    public void setCurrentPlayerIndex(final int index) {
        if (index < 0 || index >= players.size()) {
            return;
        }

        currentPlayerIndex = index;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    private boolean isColorAlreadyChosen(final PlayerColor color) {
        return players.stream()
                .anyMatch(player -> player.getColor() == color);
    }

    private int calculateStartingInfantry() {
        if (playerCount == FOUR_PLAYER_COUNT) {
            return FOUR_PLAYER_STARTING_INFANTRY;
        }

        if (playerCount == FIVE_PLAYER_COUNT) {
            return FIVE_PLAYER_STARTING_INFANTRY;
        }

        if (playerCount == MAX_PLAYER_COUNT) {
            return SIX_PLAYER_STARTING_INFANTRY;
        }

        return THREE_PLAYER_STARTING_INFANTRY;
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
        territories.add(territory);
    }

    private Territory findTerritoryByName(final String territoryName) {
        return territories.stream()
                .filter(territory -> territory.getName().equals(territoryName))
                .findFirst()
                .get();
    }

    private void connect(
            final String firstTerritoryName,
            final String secondTerritoryName) {
        Territory firstTerritory = findTerritoryByName(firstTerritoryName);
        Territory secondTerritory = findTerritoryByName(secondTerritoryName);

        firstTerritory.addAdjacentTerritory(secondTerritory);
        secondTerritory.addAdjacentTerritory(firstTerritory);
    }

    private void initializeAdjacencies() {
        connect("Alaska", "Northwest Territory");
        connect("Alaska", "Alberta");
        connect("Alaska", "Kamchatka");
        connect("Northwest Territory", "Alberta");
        connect("Northwest Territory", "Ontario");
        connect("Northwest Territory", "Greenland");
        connect("Greenland", "Ontario");
        connect("Greenland", "Quebec");
        connect("Greenland", "Iceland");
        connect("Alberta", "Ontario");
        connect("Alberta", "Western United States");
        connect("Ontario", "Quebec");
        connect("Ontario", "Western United States");
        connect("Ontario", "Eastern United States");
        connect("Quebec", "Eastern United States");
        connect("Western United States", "Eastern United States");
        connect("Western United States", "Central America");
        connect("Eastern United States", "Central America");
        connect("Central America", "Venezuela");

        connect("Venezuela", "Peru");
        connect("Venezuela", "Brazil");
        connect("Peru", "Brazil");
        connect("Peru", "Argentina");
        connect("Brazil", "Argentina");
        connect("Brazil", "North Africa");

        connect("Iceland", "Scandinavia");
        connect("Iceland", "Great Britain");
        connect("Scandinavia", "Ukraine");
        connect("Scandinavia", "Great Britain");
        connect("Great Britain", "Northern Europe");
        connect("Great Britain", "Western Europe");
        connect("Northern Europe", "Western Europe");
        connect("Northern Europe", "Southern Europe");
        connect("Northern Europe", "Ukraine");
        connect("Western Europe", "Southern Europe");
        connect("Western Europe", "North Africa");
        connect("Southern Europe", "North Africa");
        connect("Southern Europe", "Egypt");
        connect("Southern Europe", "Middle East");
        connect("Southern Europe", "Ukraine");
        connect("Ukraine", "Ural");
        connect("Ukraine", "Afghanistan");
        connect("Ukraine", "Middle East");

        connect("North Africa", "Egypt");
        connect("North Africa", "East Africa");
        connect("North Africa", "Congo");
        connect("Egypt", "East Africa");
        connect("Egypt", "Middle East");
        connect("East Africa", "Middle East");
        connect("East Africa", "Congo");
        connect("East Africa", "South Africa");
        connect("East Africa", "Madagascar");
        connect("Congo", "South Africa");
        connect("South Africa", "Madagascar");

        connect("Ural", "Siberia");
        connect("Ural", "China");
        connect("Ural", "Afghanistan");
        connect("Siberia", "Yakutsk");
        connect("Siberia", "Irkutsk");
        connect("Siberia", "Mongolia");
        connect("Siberia", "China");
        connect("Yakutsk", "Kamchatka");
        connect("Yakutsk", "Irkutsk");
        connect("Kamchatka", "Irkutsk");
        connect("Kamchatka", "Mongolia");
        connect("Kamchatka", "Japan");
        connect("Irkutsk", "Mongolia");
        connect("Mongolia", "Japan");
        connect("Mongolia", "China");
        connect("Afghanistan", "China");
        connect("Afghanistan", "Middle East");
        connect("Afghanistan", "India");
        connect("China", "India");
        connect("China", "Siam");
        connect("Middle East", "India");
        connect("India", "Siam");
        connect("Siam", "Indonesia");

        connect("Indonesia", "New Guinea");
        connect("Indonesia", "Western Australia");
        connect("New Guinea", "Western Australia");
        connect("New Guinea", "Eastern Australia");
        connect("Western Australia", "Eastern Australia");
    }

    private void initializeDeck() {
        deck = new Deck();
        deck.shuffle();
    }

    public boolean claimTerritoryDuringSetup(
            final String territoryName,
            final HashMap<ArmyType, Integer> pieces) {
        Player player = players.get(currentPlayerIndex);
        Territory territory = findTerritoryByName(territoryName);

        if (!territory.isUnclaimed()) {
            return false;
        }

        if (!isExactlyOneInfantry(pieces)) {
            return false;
        }

        if (!player.hasAvailableArmies(pieces)) {
            return false;
        }

        territory.setOwner(player);
        territory.placeArmies(pieces);
        player.addTerritory(territory);
        player.removeArmies(pieces);

        return true;
    }

    private boolean isExactlyOneInfantry(final HashMap<ArmyType, Integer> pieces) {
        return pieces.size() == SETUP_INFANTRY_COUNT
                && pieces.getOrDefault(ArmyType.INFANTRY, 0) == SETUP_INFANTRY_COUNT;
    }

    public boolean advanceCurrentPlayerIndex() {
        if (players.isEmpty()) {
            return false;
        }

        currentPlayerIndex++;

        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
        }

        return true;
    }
}
