package code.model;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

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

    @Test
    public void deckHasFortyFourCardsAfterBoardInitialization() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(DECK_CARD_COUNT, gameModel.getDeckSize());
    }

    @Test
    public void deckIsNotEmptyAfterBoardInitialization() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertFalse(gameModel.isDeckEmpty());
    }

    @Test
    public void setPlayerCountBelowMinimumPlayerCountReturnsFalse() {
        GameModel gameModel = new GameModel();

        assertFalse(gameModel.setPlayerCount(BELOW_MIN_PLAYER_COUNT));
    }

    @Test
    public void setPlayerCountAboveMaximumPlayerCountReturnsFalse() {
        GameModel gameModel = new GameModel();

        assertFalse(gameModel.setPlayerCount(ABOVE_MAX_PLAYER_COUNT));
    }

    @Test
    public void addPlayerThreePlayerGameWithNoRegisteredPlayersReturnsPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        String availableArmies = player.getAvailableArmies();

        assertEquals("Player 1", player.getName());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(THREE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addPlayerFourPlayerGameWithNoRegisteredPlayersReturnsPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(FOUR_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.BLUE);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(FOUR_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addPlayerFivePlayerGameWithNoRegisteredPlayersReturnsPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(FIVE_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.GREEN);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(FIVE_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addPlayerSixPlayerGameWithNoRegisteredPlayersReturnsPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MAX_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.YELLOW);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(SIX_PLAYER_STARTING_INFANTRY)));
    }

    @Test
    public void addPlayerPlayerListAlreadyFullReturnsNullPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        Player extraPlayer = gameModel.addPlayer("Player 4", PlayerColor.YELLOW);

        assertInstanceOf(NullPlayer.class, extraPlayer);
    }

    @Test
    public void setCurrentPlayerIndexFirstPlayerIndexSetsCurrentPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(0);

        assertEquals("Player 1", gameModel.getCurrentPlayerName());
    }

    @Test
    public void setCurrentPlayerIndexLastPlayerIndexSetsCurrentPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(2);

        assertEquals("Player 3", gameModel.getCurrentPlayerName());
    }

    @Test
    public void setCurrentPlayerIndexIndexBelowRangeDoesNotChangeCurrentPlayer() {
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(0);
        assertEquals("Player 1", gameModel.getCurrentPlayerName());
    }

    @Test
    public void getCurrentPlayerNameSelectedLastPlayerReturnsLastPlayerName() {
        GameModel gameModel = new GameModel();

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

    @Test
    public void claimTerritoryDuringSetupUnclaimedTerritoryWithOneInfantryReturnsTrue() {
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

        boolean advanced = gameModel.advanceCurrentPlayerIndex();

        assertFalse(advanced);
    }

    @Test
    public void advanceCurrentPlayerIndexLastPlayerWrapsToFirstPlayer() {
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        assertEquals("Player 1", gameModel.getCurrentPlayerName());
    }

    @Test
    public void getCurrentPlayerNameMiddlePlayerIndexReturnsMiddlePlayerName() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(1);

        assertEquals("Player 2", gameModel.getCurrentPlayerName());
    }

    @Test
    public void getUnclaimedTerritoriesByContinentReturnsAllTerritoriesWhenNoneClaimed() {
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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
        GameModel gameModel = new GameModel();

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

    @Test
    public void hasCurrentPlayerAvailableArmies_CurrentPlayerHasZeroArmiesRemaining_ReturnsFalse() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        player.removeArmies(createInfantryPieces(THREE_PLAYER_STARTING_INFANTRY));

        assertFalse(gameModel.hasCurrentPlayerAvailableArmies());
    }

    @Test
    public void hasCurrentPlayerAvailableArmies_CurrentPlayerHasOneArmyRemaining_ReturnsTrue() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        Player player = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        player.removeArmies(createInfantryPieces(THIRTY_FOUR_INFANTRY));

        assertTrue(gameModel.hasCurrentPlayerAvailableArmies());
    }

    @Test
    public void hasCurrentPlayerAvailableArmies_CurrentPlayerHasMultipleArmiesRemaining_ReturnsTrue() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        assertTrue(gameModel.hasCurrentPlayerAvailableArmies());
    }

    @Test
    public void addArmiesDuringSetup_OwnedTerritoryWithOneInfantry_ReturnsTrue() {
        GameModel gameModel = new GameModel();

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

}
