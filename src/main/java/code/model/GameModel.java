package code.model;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    private static final int ZERO_ARMIES = 0;

    private static final int REQUIRED_TRADE_IN_CARD_COUNT = 5;

    private static final int MAX_ATTACKER_DICE = 3;

    private static final int DIE_SIDE_COUNT = 6;

    private final List<Continent> continents;

    private final List<Player> players;

    private static final int TOTAL_TERRITORY_COUNT = 42;

    private int playerCount;

    private Deck deck;

    private int currentPlayerIndex;

    private int numSetsTradedIn;

    private final List<Territory> territories;

    private final Random random;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "GameModel intentionally stores the random generator to enable unit testing."
    )
    public GameModel(final Random randomGenerator) {
        continents = new ArrayList<>();
        territories = new ArrayList<>();
        players = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
        numSetsTradedIn = 0;
        random = randomGenerator;
    }

    public GameModel() {
        continents = new ArrayList<>();
        territories = new ArrayList<>();
        players = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
        numSetsTradedIn = 0;
        random = new Random(0);
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
    }

    public int getDeckSize() {
        return deck.size();
    }

    public boolean isDeckEmpty() {
        return deck.isEmpty();
    }

    int getDeckDiscardPileSize() {
        return deck.getDiscardPileSize();
    }

    public boolean setPlayerCount(final int count) {
        if (count < MIN_PLAYER_COUNT || count > MAX_PLAYER_COUNT) {
            return false;
        }

        playerCount = count;
        return true;
    }

    public Player addPlayer(final String name, final PlayerColor color) {
        if (players.size() >= playerCount) {
            return new NullPlayer();
        }

        Player player = new HumanPlayer(name, color, calculateStartingInfantry());

        players.add(player);
        return player;
    }

    public void setCurrentPlayerIndex(final int index) {
        if (index < 0 || index >= players.size()) {
            return;
        }

        currentPlayerIndex = index;
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

    Territory findTerritoryByName(final String territoryName) {
        return territories.stream()
                .filter(territory -> territory.getName().equals(territoryName))
                .findFirst()
                .get();
    }

    private Territory findTerritoryOrThrow(
            final String territoryName,
            final String errorMessage) {
        return territories.stream()
                .filter(territory -> territory.getName().equals(territoryName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(errorMessage));
    }

    private Player findPlayerByName(final String playerName) {
        return players.stream()
                .filter(player -> player.getName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player must exist."));
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

    public boolean addArmiesDuringSetup(
            final String territoryName,
            final HashMap<ArmyType, Integer> pieces) {
        Player player = players.get(currentPlayerIndex);

        for (Territory territory : territories) {
            if (!territory.getName().equals(territoryName)) {
                continue;
            }

        if (!territory.isOwnedBy(player)) {
            return false;
        }

        if (!isExactlyOneInfantry(pieces)) {
            return false;
        }

        if (!player.hasAvailableArmies(pieces)) {
            return false;
        }

        territory.placeArmies(pieces);
        player.removeArmies(pieces);

        return true;
        }

        return false;
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

    public boolean areAllTerritoriesClaimed() {
        return territories.size() == TOTAL_TERRITORY_COUNT
                && territories.stream().allMatch(territory -> !territory.isUnclaimed());
    }

    public String getCurrentPlayerName() {
        return players.get(currentPlayerIndex).getName();
    }

    public boolean hasCurrentPlayerAvailableArmies() {
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, 1);

        return players.get(currentPlayerIndex).hasAvailableArmies(requiredArmies);
    }

    public String getUnclaimedTerritoriesByContinent() {
        StringBuilder territoriesByContinent = new StringBuilder();

        for (Continent continent : continents) {
            territoriesByContinent.append(continent.getName()).append(": ");

            for (Territory territory : territories) {
                if (territory.getContinentName().equals(continent.getName())
                        && territory.isUnclaimed()) {
                    territoriesByContinent.append(territory.getName()).append(", ");
                }
            }

            territoriesByContinent.append(System.lineSeparator());
        }

        return territoriesByContinent.toString();
    }

    public String getCurrentPlayerTerritoriesByContinent() {
        Player player = players.get(currentPlayerIndex);
        StringBuilder territoriesByContinent = new StringBuilder();

        territoriesByContinent.append(player.getName()).append(" territories:");
        territoriesByContinent.append(System.lineSeparator());

        for (Continent continent : continents) {
            territoriesByContinent.append(continent.getName()).append(": ");

            for (Territory territory : territories) {
                if (territory.getContinentName().equals(continent.getName())
                        && territory.isOwnedBy(player)) {
                    territoriesByContinent.append(territory.getName()).append(", ");
                }
            }

            territoriesByContinent.append(System.lineSeparator());
        }

        return territoriesByContinent.toString();
    }

    public String getCurrentPlayerAvailableArmies() {
        return players.get(currentPlayerIndex).getAvailableArmies();
    }

    public String getCurrentPlayerCards() {
        HumanPlayer player = (HumanPlayer) players.get(currentPlayerIndex);
        StringBuilder cards = new StringBuilder();
        List<RiskCard> availableCards = player.getAvailableCards();

        for (int index = 0; index < availableCards.size(); index++) {
            if (index > 0) {
                cards.append(", ");
            }

            cards.append(index + 1)
                    .append(": ")
                    .append(availableCards.get(index).getType());
        }

        return cards.toString();
    }

    public boolean placeArmiesDuringReinforcement(
            final String territoryName,
            final HashMap<ArmyType, Integer> pieces) {
        Territory territory = findTerritoryByName(territoryName);
        Player player = players.get(currentPlayerIndex);

        if (!territory.isOwnedBy(player)) {
            return false;
        }

        if (!hasValidArmyCounts(pieces)) {
            return false;
        }

        if (!player.hasAvailableArmies(pieces)) {
            return false;
        }

        territory.placeArmies(pieces);
        player.removeArmies(pieces);

        return true;
    }

    public boolean fortifyTerritory(
            final String sourceName,
            final String destinationName,
            final int armyCount) {
        Territory sourceTerritory = findTerritoryByName(sourceName);
        Territory destinationTerritory = findTerritoryByName(destinationName);
        Player currentPlayer = players.get(currentPlayerIndex);

        if (!sourceTerritory.isOwnedBy(currentPlayer)
                || !destinationTerritory.isOwnedBy(currentPlayer)) {
            return false;
        }

        if (sourceTerritory.equals(destinationTerritory)) {
            return false;
        }

        if (armyCount < SETUP_INFANTRY_COUNT) {
            return false;
        }

        if (armyCount >= sourceTerritory.getArmyCount()) {
            return false;
        }

        if (!hasOwnedPath(sourceTerritory, destinationTerritory, currentPlayer)) {
            return false;
        }

        HashMap<ArmyType, Integer> piecesToMove = createInfantryPieces(armyCount);

        if (!sourceTerritory.removeArmies(piecesToMove)) {
            return false;
        }

        destinationTerritory.addArmies(piecesToMove);
        return true;
    }

    boolean hasOwnedPath(
            final Territory sourceTerritory,
            final Territory destinationTerritory,
            final Player currentPlayer) {
        List<Territory> territoriesToVisit = new ArrayList<>();
        Set<Territory> visitedTerritories = new HashSet<>();
        territoriesToVisit.add(sourceTerritory);

        while (!territoriesToVisit.isEmpty()) {
            Territory currentTerritory = territoriesToVisit.remove(0);
            if (currentTerritory.equals(destinationTerritory)) {
                return true;
            }

            if (!visitedTerritories.add(currentTerritory)) {
                continue;
            }

            for (Territory adjacentTerritory : currentTerritory.getAdjacentTerritories()) {
                if (adjacentTerritory.isOwnedBy(currentPlayer)) {
                    territoriesToVisit.add(adjacentTerritory);
                }
            }
        }

        return false;
    }

    private boolean hasValidArmyCounts(final HashMap<ArmyType, Integer> pieces) {
        boolean hasPositiveCount = false;

        for (int count : pieces.values()) {
            if (count < 0) {
                return false;
            }

            if (count > 0) {
                hasPositiveCount = true;
            }
        }

        return hasPositiveCount;
    }

    private HashMap<ArmyType, Integer> createArmyPieces(
            final int infantry,
            final int cavalry,
            final int artillery) {
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, infantry);
        pieces.put(ArmyType.CAVALRY, cavalry);
        pieces.put(ArmyType.ARTILLERY, artillery);
        return pieces;
    }

    private HashMap<ArmyType, Integer> createInfantryPieces(final int infantryCount) {
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, infantryCount);
        return pieces;
    }

    public boolean currentPlayerHasAvailableArmies() {
        Player player = players.get(currentPlayerIndex);
        HashMap<ArmyType, Integer> oneInfantry = createArmyPieces(
                1,
                0,
                0);

        return player.hasAvailableArmies(oneInfantry);
    }

    public String validateTerritoriesForAttackAndReturnDefenderName(
            final String attackerTerritoryName,
            final String defenderTerritoryName) {
        Territory attackingTerritory = findTerritoryOrThrow(
                attackerTerritoryName,
                "Attacking territory must exist on the board.");
        Territory defendingTerritory = findTerritoryOrThrow(
                defenderTerritoryName,
                "Defending territory must exist on the board.");
        Player currentPlayer = players.get(currentPlayerIndex);

        if (attackingTerritory.equals(defendingTerritory)) {
            throw new IllegalArgumentException(
                    "Attacking and defending territories must be different territories.");
        }

        if (!attackingTerritory.isOwnedBy(currentPlayer)) {
            throw new IllegalArgumentException(
                    "Current player must own the attacking territory.");
        }

        if (defendingTerritory.isOwnedBy(currentPlayer)) {
            throw new IllegalArgumentException(
                    "Defending territory must be owned by another player.");
        }

        if (!attackingTerritory.getAdjacentTerritories().contains(defendingTerritory)) {
            throw new IllegalArgumentException(
                    "Attacking and defending territories must be adjacent.");
        }

        if (attackingTerritory.getArmyCount() < 2) {
            throw new IllegalArgumentException(
                    "Attacking territory must have at least 2 armies.");
        }

        return defendingTerritory.getName();
    }

    public boolean validateNumberOfDice(
            final String attackerTerritoryName,
            final String defenderTerritoryName,
            final int attackerNumDice,
            final int defenderNumDice) {
        Territory attackingTerritory = findTerritoryOrThrow(
                attackerTerritoryName,
                "Attacking territory must exist on the board.");
        Territory defendingTerritory = findTerritoryOrThrow(
                defenderTerritoryName,
                "Defending territory must exist on the board.");

        if (attackerNumDice < 1 || attackerNumDice > MAX_ATTACKER_DICE) {
            throw new IllegalArgumentException("Attacker must roll between 1 and 3 dice.");
        }

        if (attackerNumDice > attackingTerritory.getArmyCount() - 1) {
            throw new IllegalArgumentException(
                    "Attacker cannot roll more dice than attacking territory armies minus one.");
        }

        if (defenderNumDice < 1 || defenderNumDice > 2) {
            throw new IllegalArgumentException("Defender must roll either 1 or 2 dice.");
        }

        if (defenderNumDice > defendingTerritory.getArmyCount()) {
            throw new IllegalArgumentException(
                    "Defender cannot roll more dice than the number of armies on the defending territory.");
        }

        return true;
    }

    public List<String> executeBattleAndReturnWinner(
            final String attackerTerritoryName,
            final String defenderTerritoryName,
            final int attackerNumDice,
            final int defenderNumDice) {
        Territory attackingTerritory = findTerritoryOrThrow(
                attackerTerritoryName,
                "Attacking territory must exist on the board.");
        Territory defendingTerritory = findTerritoryOrThrow(
                defenderTerritoryName,
                "Defending territory must exist on the board.");
        List<Integer> attackerDice = rollDice(attackerNumDice);
        List<Integer> defenderDice = rollDice(defenderNumDice);
        int attackerLosses = 0;
        int defenderLosses = 0;
        int comparisonCount = Math.min(attackerDice.size(), defenderDice.size());

        for (int comparisonIndex = 0; comparisonIndex < comparisonCount; comparisonIndex++) {
            if (attackerDice.get(comparisonIndex) > defenderDice.get(comparisonIndex)) {
                defenderLosses++;
            } else {
                attackerLosses++;
            }
        }

        if (attackerLosses > 0) {
            attackingTerritory.removeArmies(createInfantryPieces(attackerLosses));
        }

        if (defenderLosses > 0) {
            defendingTerritory.removeArmies(createInfantryPieces(defenderLosses));
        }

        return List.of(
                "Attacker dice: " + attackerDice,
                "Defender dice: " + defenderDice,
                "Attacker losses: " + attackerLosses,
                "Defender losses: " + defenderLosses,
                "Attacking territory armies: " + attackingTerritory.getArmyCount(),
                "Defending territory armies: " + defendingTerritory.getArmyCount(),
                "Captured: " + (defendingTerritory.getArmyCount() == 0));
    }

    public boolean isTerritoryCaptured(final String defenderTerritoryName) {
        Territory defendingTerritory = findTerritoryOrThrow(
                defenderTerritoryName,
                "Defending territory must exist on the board.");

        return defendingTerritory.getArmyCount() == 0;
    }

    public boolean validateCaptureMovement(
            final String attackerTerritoryName,
            final String defenderTerritoryName,
            final int armiesToMove,
            final int attackerDiceUsed) {
        Territory attackingTerritory = findTerritoryOrThrow(
                attackerTerritoryName,
                "Attacking territory must exist on the board.");

        if (!isTerritoryCaptured(defenderTerritoryName)) {
            throw new IllegalArgumentException(
                    "Cannot move armies because the defending territory has not been captured.");
        }

        if (armiesToMove <= ZERO_ARMIES) {
            throw new IllegalArgumentException(
                    "Attacker must move at least one army into a captured territory.");
        }

        if (armiesToMove >= attackingTerritory.getArmyCount()) {
            throw new IllegalArgumentException(
                    "Attacker must leave at least one army behind.");
        }

        int maximumMovableArmies = attackingTerritory.getArmyCount() - 1;
        int minimumArmiesToMove = Math.min(attackerDiceUsed, maximumMovableArmies);

        if (armiesToMove < minimumArmiesToMove) {
            throw new IllegalArgumentException(
                    "Attacker must move at least the number of dice used in the final attack when possible.");
        }

        return true;
    }

    public String captureTerritory(
            final String attackerTerritoryName,
            final String defenderTerritoryName,
            final int armiesToMove,
            final int attackerDiceUsed) {
        validateCaptureMovement(
                attackerTerritoryName,
                defenderTerritoryName,
                armiesToMove,
                attackerDiceUsed);

        Territory attackingTerritory = findTerritoryOrThrow(
                attackerTerritoryName,
                "Attacking territory must exist on the board.");
        Territory defendingTerritory = findTerritoryOrThrow(
                defenderTerritoryName,
                "Defending territory must exist on the board.");
        Player attackingPlayer = players.get(currentPlayerIndex);
        Player defendingPlayer = defendingTerritory.getOwner();
        String defendingPlayerName = defendingPlayer.getName();
        HashMap<ArmyType, Integer> movedArmies = createInfantryPieces(armiesToMove);

        defendingPlayer.removeTerritory(defendingTerritory);
        attackingPlayer.addTerritory(defendingTerritory);
        defendingTerritory.setOwner(attackingPlayer);
        attackingTerritory.removeArmies(movedArmies);
        defendingTerritory.addArmies(movedArmies);

        return defendingPlayerName;
    }

    public boolean handlePlayerElimination(final String defenderName) {
        Player defender = findPlayerByName(defenderName);

        if (defender.getTerritoryCount() > ZERO_ARMIES) {
            return false;
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        defender.markEliminated();
        currentPlayer.addCards(defender.removeAllCards());
        return true;
    }

    public boolean currentPlayerHasValidAttack() {
        Player currentPlayer = players.get(currentPlayerIndex);

        for (Territory territory : territories) {
            if (territory.isOwnedBy(currentPlayer)
                    && territory.getArmyCount() >= 2
                    && hasAdjacentEnemyTerritory(territory, currentPlayer)) {
                return true;
            }
        }

        return false;
    }

    public boolean awardRiskCardIfCaptured(final boolean capturedTerritoryThisTurn) {
        if (!capturedTerritoryThisTurn) {
            return false;
        }

        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.addCard(deck.drawCard());
        return true;
    }

    private boolean hasAdjacentEnemyTerritory(
            final Territory territory,
            final Player currentPlayer) {
        for (Territory adjacentTerritory : territory.getAdjacentTerritories()) {
            if (!adjacentTerritory.isUnclaimed()
                    && !adjacentTerritory.isOwnedBy(currentPlayer)) {
                return true;
            }
        }

        return false;
    }

    private List<Integer> rollDice(final int numDice) {
        List<Integer> dice = new ArrayList<>();

        for (int dieIndex = 0; dieIndex < numDice; dieIndex++) {
            dice.add(random.nextInt(DIE_SIDE_COUNT) + 1);
        }

        dice.sort(Collections.reverseOrder());
        return dice;
    }


    public void addArmiesToCurrentPlayerBasedOnContinents() {
        Player player = players.get(currentPlayerIndex);

        for (Continent continent : continents) {
            if (continent.isFullyOwnedBy(player)) {
                player.addArmies(createArmyPieces(
                        continent.getBonusArmies(),
                        ZERO_ARMIES,
                        ZERO_ARMIES));
            }
        }
    }

    public void addArmiesToCurrentPlayerBasedOnTerritories() {
        Player player = players.get(currentPlayerIndex);
        player.addArmiesToAvailableBasedOnTerritories();
    }

    public boolean handleCardTradeIn(final List<Integer> cardIndices) {
        if (cardIndices == null || cardIndices.isEmpty()) {
            return true;
        }

        Player player = players.get(currentPlayerIndex);
        boolean tradedIn = player.tradeCardsAndAddArmies(cardIndices, deck, numSetsTradedIn);

        if (tradedIn) {
            numSetsTradedIn++;
        }

        return tradedIn;
    }

    public TradeInPossibility checkCardTradeInPossibility() {
        HumanPlayer player = (HumanPlayer) players.get(currentPlayerIndex);
        int cardCount = player.getCardCount();

        if (cardCount < MIN_PLAYER_COUNT) {
            return TradeInPossibility.NOT_ALLOWED;
        }

        if (cardCount >= REQUIRED_TRADE_IN_CARD_COUNT) {
            return TradeInPossibility.REQUIRED;
        }

        if (hasAnyValidTradeInSet(player.getAvailableCards())) {
            return TradeInPossibility.ALLOWED;
        }

        return TradeInPossibility.NOT_ALLOWED;
    }

    private boolean hasAnyValidTradeInSet(final List<RiskCard> cards) {
        if (cards.size() < MIN_PLAYER_COUNT) {
            return false;
        }

        for (RiskCard firstCard : cards) {
            for (RiskCard secondCard : cards) {
                for (RiskCard thirdCard : cards) {
                    if (firstCard != secondCard
                            && firstCard != thirdCard
                            && secondCard != thirdCard
                            && isValidTradeInSet(List.of(firstCard, secondCard, thirdCard))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isValidTradeInSet(final List<RiskCard> selectedCards) {
        CardType firstCardType = selectedCards.get(0).getType();
        boolean hasThreeCardsOfSameType = firstCardType != CardType.WILD
                && selectedCards.stream().allMatch(card -> card.getType() == firstCardType);
        boolean hasOneOfEachType = selectedCards.stream()
                .anyMatch(card -> card.getType() == CardType.INFANTRY)
                && selectedCards.stream().anyMatch(card -> card.getType() == CardType.CAVALRY)
                && selectedCards.stream().anyMatch(card -> card.getType() == CardType.ARTILLERY);
        long wildCardCount = selectedCards.stream()
                .filter(card -> card.getType() == CardType.WILD)
                .count();
        long nonWildCardCount = selectedCards.stream()
                .filter(card -> card.getType() != CardType.WILD)
                .count();

        return hasThreeCardsOfSameType
                || hasOneOfEachType
                || (wildCardCount == 1 && nonWildCardCount == 2);
    }

    public boolean areTerritoriesAdjacent(
            final String territory1Name,
            final String territory2Name) {
        Territory t1 = findTerritoryByName(territory1Name);
        Territory t2 = findTerritoryByName(territory2Name);
        return t1.getAdjacentTerritories().contains(t2);
    }
}
