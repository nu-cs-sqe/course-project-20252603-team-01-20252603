package code.model;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests board initialization behavior for the GameModel class.
 */
public final class GameModelTest {

    private static final int DECK_CARD_COUNT = 44;

    private static final int BELOW_MIN_PLAYER_COUNT = 2;

    private static final int MIN_PLAYER_COUNT = 3;

    private static final int FOUR_PLAYER_COUNT = 4;

    private static final int FIVE_PLAYER_COUNT = 5;

    private static final int MAX_PLAYER_COUNT = 6;

    private static final int ABOVE_MAX_PLAYER_COUNT = 7;

    private static final int THREE_PLAYER_STARTING_INFANTRY = 35;

    private static final int FOUR_PLAYER_STARTING_INFANTRY = 30;

    private static final int FIVE_PLAYER_STARTING_INFANTRY = 25;

    private static final int SIX_PLAYER_STARTING_INFANTRY = 20;

    private static final int ONE_INFANTRY = 1;

    private static final int ZERO_INFANTRY = 0;

    private static final int TWO_INFANTRY = 2;

    private static final int THIRTY_FOUR_INFANTRY = 34;

    private static final int TERRITORY_COUNT = 42;

    private static final int ZERO_ARMIES = 0;

    private static final int ONE_ARMY = 1;

    private static final int TWO_ARMIES = 2;

    private static final int THREE_ARMIES = 3;

    private static final int FIVE_ARMIES = 5;

    private static final int FIFTEEN_ARMIES = 15;

    private static final int FOUR_CARD_TRADE_IN_ARMIES = 4;

    private static final int EIGHT_TERRITORIES = 8;

    private static final int TWELVE_TERRITORIES = 12;

    private static final int FORTY_ONE_TERRITORIES = 41;

    private static final int AUSTRALIA_TERRITORY_COUNT = 4;

    private static final int SOUTH_AMERICA_TERRITORY_COUNT = 4;

    private static final int AFRICA_TERRITORY_COUNT = 6;

    private static final int EUROPE_TERRITORY_COUNT = 7;

    private static final int NORTH_AMERICA_TERRITORY_COUNT = 9;

    private static final int ASIA_TERRITORY_COUNT = 12;

    private GameModel createGameModel() {
        return new GameModel(new Random(0));
    }

    private GameModel createGameModelWithDiceRolls(final int... diceRolls) {
        return new GameModel(new FixedDiceRandom(diceRolls));
    }

    private static final class FixedDiceRandom extends Random {
        private final int[] diceRolls;

        private int nextRollIndex;

        private FixedDiceRandom(final int... fixedDiceRolls) {
            diceRolls = fixedDiceRolls;
            nextRollIndex = 0;
        }

        @Override
        public int nextInt(final int bound) {
            int diceRoll = diceRolls[nextRollIndex];
            nextRollIndex++;
            return diceRoll - 1;
        }
    }

    @Test
    public void constructorWithInjectedRandomConstructsGameModel() {
        GameModel gameModel = createGameModel();

        assertNotNull(gameModel);
        assertFalse(gameModel.isDeckEmpty());
    }

    @Test
    public void deckHasFortyFourCardsAfterBoardInitialization() {
        GameModel gameModel = createGameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(DECK_CARD_COUNT, gameModel.getDeckSize());
    }

    @Test
    public void deckIsNotEmptyAfterBoardInitialization() {
        GameModel gameModel = createGameModel();

        gameModel.initializeContinentsAndTerritories();

        assertFalse(gameModel.isDeckEmpty());
    }

    @Test
    public void setPlayerCountBelowMinimumPlayerCountReturnsFalse() {
        GameModel gameModel = createGameModel();

        assertFalse(gameModel.setPlayerCount(BELOW_MIN_PLAYER_COUNT));
    }

    @Test
    public void setPlayerCountAboveMaximumPlayerCountReturnsFalse() {
        GameModel gameModel = createGameModel();

        assertFalse(gameModel.setPlayerCount(ABOVE_MAX_PLAYER_COUNT));
    }

    @Test
    public void addPlayerThreePlayerGameWithNoRegisteredPlayersReturnsPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        String availableArmies = player.getAvailableArmies();

        assertEquals("Player 1", player.getName());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(THREE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addPlayerFourPlayerGameWithNoRegisteredPlayersReturnsPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(FOUR_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.BLUE);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(FOUR_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addPlayerFivePlayerGameWithNoRegisteredPlayersReturnsPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(FIVE_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.GREEN);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(FIVE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addPlayerSixPlayerGameWithNoRegisteredPlayersReturnsPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MAX_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.YELLOW);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(SIX_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addPlayerPlayerListAlreadyFullReturnsNullPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        Player extraPlayer = gameModel.addPlayer("Player 4", PlayerColor.YELLOW);

        assertInstanceOf(NullPlayer.class, extraPlayer);
    }

    @Test
    public void setCurrentPlayerIndexFirstPlayerIndexSetsCurrentPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(0);

        assertEquals("Player 1", gameModel.getCurrentPlayerName());
    }

    @Test
    public void setCurrentPlayerIndexLastPlayerIndexSetsCurrentPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(2);

        assertEquals("Player 3", gameModel.getCurrentPlayerName());
    }

    @Test
    public void setCurrentPlayerIndexIndexBelowRangeDoesNotChangeCurrentPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(1);
        gameModel.setCurrentPlayerIndex(-1);

        assertEquals("Player 2", gameModel.getCurrentPlayerName());
    }

    @Test
    public void setCurrentPlayerIndexIndexAboveRangeDoesNotChangeCurrentPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(1);
        gameModel.setCurrentPlayerIndex(MIN_PLAYER_COUNT);
        assertEquals("Player 2", gameModel.getCurrentPlayerName());
    }

    @Test
    public void getCurrentPlayerNameSelectedFirstPlayerReturnsFirstPlayerName() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(0);
        assertEquals("Player 1", gameModel.getCurrentPlayerName());
    }

    @Test
    public void getCurrentPlayerNameSelectedLastPlayerReturnsLastPlayerName() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(2);
        assertEquals("Player 3", gameModel.getCurrentPlayerName());
    }

    private HashMap<ArmyType, Integer> createInfantryPieces(final int infantryCount) {
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, infantryCount);

        return pieces;
    }

    private RiskCard createCard(final CardType cardType) {
        if (cardType == CardType.WILD) {
            return new RiskCard(null, CardType.WILD, true);
        }

        Territory territory = new Territory(
                "Test Territory",
                new Continent("Test Continent", FIVE_ARMIES),
                List.of());

        return new RiskCard(territory, cardType, false);
    }

    private void addValidTradeInSet(final HumanPlayer player) {
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));
    }

    @Test
    public void claimTerritoryDuringSetupUnclaimedTerritoryWithOneInfantryReturnsTrue() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> pieces = createInfantryPieces(ONE_INFANTRY);

        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", pieces);

        String availableArmies = player.getAvailableArmies();

        assertTrue(claimed);
        assertEquals(ONE_INFANTRY, player.getTerritoryCount());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(THIRTY_FOUR_INFANTRY)));
    }

    @Test
    public void claimTerritoryDuringSetupAlreadyClaimedTerritoryReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player playerOne = gameModel.addPlayer("Player 1", PlayerColor.RED);
        Player playerTwo = gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> pieces = createInfantryPieces(ONE_INFANTRY);

        gameModel.claimTerritoryDuringSetup("Alaska", pieces);
        gameModel.advanceCurrentPlayerIndex();

        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", pieces);

        String playerTwoAvailableArmies = playerTwo.getAvailableArmies();

        assertFalse(claimed);
        assertEquals(ONE_INFANTRY, playerOne.getTerritoryCount());
        assertEquals(ZERO_INFANTRY, playerTwo.getTerritoryCount());
        assertTrue(playerTwoAvailableArmies.contains("INFANTRY"));
        assertTrue(playerTwoAvailableArmies.contains(
                String.valueOf(THREE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void claimTerritoryDuringSetupZeroInfantryReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> pieces = createInfantryPieces(ZERO_INFANTRY);

        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", pieces);

        String availableArmies = player.getAvailableArmies();

        assertFalse(claimed);
        assertEquals(ZERO_INFANTRY, player.getTerritoryCount());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(
                String.valueOf(THREE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void claimTerritoryDuringSetupMoreThanOneInfantryReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> pieces = createInfantryPieces(TWO_INFANTRY);

        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", pieces);

        String availableArmies = player.getAvailableArmies();

        assertFalse(claimed);
        assertEquals(ZERO_INFANTRY, player.getTerritoryCount());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(
                String.valueOf(THREE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void claimTerritoryDuringSetupNoAvailableInfantryReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> allAvailableArmies =
                createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY);
        HashMap<ArmyType, Integer> pieces = createInfantryPieces(ONE_INFANTRY);

        player.removeArmies(allAvailableArmies);

        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", pieces);

        String availableArmies = player.getAvailableArmies();

        assertFalse(claimed);
        assertEquals(ZERO_INFANTRY, player.getTerritoryCount());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(ZERO_INFANTRY)));
    }

    @Test
    public void advanceCurrentPlayerIndexNoPlayersReturnsFalse() {
        GameModel gameModel = createGameModel();

        boolean advanced = gameModel.advanceCurrentPlayerIndex();

        assertFalse(advanced);
    }

    @Test
    public void advanceCurrentPlayerIndexLastPlayerWrapsToFirstPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(2);

        boolean advanced = gameModel.advanceCurrentPlayerIndex();

        assertTrue(advanced);
        assertEquals("Player 1", gameModel.getCurrentPlayerName());
    }

    @Test
    public void advanceCurrentPlayerIndexMiddlePlayerAdvancesToNextPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(1);

        boolean advanced = gameModel.advanceCurrentPlayerIndex();

        assertTrue(advanced);
        assertEquals("Player 3", gameModel.getCurrentPlayerName());
    }

    @Test
    public void advanceCurrentPlayerIndexFirstPlayerAdvancesToSecondPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        boolean advanced = gameModel.advanceCurrentPlayerIndex();

        assertTrue(advanced);
        assertEquals("Player 2", gameModel.getCurrentPlayerName());
    }

    @Test
    public void areAllTerritoriesClaimedReturnsFalseWhenNoTerritoriesClaimed() {
        GameModel gameModel = createGameModel();

        gameModel.initializeContinentsAndTerritories();

        assertFalse(gameModel.areAllTerritoriesClaimed());
    }

    private List<String> getTerritoryNames() {
        return List.of(
                "Alaska",
                "Northwest Territory",
                "Greenland",
                "Alberta",
                "Ontario",
                "Quebec",
                "Western United States",
                "Eastern United States",
                "Central America",
                "Venezuela",
                "Peru",
                "Brazil",
                "Argentina",
                "Iceland",
                "Scandinavia",
                "Ukraine",
                "Great Britain",
                "Northern Europe",
                "Western Europe",
                "Southern Europe",
                "North Africa",
                "Egypt",
                "East Africa",
                "Congo",
                "South Africa",
                "Madagascar",
                "Ural",
                "Siberia",
                "Yakutsk",
                "Kamchatka",
                "Irkutsk",
                "Mongolia",
                "Japan",
                "Afghanistan",
                "China",
                "Middle East",
                "India",
                "Siam",
                "Indonesia",
                "New Guinea",
                "Western Australia",
                "Eastern Australia");
    }

    private void claimTerritories(
            final GameModel gameModel,
            final int territoryCount) {
        HashMap<ArmyType, Integer> pieces = createInfantryPieces(ONE_INFANTRY);
        List<String> territoryNames = getTerritoryNames();

        for (int index = 0; index < territoryCount; index++) {
            gameModel.claimTerritoryDuringSetup(territoryNames.get(index), pieces);
        }
    }

    @Test
    public void areAllTerritoriesClaimedReturnsFalseWhenOneTerritoryRemainsUnclaimed() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        player.addArmies(createInfantryPieces(TERRITORY_COUNT));

        claimTerritories(gameModel, TERRITORY_COUNT - ONE_INFANTRY);

        assertFalse(gameModel.areAllTerritoriesClaimed());
    }

    @Test
    public void areAllTerritoriesClaimedReturnsTrueWhenAllTerritoriesClaimed() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        player.addArmies(createInfantryPieces(TERRITORY_COUNT));

        claimTerritories(gameModel, TERRITORY_COUNT);

        assertTrue(gameModel.areAllTerritoriesClaimed());
    }

    @Test
    public void getCurrentPlayerNameFirstPlayerIndexReturnsFirstPlayerName() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        assertEquals("Player 1", gameModel.getCurrentPlayerName());
    }

    @Test
    public void getCurrentPlayerNameMiddlePlayerIndexReturnsMiddlePlayerName() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(1);

        assertEquals("Player 2", gameModel.getCurrentPlayerName());
    }

    @Test
    public void getUnclaimedTerritoriesByContinentReturnsAllTerritoriesWhenNoneClaimed() {
        GameModel gameModel = createGameModel();

        gameModel.initializeContinentsAndTerritories();

        String unclaimedTerritories = gameModel.getUnclaimedTerritoriesByContinent();

        assertTrue(unclaimedTerritories.contains("North America"));
        assertTrue(unclaimedTerritories.contains("Alaska"));
        assertTrue(unclaimedTerritories.contains("South America"));
        assertTrue(unclaimedTerritories.contains("Brazil"));
        assertTrue(unclaimedTerritories.contains("Australia"));
        assertTrue(unclaimedTerritories.contains("Eastern Australia"));
    }

    @Test
    public void getUnclaimedTerritoriesByContinentExcludesClaimedTerritory() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        String unclaimedTerritories = gameModel.getUnclaimedTerritoriesByContinent();

        assertTrue(unclaimedTerritories.contains("North America"));
        assertFalse(unclaimedTerritories.contains("Alaska"));
        assertTrue(unclaimedTerritories.contains("Alberta"));
    }

    @Test
    public void getCurrentPlayerTerritoriesByContinentReturnsNoTerritoriesWhenNoneOwned() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        String ownedTerritories = gameModel.getCurrentPlayerTerritoriesByContinent();

        assertTrue(ownedTerritories.contains("Player 1"));
        assertFalse(ownedTerritories.contains("Alaska"));
        assertFalse(ownedTerritories.contains("Brazil"));
    }

    @Test
    public void getCurrentPlayerTerritoriesByContinentExcludesOtherPlayerTerritory() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();

        String ownedTerritories = gameModel.getCurrentPlayerTerritoriesByContinent();

        assertTrue(ownedTerritories.contains("Player 2"));
        assertFalse(ownedTerritories.contains("Alaska"));
    }

    private HashMap<ArmyType, Integer> createArmies(
            final int infantry,
            final int cavalry,
            final int artillery) {
        HashMap<ArmyType, Integer> armies = new HashMap<>();
        armies.put(ArmyType.INFANTRY, infantry);
        armies.put(ArmyType.CAVALRY, cavalry);
        armies.put(ArmyType.ARTILLERY, artillery);

        return armies;
    }

    @Test
    public void hasCurrentPlayerAvailableArmiesCurrentPlayerHasZeroArmiesRemainingReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY));

        assertFalse(gameModel.hasCurrentPlayerAvailableArmies());
    }

    @Test
    public void hasCurrentPlayerAvailableArmiesCurrentPlayerHasOneArmyRemainingReturnsTrue() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        player.removeArmies(createInfantryPieces(THIRTY_FOUR_INFANTRY));

        assertTrue(gameModel.hasCurrentPlayerAvailableArmies());
    }

    @Test
    public void hasCurrentPlayerAvailableArmiesCurrentPlayerHasMultipleArmiesRemainingReturnsTrue() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        assertTrue(gameModel.hasCurrentPlayerAvailableArmies());
    }

    @Test
    public void addArmiesDuringSetupOwnedTerritoryWithOneInfantryReturnsTrue() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        boolean added = gameModel.addArmiesDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        assertTrue(added);
        assertTrue(player.getAvailableArmies().contains(
                String.valueOf(THIRTY_FOUR_INFANTRY - ONE_INFANTRY)));
    }

    @Test
    public void addArmiesDuringSetupOwnedTerritoryWithFinalInfantryReturnsTrue() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(
                THIRTY_FOUR_INFANTRY - ONE_INFANTRY));

        boolean added = gameModel.addArmiesDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        assertTrue(added);
        assertFalse(gameModel.hasCurrentPlayerAvailableArmies());
    }

    @Test
    public void addArmiesDuringSetupTerritoryOwnedByAnotherPlayerReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        Player playerTwo = gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();

        boolean added = gameModel.addArmiesDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        assertFalse(added);
        assertTrue(playerTwo.getAvailableArmies().contains(
                String.valueOf(THREE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addArmiesDuringSetupUnknownTerritoryNameReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        boolean added = gameModel.addArmiesDuringSetup(
                "Unknown Territory",
                createInfantryPieces(ONE_INFANTRY));

        assertFalse(added);
        assertTrue(player.getAvailableArmies().contains(
                String.valueOf(THREE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addArmiesDuringSetupOwnedTerritoryWithZeroInfantryReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        boolean added = gameModel.addArmiesDuringSetup(
                "Alaska",
                createInfantryPieces(ZERO_INFANTRY));

        assertFalse(added);
        assertTrue(player.getAvailableArmies().contains(
                String.valueOf(THIRTY_FOUR_INFANTRY)));
    }

    @Test
    public void addTwoArmiesDuringSetupOwnedTerritoryReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        boolean added = gameModel.addArmiesDuringSetup(
                "Alaska",
                createInfantryPieces(TWO_INFANTRY));

        assertFalse(added);
        assertTrue(player.getAvailableArmies().contains(
                String.valueOf(THIRTY_FOUR_INFANTRY)));
    }

    @Test
    public void addArmiesDuringSetupOwnedTerritoryWithNoArmiesRemainingReturnsFalse() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(THIRTY_FOUR_INFANTRY));

        boolean added = gameModel.addArmiesDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        assertFalse(added);
        assertTrue(player.getAvailableArmies().contains(
                String.valueOf(ZERO_INFANTRY)));
    }

    @Test
    public void placeArmiesDuringReinforcementPlacesOneInfantryOnOwnedTerritory() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> setupPiece = createInfantryPieces(ONE_INFANTRY);
        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", setupPiece);

        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);
        player.addArmies(reinforcementPieces);

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertTrue(claimed);
        assertTrue(placed);
    }

    @Test
    public void placeArmiesDuringReinforcementPlacesMixedArmiesOnOwnedTerritory() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> setupPiece = createInfantryPieces(ONE_INFANTRY);
        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", setupPiece);

        HashMap<ArmyType, Integer> availableArmies =
                createArmies(FIFTEEN_ARMIES, ZERO_ARMIES, ZERO_ARMIES);
        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(ZERO_ARMIES, ONE_ARMY, ONE_ARMY);

        player.addArmies(availableArmies);

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertTrue(claimed);
        assertTrue(placed);
    }

    @Test
    public void placeArmiesDuringReinforcementRejectsZeroArmies() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> setupPiece = createInfantryPieces(ONE_INFANTRY);
        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", setupPiece);

        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(ZERO_ARMIES, ZERO_ARMIES, ZERO_ARMIES);
        player.addArmies(createArmies(THREE_ARMIES, ZERO_ARMIES, ZERO_ARMIES));

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertTrue(claimed);
        assertFalse(placed);
    }

    @Test
    public void placeArmiesDuringReinforcementRejectsNegativeInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> setupPiece = createInfantryPieces(ONE_INFANTRY);
        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", setupPiece);

        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(-ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);
        player.addArmies(createArmies(THREE_ARMIES, ZERO_ARMIES, ZERO_ARMIES));

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertTrue(claimed);
        assertFalse(placed);
    }

    @Test
    public void placeArmiesDuringReinforcementRejectsNegativeCavalry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> setupPiece = createInfantryPieces(ONE_INFANTRY);
        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", setupPiece);

        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(ZERO_ARMIES, -ONE_ARMY, ZERO_ARMIES);
        player.addArmies(createArmies(THREE_ARMIES, ZERO_ARMIES, ZERO_ARMIES));

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertTrue(claimed);
        assertFalse(placed);
    }

    @Test
    public void placeArmiesDuringReinforcementRejectsNegativeArtillery() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> setupPiece = createInfantryPieces(ONE_INFANTRY);
        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", setupPiece);

        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(ZERO_ARMIES, ZERO_ARMIES, -ONE_ARMY);
        player.addArmies(createArmies(THREE_ARMIES, ZERO_ARMIES, ZERO_ARMIES));

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertTrue(claimed);
        assertFalse(placed);
    }

    @Test
    public void placeArmiesDuringReinforcementRejectsMoreArmiesThanAvailable() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> setupPiece = createInfantryPieces(ONE_INFANTRY);
        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", setupPiece);

        player.removeArmies(createInfantryPieces(THIRTY_FOUR_INFANTRY));

        HashMap<ArmyType, Integer> availableArmies =
                createArmies(TWO_ARMIES, ZERO_ARMIES, ZERO_ARMIES);
        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(THREE_ARMIES, ZERO_ARMIES, ZERO_ARMIES);

        player.addArmies(availableArmies);

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertTrue(claimed);
        assertFalse(placed);
    }

    @Test
    public void placeArmiesDuringReinforcementRejectsOtherPlayersTerritory() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        Player playerTwo = gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> setupPiece = createInfantryPieces(ONE_INFANTRY);
        boolean claimed = gameModel.claimTerritoryDuringSetup("Alaska", setupPiece);

        gameModel.advanceCurrentPlayerIndex();

        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);
        playerTwo.addArmies(reinforcementPieces);

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertTrue(claimed);
        assertFalse(placed);
    }

    @Test
    public void placeArmiesDuringReinforcementRejectsUnownedTerritory() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        HashMap<ArmyType, Integer> reinforcementPieces =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);
        player.addArmies(reinforcementPieces);

        boolean placed = gameModel.placeArmiesDuringReinforcement(
                "Alaska",
                reinforcementPieces);

        assertFalse(placed);
    }

    @Test
    public void currentPlayerHasAvailableArmiesReturnsFalseWhenNoArmiesAvailable() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY));

        assertFalse(gameModel.currentPlayerHasAvailableArmies());
    }

    @Test
    public void currentPlayerHasAvailableArmiesReturnsTrueWhenOneInfantryAvailable() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY));
        player.addArmies(createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES));

        assertTrue(gameModel.currentPlayerHasAvailableArmies());
    }

    @Test
    public void currentPlayerHasAvailableArmiesReturnsTrueWhenOnlyCavalryAvailable() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY));
        player.addArmies(createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES));

        assertTrue(gameModel.currentPlayerHasAvailableArmies());
    }

    @Test
    public void currentPlayerHasAvailableArmiesReturnsTrueWhenOnlyArtilleryAvailable() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY));
        player.addArmies(createArmies(ZERO_ARMIES, ZERO_ARMIES, ONE_ARMY));

        assertTrue(gameModel.currentPlayerHasAvailableArmies());
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnContinentsWithNoFullContinentAddsNoBonus() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Brazil", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("India", createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY - THREE_ARMIES));

        gameModel.addArmiesToCurrentPlayerBasedOnContinents();

        assertTrue(player.getAvailableArmies().contains("INFANTRY"));
        assertTrue(player.getAvailableArmies().contains(String.valueOf(ZERO_INFANTRY)));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnTerritoriesWithOneTerritoryAddsThreeInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY - ONE_INFANTRY));

        gameModel.addArmiesToCurrentPlayerBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=3"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnTerritoriesWithEightTerritoriesAddsThreeInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        claimTerritories(gameModel, EIGHT_TERRITORIES);
        player.removeArmies(createInfantryPieces(
                THREE_PLAYER_STARTING_INFANTRY - EIGHT_TERRITORIES));

        gameModel.addArmiesToCurrentPlayerBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=3"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnTerritoriesWithTwelveTerritoriesAddsFourInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        claimTerritories(gameModel, TWELVE_TERRITORIES);
        player.removeArmies(createInfantryPieces(
                THREE_PLAYER_STARTING_INFANTRY - TWELVE_TERRITORIES));

        gameModel.addArmiesToCurrentPlayerBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=4"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnTerritoriesWithFortyOneTerritoriesAddsThirteenInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        player.addArmies(createInfantryPieces(TERRITORY_COUNT));
        claimTerritories(gameModel, FORTY_ONE_TERRITORIES);
        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY + ONE_INFANTRY));

        gameModel.addArmiesToCurrentPlayerBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=13"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnTerritoriesWithZeroTerritoriesRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                gameModel::addArmiesToCurrentPlayerBasedOnTerritories);

        assertEquals(
                "Player cannot own 0 territories and play a turn because they have been eliminated.",
                exception.getMessage());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=35"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnTerritoriesWithFortyTwoTerritoriesRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        player.addArmies(createInfantryPieces(TERRITORY_COUNT));
        claimTerritories(gameModel, TERRITORY_COUNT);
        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                gameModel::addArmiesToCurrentPlayerBasedOnTerritories);

        assertEquals(
                "Player cannot own 42 territories and play a turn because they should have already won.",
                exception.getMessage());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void setPlayerCountMinimumValidCountReturnsTrue() {
        GameModel gameModel = createGameModel();

        assertTrue(gameModel.setPlayerCount(MIN_PLAYER_COUNT));
    }

    @Test
    public void setPlayerCountMaximumValidCountReturnsTrue() {
        GameModel gameModel = createGameModel();

        assertTrue(gameModel.setPlayerCount(MAX_PLAYER_COUNT));
    }

    @Test
    public void setCurrentPlayerIndexZeroAfterNonZeroIndexSetsFirstPlayer() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(2);
        gameModel.setCurrentPlayerIndex(0);

        assertEquals("Player 1", gameModel.getCurrentPlayerName());
    }

    @Test
    public void getUnclaimedTerritoriesByContinentShowsAlaskaUnderNorthAmericaSection() {
        GameModel gameModel = createGameModel();

        gameModel.initializeContinentsAndTerritories();

        String output = gameModel.getUnclaimedTerritoriesByContinent();
        int northAmericaIndex = output.indexOf("North America");
        int southAmericaIndex = output.indexOf("South America");
        int alaskaIndex = output.indexOf("Alaska");

        assertTrue(alaskaIndex > northAmericaIndex);
        assertTrue(alaskaIndex < southAmericaIndex);
    }

    @Test
    public void getCurrentPlayerTerritoriesByContinentShowsClaimedAlaskaUnderNorthAmerica() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));

        String output = gameModel.getCurrentPlayerTerritoriesByContinent();
        int northAmericaIndex = output.indexOf("North America");
        int southAmericaIndex = output.indexOf("South America");
        int alaskaIndex = output.indexOf("Alaska");

        assertTrue(alaskaIndex > northAmericaIndex);
        assertTrue(alaskaIndex < southAmericaIndex);
    }

    @Test
    public void placeArmiesDuringReinforcementDepletesPlayerAvailableArmies() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));
        HashMap<ArmyType, Integer> allRemaining =
                createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY - ONE_INFANTRY);

        boolean placed = gameModel.placeArmiesDuringReinforcement("Alaska", allRemaining);

        assertTrue(placed);
        assertFalse(gameModel.currentPlayerHasAvailableArmies());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void initializeContinentsAndTerritoriesCalledTwiceDoesNotDuplicateTerritoriesInUnclaimedList() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        String output = gameModel.getUnclaimedTerritoriesByContinent();

        assertFalse(output.contains("Alaska"));
    }

    @Test
    public void initializeContinentsAndTerritoriesCalledTwiceDoesNotDuplicateContinentSections() {
        GameModel gameModel = createGameModel();

        gameModel.initializeContinentsAndTerritories();
        gameModel.initializeContinentsAndTerritories();

        String output = gameModel.getUnclaimedTerritoriesByContinent();
        int firstOccurrence = output.indexOf("North America");
        int secondOccurrence = output.indexOf("North America", firstOccurrence + ONE_ARMY);

        assertEquals(-1, secondOccurrence);
    }

    @Test
    public void isDeckEmptyReturnsFalseWhenDeckHasCards() {
        GameModel gameModel = createGameModel();

        assertFalse(gameModel.isDeckEmpty());
    }

    @Test
    public void initializeContinentsAndTerritoriesInitializesDeck() {
        GameModel gameModel = createGameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(DECK_CARD_COUNT, gameModel.getDeckSize());
        assertFalse(gameModel.isDeckEmpty());
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameMinimumValidAttackReturnsDefendingTerritoryName() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        String defendingTerritoryName =
                gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Alaska",
                        "Alberta");

        assertEquals("Alberta", defendingTerritoryName);
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameMoreThanMinimumArmiesReturnsDefendingTerritoryName() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(TWO_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        String defendingTerritoryName =
                gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Alaska",
                        "Alberta");

        assertEquals("Alberta", defendingTerritoryName);
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameAttackingTerritoryNotOwnedRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.setCurrentPlayerIndex(2);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Alaska",
                        "Alberta"));

        assertEquals("Current player must own the attacking territory.", exception.getMessage());
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameDefendingTerritoryOwnedByCurrentPlayerRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Alaska",
                        "Alberta"));

        assertEquals("Defending territory must be owned by another player.", exception.getMessage());
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameNonAdjacentTerritoriesRaiseException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Brazil", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Alaska",
                        "Brazil"));

        assertEquals("Attacking and defending territories must be adjacent.", exception.getMessage());
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameOneArmyAttackingTerritoryRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Alaska",
                        "Alberta"));

        assertEquals("Attacking territory must have at least 2 armies.", exception.getMessage());
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameSameTerritoryRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Alaska",
                        "Alaska"));

        assertEquals(
                "Attacking and defending territories must be different territories.",
                exception.getMessage());
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameUnknownAttackingTerritoryRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Atlantis",
                        "Alberta"));

        assertEquals("Attacking territory must exist on the board.", exception.getMessage());
    }

    @Test
    public void validateTerritoriesForAttackAndReturnDefenderNameUnknownDefendingTerritoryRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateTerritoriesForAttackAndReturnDefenderName(
                        "Alaska",
                        "Atlantis"));

        assertEquals("Defending territory must exist on the board.", exception.getMessage());
    }

    @Test
    public void validateNumberOfDiceMinimumValidDiceCountsReturnsTrue() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        boolean validDice = gameModel.validateNumberOfDice(
                "Alaska",
                "Alberta",
                ONE_ARMY,
                ONE_ARMY);

        assertTrue(validDice);
    }

    @Test
    public void validateNumberOfDiceMaximumValidDiceCountsReturnsTrue() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(THREE_ARMIES));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alberta").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        boolean validDice = gameModel.validateNumberOfDice(
                "Alaska",
                "Alberta",
                THREE_ARMIES,
                TWO_ARMIES);

        assertTrue(validDice);
    }

    @Test
    public void validateNumberOfDiceThreeArmiesAllowsTwoAttackerDice() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(TWO_ARMIES));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        boolean validDice = gameModel.validateNumberOfDice(
                "Alaska",
                "Alberta",
                TWO_ARMIES,
                ONE_ARMY);

        assertTrue(validDice);
    }

    @Test
    public void validateNumberOfDiceZeroAttackerDiceRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateNumberOfDice(
                        "Alaska",
                        "Alberta",
                        ZERO_ARMIES,
                        ONE_ARMY));

        assertEquals("Attacker must roll between 1 and 3 dice.", exception.getMessage());
    }

    @Test
    public void validateNumberOfDiceAttackerRollingMoreThanArmiesMinusOneRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateNumberOfDice(
                        "Alaska",
                        "Alberta",
                        TWO_ARMIES,
                        ONE_ARMY));

        assertEquals(
                "Attacker cannot roll more dice than attacking territory armies minus one.",
                exception.getMessage());
    }

    @Test
    public void validateNumberOfDiceZeroDefenderDiceRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateNumberOfDice(
                        "Alaska",
                        "Alberta",
                        ONE_ARMY,
                        ZERO_ARMIES));

        assertEquals("Defender must roll either 1 or 2 dice.", exception.getMessage());
    }

    @Test
    public void validateNumberOfDiceDefenderRollingMoreThanTerritoryArmiesRaisesException() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(THREE_ARMIES));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameModel.validateNumberOfDice(
                        "Alaska",
                        "Alberta",
                        ONE_ARMY,
                        TWO_ARMIES));

        assertEquals(
                "Defender cannot roll more dice than the number of armies on the defending territory.",
                exception.getMessage());
    }

    @Test
    public void executeBattleAndReturnWinnerOneVersusOneAttackerWinRemovesDefendingArmy() {
        GameModel gameModel = createGameModelWithDiceRolls(6, 3);

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alberta").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        List<String> battleResult = gameModel.executeBattleAndReturnWinner(
                "Alaska",
                "Alberta",
                ONE_ARMY,
                ONE_ARMY);

        assertEquals(TWO_ARMIES, gameModel.findTerritoryByName("Alaska").getArmyCount());
        assertEquals(ONE_ARMY, gameModel.findTerritoryByName("Alberta").getArmyCount());
        assertTrue(battleResult.contains("Attacker dice: [6]"));
        assertTrue(battleResult.contains("Defender dice: [3]"));
        assertTrue(battleResult.contains("Attacker losses: 0"));
        assertTrue(battleResult.contains("Defender losses: 1"));
        assertTrue(battleResult.contains("Attacking territory armies: 2"));
        assertTrue(battleResult.contains("Defending territory armies: 1"));
        assertTrue(battleResult.contains("Captured: false"));
    }

    @Test
    public void executeBattleAndReturnWinnerOneVersusOneDefenderWinRemovesAttackingArmy() {
        GameModel gameModel = createGameModelWithDiceRolls(2, 5);

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(TWO_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alberta").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        List<String> battleResult = gameModel.executeBattleAndReturnWinner(
                "Alaska",
                "Alberta",
                ONE_ARMY,
                ONE_ARMY);

        assertEquals(TWO_ARMIES, gameModel.findTerritoryByName("Alaska").getArmyCount());
        assertEquals(TWO_ARMIES, gameModel.findTerritoryByName("Alberta").getArmyCount());
        assertTrue(battleResult.contains("Attacker dice: [2]"));
        assertTrue(battleResult.contains("Defender dice: [5]"));
        assertTrue(battleResult.contains("Attacker losses: 1"));
        assertTrue(battleResult.contains("Defender losses: 0"));
        assertTrue(battleResult.contains("Attacking territory armies: 2"));
        assertTrue(battleResult.contains("Defending territory armies: 2"));
        assertTrue(battleResult.contains("Captured: false"));
    }

    @Test
    public void executeBattleAndReturnWinnerOneVersusOneTieRemovesAttackingArmy() {
        GameModel gameModel = createGameModelWithDiceRolls(4, 4);

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(TWO_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alberta").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        List<String> battleResult = gameModel.executeBattleAndReturnWinner(
                "Alaska",
                "Alberta",
                ONE_ARMY,
                ONE_ARMY);

        assertEquals(TWO_ARMIES, gameModel.findTerritoryByName("Alaska").getArmyCount());
        assertEquals(TWO_ARMIES, gameModel.findTerritoryByName("Alberta").getArmyCount());
        assertTrue(battleResult.contains("Attacker dice: [4]"));
        assertTrue(battleResult.contains("Defender dice: [4]"));
        assertTrue(battleResult.contains("Attacker losses: 1"));
        assertTrue(battleResult.contains("Defender losses: 0"));
        assertTrue(battleResult.contains("Attacking territory armies: 2"));
        assertTrue(battleResult.contains("Defending territory armies: 2"));
        assertTrue(battleResult.contains("Captured: false"));
    }

    @Test
    public void executeBattleAndReturnWinnerTwoVersusOneComparesOnlyHighestDice() {
        GameModel gameModel = createGameModelWithDiceRolls(1, 6, 5);

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();
        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alaska").placeArmies(createInfantryPieces(TWO_INFANTRY));
        gameModel.advanceCurrentPlayerIndex();
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.findTerritoryByName("Alberta").placeArmies(createInfantryPieces(ONE_INFANTRY));
        gameModel.setCurrentPlayerIndex(0);

        List<String> battleResult = gameModel.executeBattleAndReturnWinner(
                "Alaska",
                "Alberta",
                TWO_ARMIES,
                ONE_ARMY);

        assertEquals(THREE_ARMIES, gameModel.findTerritoryByName("Alaska").getArmyCount());
        assertEquals(ONE_ARMY, gameModel.findTerritoryByName("Alberta").getArmyCount());
        assertTrue(battleResult.contains("Attacker dice: [6, 1]"));
        assertTrue(battleResult.contains("Defender dice: [5]"));
        assertTrue(battleResult.contains("Attacker losses: 0"));
        assertTrue(battleResult.contains("Defender losses: 1"));
        assertTrue(battleResult.contains("Attacking territory armies: 3"));
        assertTrue(battleResult.contains("Defending territory armies: 1"));
        assertTrue(battleResult.contains("Captured: false"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnContinentsWithFullAustraliaAddsTwoInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("Indonesia", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("New Guinea", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Western Australia", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Eastern Australia", createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(
                THREE_PLAYER_STARTING_INFANTRY - AUSTRALIA_TERRITORY_COUNT));

        gameModel.addArmiesToCurrentPlayerBasedOnContinents();

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=2"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnContinentsWithFullSouthAmericaAddsTwoInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("Venezuela", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Peru", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Brazil", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Argentina", createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(
                THREE_PLAYER_STARTING_INFANTRY - SOUTH_AMERICA_TERRITORY_COUNT));

        gameModel.addArmiesToCurrentPlayerBasedOnContinents();

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=2"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnContinentsWithFullAfricaAddsThreeInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("North Africa", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Egypt", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("East Africa", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Congo", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("South Africa", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Madagascar", createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(
                THREE_PLAYER_STARTING_INFANTRY - AFRICA_TERRITORY_COUNT));

        gameModel.addArmiesToCurrentPlayerBasedOnContinents();

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=3"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnContinentsWithFullEuropeAddsFiveInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("Iceland", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Scandinavia", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Ukraine", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Great Britain", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Northern Europe", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Western Europe", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Southern Europe", createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(
                THREE_PLAYER_STARTING_INFANTRY - EUROPE_TERRITORY_COUNT));

        gameModel.addArmiesToCurrentPlayerBasedOnContinents();

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=5"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnContinentsWithFullNorthAmericaAddsFiveInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Northwest Territory", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Greenland", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Alberta", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Ontario", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Quebec", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Western United States", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Eastern United States", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Central America", createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(
                THREE_PLAYER_STARTING_INFANTRY - NORTH_AMERICA_TERRITORY_COUNT));

        gameModel.addArmiesToCurrentPlayerBasedOnContinents();

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=5"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void addArmiesToCurrentPlayerBasedOnContinentsWithFullAsiaAddsSevenInfantry() {
        GameModel gameModel = createGameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.initializeContinentsAndTerritories();

        gameModel.claimTerritoryDuringSetup("Ural", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Siberia", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Yakutsk", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Kamchatka", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Irkutsk", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Mongolia", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Japan", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Afghanistan", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("China", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Middle East", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("India", createInfantryPieces(ONE_INFANTRY));
        gameModel.claimTerritoryDuringSetup("Siam", createInfantryPieces(ONE_INFANTRY));
        player.removeArmies(createInfantryPieces(
                THREE_PLAYER_STARTING_INFANTRY - ASIA_TERRITORY_COUNT));

        gameModel.addArmiesToCurrentPlayerBasedOnContinents();

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=7"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }
}
