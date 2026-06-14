package code.model;

import static org.easymock.EasyMock.createMock;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the HumanPlayer class.
 */
public final class HumanPlayerTest {

    private static final int MIN_SETUP_INFANTRY = 20;

    private static final int MAX_SETUP_INFANTRY = 35;

    private static final int STARTING_INFANTRY = 20;

    private static final int ZERO_INFANTRY = 0;

    private static final int ONE_INFANTRY = 1;

    private static final int TWENTY_INFANTRY = 20;

    private static final int TWENTY_CARD_TRADE_IN_ARMIES = 20;

    private static final int FIFTY_FIVE_CARD_TRADE_IN_ARMIES = 55;

    private static final int ZERO_ARMIES = 0;

    private static final int ONE_ARMY = 1;

    private static final int THREE_ARMIES = 3;

    private static final int FOUR_ARMIES = 4;

    private static final int FOUR_CARD_TRADE_IN_ARMIES = 4;

    private static final int FIVE_ARMIES = 5;

    private static final int SIX_ARMIES = 6;

    private static final int EIGHT_ARMIES = 8;

    private static final int TEN_ARMIES = 10;

    private static final int TWELVE_ARMIES = 12;

    private static final int FIFTEEN_CARD_TRADE_IN_ARMIES = 15;

    private static final int FIFTEEN_ARMIES = 15;

    private static final int THIRTEEN_ARMIES = 13;

    private static final int ONE_ARTILLERY = 10;

    private static final Integer ONE_CAVALRY = 5;

    @Test
    public void constructorMinimumSetupInfantryCreatesPlayer() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);
        String availableArmies = player.getAvailableArmies();

        assertEquals("Player 1", player.getName());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(MIN_SETUP_INFANTRY)));
    }

    @Test
    public void constructorMaximumSetupInfantryCreatesPlayer() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.BLUE,
                MAX_SETUP_INFANTRY);
        String availableArmies = player.getAvailableArmies();

        assertEquals("Player 1", player.getName());
        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(MAX_SETUP_INFANTRY)));
    }

    @Test
    public void getNameRegisteredPlayerReturnsRegisteredName() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);

        assertEquals("Player 1", player.getName());
    }

    @Test
    public void getAvailableArmiesMinimumSetupInfantryReturnsAvailableArmies() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(MIN_SETUP_INFANTRY)));
    }

    @Test
    public void getAvailableArmiesMaximumSetupInfantryReturnsAvailableArmies() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.BLUE,
                MAX_SETUP_INFANTRY);
        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains(String.valueOf(MAX_SETUP_INFANTRY)));
    }

    @Test
    public void hasAvailableArmiesSetupArmyAssignmentReturnsTrue() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                MIN_SETUP_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void addTerritoryAddsFirstClaimedTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, MIN_SETUP_INFANTRY);
        Territory territory = createMock(Territory.class);

        player.addTerritory(territory);

        assertEquals(1, player.getTerritoryCount());
        assertTrue(player.ownsTerritory(territory));
    }

    @Test
    public void addTerritoryAddsAnotherClaimedTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory alaska = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);

        player.addTerritory(alaska);
        player.addTerritory(alberta);

        assertEquals(2, player.getTerritoryCount());
        assertTrue(player.ownsTerritory(alaska));
        assertTrue(player.ownsTerritory(alberta));
    }

    @Test
    public void ownsTerritoryReturnsFalseForUnownedTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory alaska = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);

        player.addTerritory(alaska);

        assertTrue(player.ownsTerritory(alaska));
        assertFalse(player.ownsTerritory(alberta));
    }

    @Test
    public void getTerritoryCountReturnsZeroBeforeTerritoryClaimed() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);

        assertEquals(0, player.getTerritoryCount());
    }



    @Test
    public void hasAvailableArmiesReturnsTrueWhenRequiredInfantryIsAvailable() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void addArmiesAddsOneInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        HashMap<ArmyType, Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(ArmyType.INFANTRY, ONE_INFANTRY);

        player.addArmies(armiesToAdd);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("1"));
    }

    @Test
    public void addArmiesAddsMultipleInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        HashMap<ArmyType, Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(ArmyType.INFANTRY, TWENTY_INFANTRY);

        player.addArmies(armiesToAdd);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("20"));
    }

    @Test
    public void removeArmiesRemovesOneInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, TWENTY_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, ONE_INFANTRY);

        player.removeArmies(armiesToRemove);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("19"));
    }

    @Test
    public void removeArmiesRemovesLastInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ONE_INFANTRY);
        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.INFANTRY, ONE_INFANTRY);

        player.removeArmies(armiesToRemove);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("0"));
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenRequiredInfantryEqualsAvailableInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ONE_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsFalseWhenRequiredInfantryGreaterThanAvailableInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        HashMap<ArmyType, Integer> requiredArmies = new HashMap<>();
        requiredArmies.put(ArmyType.INFANTRY, ONE_INFANTRY);

        assertFalse(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void getAvailableArmiesReturnsDisplayStringWhenInfantryAvailable() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, TWENTY_INFANTRY);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("20"));
    }

    @Test
    public void getAvailableArmiesReturnsDisplayStringWhenNoInfantryAvailable() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY"));
        assertTrue(availableArmies.contains("0"));
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

    private void addTerritoriesToPlayer(final HumanPlayer player, final int territoryCount) {
        for (int index = 0; index < territoryCount; index++) {
            player.addTerritory(createMock(Territory.class));
        }
    }

    private RiskCard createCard(final CardType cardType) {
        if (cardType == CardType.WILD) {
            return new RiskCard(null, CardType.WILD, true);
        }

        return new RiskCard(createMock(Territory.class), cardType, false);
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenExactInfantryCountAvailable() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ONE_ARMY);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenInfantryCanConvertToCavalryAndArtillery() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                FIFTEEN_ARMIES);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ZERO_ARMIES, ONE_ARMY, ONE_ARMY);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsTrueWhenArtilleryCanConvertToCavalry() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ZERO_ARMIES);
        HashMap<ArmyType, Integer> availableArmies =
                createArmies(ZERO_ARMIES, ZERO_ARMIES, ONE_ARMY);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);

        player.addArmies(availableArmies);

        assertTrue(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsFalseWhenRequiredValueGreaterThanAvailableValue() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                FOUR_ARMIES);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);

        assertFalse(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void hasAvailableArmiesReturnsFalseWhenPlayerHasZeroAvailableArmyValue() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ZERO_ARMIES);
        HashMap<ArmyType, Integer> requiredArmies =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        assertFalse(player.hasAvailableArmies(requiredArmies));
    }

    @Test
    public void removeArmiesRemovesOneInfantryFromOneAvailableInfantry() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ONE_ARMY);
        HashMap<ArmyType, Integer> armiesToRemove =
                createArmies(ONE_ARMY, ZERO_ARMIES, ZERO_ARMIES);

        player.removeArmies(armiesToRemove);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=0"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void removeArmiesRemovesCavalryUsingInfantryValue() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                FIVE_ARMIES);
        HashMap<ArmyType, Integer> armiesToRemove =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);

        player.removeArmies(armiesToRemove);

        String availableArmies = player.getAvailableArmies();

        assertTrue(availableArmies.contains("INFANTRY=0"));
        assertTrue(availableArmies.contains("CAVALRY=0"));
        assertTrue(availableArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void removeArmiesRemovesCavalryFromArtilleryAndMakesChange() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ZERO_ARMIES);
        HashMap<ArmyType, Integer> availableArmies =
                createArmies(ZERO_ARMIES, ZERO_ARMIES, ONE_ARMY);
        HashMap<ArmyType, Integer> armiesToRemove =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);

        player.addArmies(availableArmies);

        player.removeArmies(armiesToRemove);

        String remainingArmies = player.getAvailableArmies();

        assertTrue(remainingArmies.contains("INFANTRY=0"));
        assertTrue(remainingArmies.contains("CAVALRY=1"));
        assertTrue(remainingArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void removeArmiesRemovesInfantryFromCavalryAndMakesChange() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                ZERO_ARMIES);
        HashMap<ArmyType, Integer> availableArmies =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);
        HashMap<ArmyType, Integer> armiesToRemove =
                createArmies(THREE_ARMIES, ZERO_ARMIES, ZERO_ARMIES);

        player.addArmies(availableArmies);

        player.removeArmies(armiesToRemove);

        String remainingArmies = player.getAvailableArmies();

        assertTrue(remainingArmies.contains("INFANTRY=2"));
        assertTrue(remainingArmies.contains("CAVALRY=0"));
        assertTrue(remainingArmies.contains("ARTILLERY=0"));
    }

    @Test
    public void removeArmiesBreaksArtilleryWhenRemovingCavalry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        HashMap<ArmyType, Integer> availableArmies = new HashMap<>();
        availableArmies.put(ArmyType.ARTILLERY, ONE_ARTILLERY);
        player.addArmies(availableArmies);

        HashMap<ArmyType, Integer> armiesToRemove = new HashMap<>();
        armiesToRemove.put(ArmyType.CAVALRY, ONE_CAVALRY);

        player.removeArmies(armiesToRemove);

        String remainingArmies = player.getAvailableArmies();

        assertTrue(remainingArmies.contains("CAVALRY"));
        assertTrue(remainingArmies.contains("1"));
        assertTrue(remainingArmies.contains("ARTILLERY"));
        assertTrue(remainingArmies.contains("0"));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithZeroTerritoriesRaisesException() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                player::addArmiesToAvailableBasedOnTerritories);

        assertEquals(
                "Player cannot own 0 territories and play a turn because they have been eliminated.",
                exception.getMessage());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithOneTerritoryAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        Territory territory = createMock(Territory.class);

        player.addTerritory(territory);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithTwoTerritoriesAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        Territory firstTerritory = createMock(Territory.class);
        Territory secondTerritory = createMock(Territory.class);

        player.addTerritory(firstTerritory);
        player.addTerritory(secondTerritory);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithEightTerritoriesAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, 8);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithNineTerritoriesAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, 9);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithTenTerritoriesAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, 10);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithElevenTerritoriesAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, 11);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithTwelveTerritoriesAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, 12);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + FOUR_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithFortyOneTerritoriesAddsThirteenInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, 41);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THIRTEEN_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithFortyTwoTerritoriesRaisesException() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, 42);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                player::addArmiesToAvailableBasedOnTerritories);

        assertEquals(
                "Player cannot own 42 territories and play a turn because they should have already won.",
                exception.getMessage());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithFortyThreeTerritoriesRaisesException() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, 43);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                player::addArmiesToAvailableBasedOnTerritories);

        assertEquals(
                "Player cannot own 43 territories because there are only 42 territories on the board.",
                exception.getMessage());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFewerThanThreeSelectedCardsReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2), new Deck(), 0);

        assertFalse(traded);
        assertEquals(3, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithMoreThanThreeSelectedCardsReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));
        player.addCard(createCard(CardType.INFANTRY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3, 4), new Deck(), 0);

        assertFalse(traded);
        assertEquals(4, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSelectedIndexBelowOneReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(0, 1, 2), new Deck(), 0);

        assertFalse(traded);
        assertEquals(3, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSelectedIndexAboveHandSizeReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 4), new Deck(), 0);

        assertFalse(traded);
        assertEquals(3, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithDuplicateSelectedIndicesReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 1, 2), new Deck(), 0);

        assertFalse(traded);
        assertEquals(3, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThreeInfantryCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.INFANTRY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertTrue(traded);
        assertEquals(0, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThreeCavalryCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.CAVALRY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertTrue(traded);
        assertEquals(0, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThreeArtilleryCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.ARTILLERY));
        player.addCard(createCard(CardType.ARTILLERY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertTrue(traded);
        assertEquals(0, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithOneOfEachCardTypeAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertTrue(traded);
        assertEquals(0, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithTwoMatchingAndOneDifferentCardReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertFalse(traded);
        assertEquals(3, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithOneWildAndTwoMatchingCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.INFANTRY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertTrue(traded);
        assertEquals(0, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithOneWildAndTwoDifferentCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertTrue(traded);
        assertEquals(0, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithTwoWildCardsReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.INFANTRY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertFalse(traded);
        assertEquals(3, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFirstTradeInAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 0);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSecondTradeInAddsSixInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 1);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + SIX_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThirdTradeInAddsEightInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 2);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + EIGHT_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFourthTradeInAddsTenInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 3);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + TEN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFifthTradeInAddsTwelveInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 4);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + TWELVE_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSixthTradeInAddsFifteenInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 5);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FIFTEEN_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSeventhTradeInAddsTwentyInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 6);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + TWENTY_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFourteenthTradeInAddsFiftyFiveInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 13);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FIFTY_FIVE_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFifteenthTradeInRaisesException() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.tradeCardsAndAddArmies(List.of(1, 2, 3), new Deck(), 14));

        assertEquals(
                "Cannot trade cards after 14 sets because a 44-card deck supports at most 14 traded sets.",
                exception.getMessage());
        assertEquals(3, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

}
