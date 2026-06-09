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

    private static final int CONTINENT_COUNT = 6;

    private static final int TERRITORY_COUNT = 42;

    private static final int NORTH_AMERICA_TERRITORY_COUNT = 9;

    private static final int SOUTH_AMERICA_TERRITORY_COUNT = 4;

    private static final int EUROPE_TERRITORY_COUNT = 7;

    private static final int AFRICA_TERRITORY_COUNT = 6;

    private static final int ASIA_TERRITORY_COUNT = 12;

    private static final int AUSTRALIA_TERRITORY_COUNT = 4;

    private static final int NORTH_AMERICA_BONUS = 5;

    private static final int SOUTH_AMERICA_BONUS = 2;

    private static final int EUROPE_BONUS = 5;

    private static final int AFRICA_BONUS = 3;

    private static final int ASIA_BONUS = 7;

    private static final int AUSTRALIA_BONUS = 2;

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

    @Test
    public void gameModelConstructsWithEmptyContinents() {
        GameModel gameModel = new GameModel();

        assertTrue(gameModel.getContinents().isEmpty());
    }

    @Test
    public void initializeCreatesSixContinents() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(CONTINENT_COUNT, gameModel.getContinents().size());
    }

    @Test
    public void initializeCreatesFortyTwoTerritories() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        int territoryCount = gameModel.getContinents()
                .stream()
                .mapToInt(continent -> continent.getTerritories().size())
                .sum();

        assertEquals(TERRITORY_COUNT, territoryCount);
    }

    @Test
    public void allTerritoriesStartWithZeroArmies() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean allHaveZeroArmies = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .allMatch(territory -> territory.getArmyCount() == 0);

        assertTrue(allHaveZeroArmies);
    }

    private Continent findContinent(
            final GameModel gameModel,
            final String continentName) {
        return gameModel.getContinents()
                .stream()
                .filter(continent -> continent.getName().equals(continentName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Continent not found: " + continentName));
    }

    @Test
    public void eachContinentHasCorrectTerritoryCount() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(
                NORTH_AMERICA_TERRITORY_COUNT,
                findContinent(gameModel, "North America").getTerritories().size());
        assertEquals(
                SOUTH_AMERICA_TERRITORY_COUNT,
                findContinent(gameModel, "South America").getTerritories().size());
        assertEquals(
                EUROPE_TERRITORY_COUNT,
                findContinent(gameModel, "Europe").getTerritories().size());
        assertEquals(
                AFRICA_TERRITORY_COUNT,
                findContinent(gameModel, "Africa").getTerritories().size());
        assertEquals(
                ASIA_TERRITORY_COUNT,
                findContinent(gameModel, "Asia").getTerritories().size());
        assertEquals(
                AUSTRALIA_TERRITORY_COUNT,
                findContinent(gameModel, "Australia").getTerritories().size());
    }

    @Test
    public void eachContinentHasCorrectBonusArmyValue() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        assertEquals(NORTH_AMERICA_BONUS,
                findContinent(gameModel, "North America").getBonusArmies());
        assertEquals(SOUTH_AMERICA_BONUS,
                findContinent(gameModel, "South America").getBonusArmies());
        assertEquals(EUROPE_BONUS,
                findContinent(gameModel, "Europe").getBonusArmies());
        assertEquals(AFRICA_BONUS,
                findContinent(gameModel, "Africa").getBonusArmies());
        assertEquals(ASIA_BONUS,
                findContinent(gameModel, "Asia").getBonusArmies());
        assertEquals(AUSTRALIA_BONUS,
                findContinent(gameModel, "Australia").getBonusArmies());
    }

    @Test
    public void everyTerritoryBelongsToExactlyOneContinent() {
        GameModel gameModel = new GameModel();
        Set<Territory> territories = new HashSet<>();

        gameModel.initializeContinentsAndTerritories();

        gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .forEach(territories::add);

        assertEquals(TERRITORY_COUNT, territories.size());
    }

    private Territory findTerritory(
            final GameModel gameModel,
            final String territoryName) {
        return gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .filter(territory -> territory.getName().equals(territoryName))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Territory not found: " + territoryName));
    }

    private boolean hasReciprocalAdjacency(final Territory territory) {
        return territory.getAdjacentTerritories()
                .stream()
                .allMatch(adjacentTerritory -> adjacentTerritory
                        .getAdjacentTerritories()
                        .contains(territory));
    }

    @Test
    public void everyTerritoryHasAtLeastOneAdjacentTerritory() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean allHaveAdjacency = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .noneMatch(territory -> territory.getAdjacentTerritories().isEmpty());

        assertTrue(allHaveAdjacency);
    }

    @Test
    public void adjacencyIsReciprocalForAllTerritories() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean allReciprocal = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .allMatch(this::hasReciprocalAdjacency);

        assertTrue(allReciprocal);
    }

    @Test
    public void alaskaIsAdjacentToKamchatka() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        Territory alaska = findTerritory(gameModel, "Alaska");
        Territory kamchatka = findTerritory(gameModel, "Kamchatka");

        assertTrue(alaska.getAdjacentTerritories().contains(kamchatka));
        assertTrue(kamchatka.getAdjacentTerritories().contains(alaska));
    }

    @Test
    public void noTerritoryIsAdjacentToItself() {
        GameModel gameModel = new GameModel();

        gameModel.initializeContinentsAndTerritories();

        boolean noSelfAdjacency = gameModel.getContinents()
                .stream()
                .flatMap(continent -> continent.getTerritories().stream())
                .noneMatch(territory -> territory.getAdjacentTerritories()
                        .contains(territory));

        assertTrue(noSelfAdjacency);
    }

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
        Player player = createMock(Player.class);
        Territory territory = createMock(Territory.class);
        HashMap<ArmyType, Integer> pieces = createInfantryPieces(ONE_INFANTRY);

        expect(territory.isUnclaimed()).andReturn(true);
        expect(player.hasAvailableArmies(pieces)).andReturn(true);

        territory.setOwner(player);
        expectLastCall().once();

        expect(territory.placeArmies(pieces)).andReturn(true);

        player.addTerritory(territory);
        expectLastCall().once();

        player.removeArmies(pieces);
        expectLastCall().once();

        replay(player, territory);

        boolean claimed = gameModel.claimTerritoryDuringSetup(player, territory, pieces);

        assertTrue(claimed);
        verify(player, territory);
    }

    @Test
    public void claimTerritoryDuringSetupAlreadyClaimedTerritoryReturnsFalse() {
        GameModel gameModel = new GameModel();
        Player player = createMock(Player.class);
        Territory territory = createMock(Territory.class);
        HashMap<ArmyType, Integer> pieces = createInfantryPieces(ONE_INFANTRY);

        expect(territory.isUnclaimed()).andReturn(false);

        replay(player, territory);

        boolean claimed = gameModel.claimTerritoryDuringSetup(player, territory, pieces);

        assertFalse(claimed);
        verify(player, territory);
    }

    @Test
    public void claimTerritoryDuringSetupZeroInfantryReturnsFalse() {
        GameModel gameModel = new GameModel();
        Player player = createMock(Player.class);
        Territory territory = createMock(Territory.class);
        HashMap<ArmyType, Integer> pieces = createInfantryPieces(ZERO_INFANTRY);

        expect(territory.isUnclaimed()).andReturn(true);

        replay(player, territory);

        boolean claimed = gameModel.claimTerritoryDuringSetup(player, territory, pieces);

        assertFalse(claimed);
        verify(player, territory);
    }

    @Test
    public void claimTerritoryDuringSetupMoreThanOneInfantryReturnsFalse() {
        GameModel gameModel = new GameModel();
        Player player = createMock(Player.class);
        Territory territory = createMock(Territory.class);
        HashMap<ArmyType, Integer> pieces = createInfantryPieces(TWO_INFANTRY);

        expect(territory.isUnclaimed()).andReturn(true);

        replay(player, territory);

        boolean claimed = gameModel.claimTerritoryDuringSetup(player, territory, pieces);

        assertFalse(claimed);
        verify(player, territory);
    }
}
