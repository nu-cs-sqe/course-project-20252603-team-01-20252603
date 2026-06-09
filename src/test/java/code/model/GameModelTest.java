package code.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;
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

    private static final int TOTAL_PLAYER_COLORS = PlayerColor.values().length - 1;

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
    public void setPlayerCountMinimumPlayerCountReturnsTrue() {
        GameModel gameModel = new GameModel();

        assertTrue(gameModel.setPlayerCount(MIN_PLAYER_COUNT));
        assertEquals(MIN_PLAYER_COUNT, gameModel.getPlayerCount());
    }

    @Test
    public void setPlayerCountMaximumPlayerCountReturnsTrue() {
        GameModel gameModel = new GameModel();

        assertTrue(gameModel.setPlayerCount(MAX_PLAYER_COUNT));
        assertEquals(MAX_PLAYER_COUNT, gameModel.getPlayerCount());
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
        assertEquals(PlayerColor.RED, player.getColor());
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
    public void addPlayerDuplicatePlayerColorReturnsNullPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        Player duplicatePlayer = gameModel.addPlayer("Player 2", PlayerColor.RED);

        assertInstanceOf(NullPlayer.class, duplicatePlayer);
        assertEquals(1, gameModel.getPlayers().size());
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
        assertEquals(MIN_PLAYER_COUNT, gameModel.getPlayers().size());
    }

    @Test
    public void showAvailableColorsNoRegisteredPlayersReturnsAllColors() {
        GameModel gameModel = new GameModel();
        List<PlayerColor> availableColors = gameModel.showAvailableColors();

        assertEquals(TOTAL_PLAYER_COLORS, availableColors.size());
        assertTrue(availableColors.contains(PlayerColor.RED));
        assertTrue(availableColors.contains(PlayerColor.BLUE));
        assertTrue(availableColors.contains(PlayerColor.GREEN));
        assertTrue(availableColors.contains(PlayerColor.YELLOW));
        assertTrue(availableColors.contains(PlayerColor.BLACK));
        assertTrue(availableColors.contains(PlayerColor.PURPLE));
    }

    @Test
    public void showAvailableColorsRedAlreadyChosenReturnsRemainingColors() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        List<PlayerColor> availableColors = gameModel.showAvailableColors();

        assertEquals(TOTAL_PLAYER_COLORS - 1, availableColors.size());
        assertFalse(availableColors.contains(PlayerColor.RED));
    }

    @Test
    public void showAvailableColorsFiveColorsAlreadyChosenReturnsOneColor() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MAX_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.addPlayer("Player 4", PlayerColor.YELLOW);
        gameModel.addPlayer("Player 5", PlayerColor.BLACK);
        List<PlayerColor> availableColors = gameModel.showAvailableColors();

        assertEquals(TOTAL_PLAYER_COLORS - (MAX_PLAYER_COUNT - 1), availableColors.size());
        assertTrue(availableColors.contains(PlayerColor.PURPLE));
    }

    @Test
    public void setCurrentPlayerIndexFirstPlayerIndexSetsCurrentPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        final Player firstPlayer = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(0);

        assertEquals(firstPlayer, gameModel.getCurrentPlayer());
    }

    @Test
    public void setCurrentPlayerIndexLastPlayerIndexSetsCurrentPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        final Player lastPlayer = gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(2);

        assertEquals(lastPlayer, gameModel.getCurrentPlayer());
    }

    @Test
    public void setCurrentPlayerIndexIndexBelowRangeDoesNotChangeCurrentPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        final Player secondPlayer = gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(1);
        gameModel.setCurrentPlayerIndex(-1);

        assertEquals(secondPlayer, gameModel.getCurrentPlayer());
    }

    @Test
    public void setCurrentPlayerIndexIndexAboveRangeDoesNotChangeCurrentPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        final Player secondPlayer = gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(1);
        gameModel.setCurrentPlayerIndex(MIN_PLAYER_COUNT);
        assertEquals(secondPlayer, gameModel.getCurrentPlayer());
    }

    @Test
    public void getCurrentPlayerFirstPlayerIndexReturnsFirstPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        final Player firstPlayer = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(0);
        assertEquals(firstPlayer, gameModel.getCurrentPlayer());
    }

    @Test
    public void getCurrentPlayerLastPlayerIndexReturnsLastPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        final Player lastPlayer = gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(2);
        assertEquals(lastPlayer, gameModel.getCurrentPlayer());
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
        Player firstPlayer = gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(2);

        boolean advanced = gameModel.advanceCurrentPlayerIndex();

        assertTrue(advanced);
        assertEquals(firstPlayer, gameModel.getCurrentPlayer());
    }

    @Test
    public void advanceCurrentPlayerIndexMiddlePlayerAdvancesToNextPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        Player thirdPlayer = gameModel.addPlayer("Player 3", PlayerColor.GREEN);
        gameModel.setCurrentPlayerIndex(1);

        boolean advanced = gameModel.advanceCurrentPlayerIndex();

        assertTrue(advanced);
        assertEquals(thirdPlayer, gameModel.getCurrentPlayer());
    }

    @Test
    public void advanceCurrentPlayerIndexFirstPlayerAdvancesToSecondPlayer() {
        GameModel gameModel = new GameModel();

        gameModel.setPlayerCount(MIN_PLAYER_COUNT);
        gameModel.addPlayer("Player 1", PlayerColor.RED);
        Player secondPlayer = gameModel.addPlayer("Player 2", PlayerColor.BLUE);
        gameModel.addPlayer("Player 3", PlayerColor.GREEN);

        boolean advanced = gameModel.advanceCurrentPlayerIndex();

        assertTrue(advanced);
        assertEquals(secondPlayer, gameModel.getCurrentPlayer());
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


}
