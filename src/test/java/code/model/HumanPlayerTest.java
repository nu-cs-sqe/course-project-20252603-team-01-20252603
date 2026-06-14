package code.model;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
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

    private static final int ONE_ARTILLERY = 1;

    private static final int ONE_CAVALRY = 1;

    private static final int EIGHT_TERRITORIES = 8;

    private static final int NINE_TERRITORIES = 9;

    private static final int TEN_TERRITORIES = 10;

    private static final int ELEVEN_TERRITORIES = 11;

    private static final int TWELVE_TERRITORIES = 12;

    private static final int FORTY_ONE_TERRITORIES = 41;

    private static final int FORTY_TWO_TERRITORIES = 42;

    private static final int FORTY_THREE_TERRITORIES = 43;

    private static final int FIRST_CARD_INDEX = 1;

    private static final int SECOND_CARD_INDEX = 2;

    private static final int THIRD_CARD_INDEX = 3;

    private static final int FOURTH_CARD_INDEX = 4;

    private static final int ZERO_TRADE_SETS = 0;

    private static final int ONE_TRADE_SET = 1;

    private static final int TWO_TRADE_SETS = 2;

    private static final int THREE_TRADE_SETS = 3;

    private static final int FOUR_TRADE_SETS = 4;

    private static final int FIVE_TRADE_SETS = 5;

    private static final int SIX_TRADE_SETS = 6;

    private static final int THIRTEEN_TRADE_SETS = 13;

    private static final int FOURTEEN_TRADE_SETS = 14;

    private static final int ZERO_CARDS = 0;

    private static final int THREE_CARDS = 3;

    private static final int FOUR_CARDS = 4;

    private static final int FIRST_CARD_TYPE_CALLS = 7;

    private static final int SECOND_CARD_TYPE_CALLS = 5;

    private static final int THIRD_CARD_TYPE_CALLS_BEFORE_WILD = 3;

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

        assertEquals(ONE_ARMY, player.getTerritoryCount());
        assertTrue(player.ownsTerritory(territory));
    }

    @Test
    public void addTerritoryAddsAnotherClaimedTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory alaska = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);

        player.addTerritory(alaska);
        player.addTerritory(alberta);

        assertEquals(SECOND_CARD_INDEX, player.getTerritoryCount());
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
    public void removeTerritoryWithOnlyOwnedTerritoryRemovesTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory alaska = createMock(Territory.class);

        player.addTerritory(alaska);
        player.removeTerritory(alaska);

        assertEquals(ZERO_ARMIES, player.getTerritoryCount());
        assertFalse(player.ownsTerritory(alaska));
    }

    @Test
    public void removeTerritoryWithAnotherOwnedTerritoryLeavesOtherTerritoryOwned() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory alaska = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);

        player.addTerritory(alaska);
        player.addTerritory(alberta);
        player.removeTerritory(alaska);

        assertEquals(ONE_ARMY, player.getTerritoryCount());
        assertFalse(player.ownsTerritory(alaska));
        assertTrue(player.ownsTerritory(alberta));
    }

    @Test
    public void removeTerritoryNotOwnedByPlayerLeavesTerritoriesUnchanged() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory alaska = createMock(Territory.class);
        Territory alberta = createMock(Territory.class);

        player.addTerritory(alaska);
        player.removeTerritory(alberta);

        assertEquals(ONE_ARMY, player.getTerritoryCount());
        assertTrue(player.ownsTerritory(alaska));
        assertFalse(player.ownsTerritory(alberta));
    }

    @Test
    public void getTerritoryCountReturnsZeroBeforeTerritoryClaimed() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);

        assertEquals(ZERO_ARMIES, player.getTerritoryCount());
    }

    @Test
    public void isEliminatedReturnsFalseForNewHumanPlayer() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);

        assertFalse(player.isEliminated());
    }

    @Test
    public void markEliminatedMakesHumanPlayerEliminated() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);

        player.markEliminated();

        assertTrue(player.isEliminated());
    }

    @Test
    public void markEliminatedOnAlreadyEliminatedHumanPlayerKeepsPlayerEliminated() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);

        player.markEliminated();
        player.markEliminated();

        assertTrue(player.isEliminated());
    }

    @Test
    public void addCardToEmptyHandAddsFirstRiskCard() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        RiskCard card = createCard(CardType.INFANTRY);

        player.addCard(card);

        assertEquals(ONE_ARMY, player.getCardCount());
        assertTrue(player.getAvailableCards().contains(card));
    }

    @Test
    public void addCardToNonEmptyHandAddsAnotherRiskCard() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        RiskCard firstCard = createCard(CardType.INFANTRY);
        RiskCard secondCard = createCard(CardType.CAVALRY);

        player.addCard(firstCard);
        player.addCard(secondCard);

        assertEquals(SECOND_CARD_INDEX, player.getCardCount());
        assertTrue(player.getAvailableCards().contains(firstCard));
        assertTrue(player.getAvailableCards().contains(secondCard));
    }

    @Test
    public void addCardsWithEmptyListLeavesHandEmpty() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);

        player.addCards(List.of());

        assertEquals(ZERO_CARDS, player.getCardCount());
    }

    @Test
    public void addCardsWithOneCardAddsCardToEmptyHand() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        RiskCard card = createCard(CardType.INFANTRY);

        player.addCards(List.of(card));

        assertEquals(ONE_ARMY, player.getCardCount());
        assertTrue(player.getAvailableCards().contains(card));
    }

    @Test
    public void addCardsWithMultipleCardsAddsCardsToNonEmptyHand() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        RiskCard firstCard = createCard(CardType.INFANTRY);
        RiskCard secondCard = createCard(CardType.CAVALRY);
        RiskCard thirdCard = createCard(CardType.ARTILLERY);

        player.addCard(firstCard);
        player.addCards(List.of(secondCard, thirdCard));

        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableCards().contains(firstCard));
        assertTrue(player.getAvailableCards().contains(secondCard));
        assertTrue(player.getAvailableCards().contains(thirdCard));
    }

    @Test
    public void removeAllCardsFromEmptyHandReturnsEmptyList() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);

        List<RiskCard> removedCards = player.removeAllCards();

        assertTrue(removedCards.isEmpty());
        assertEquals(ZERO_CARDS, player.getCardCount());
    }

    @Test
    public void removeAllCardsFromOneCardHandReturnsCardAndEmptiesHand() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        RiskCard card = createCard(CardType.INFANTRY);

        player.addCard(card);
        List<RiskCard> removedCards = player.removeAllCards();

        assertEquals(ONE_ARMY, removedCards.size());
        assertTrue(removedCards.contains(card));
        assertEquals(ZERO_CARDS, player.getCardCount());
    }

    @Test
    public void removeAllCardsFromMultiCardHandReturnsAllCardsAndEmptiesHand() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        RiskCard firstCard = createCard(CardType.INFANTRY);
        RiskCard secondCard = createCard(CardType.CAVALRY);
        RiskCard thirdCard = createCard(CardType.ARTILLERY);

        player.addCards(List.of(firstCard, secondCard, thirdCard));
        List<RiskCard> removedCards = player.removeAllCards();

        assertEquals(THREE_CARDS, removedCards.size());
        assertTrue(removedCards.contains(firstCard));
        assertTrue(removedCards.contains(secondCard));
        assertTrue(removedCards.contains(thirdCard));
        assertEquals(ZERO_CARDS, player.getCardCount());
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

        addTerritoriesToPlayer(player, EIGHT_TERRITORIES);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithNineTerritoriesAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, NINE_TERRITORIES);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithTenTerritoriesAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, TEN_TERRITORIES);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithElevenTerritoriesAddsThreeInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, ELEVEN_TERRITORIES);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THREE_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithTwelveTerritoriesAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, TWELVE_TERRITORIES);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + FOUR_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithFortyOneTerritoriesAddsThirteenInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, FORTY_ONE_TERRITORIES);
        player.addArmiesToAvailableBasedOnTerritories();

        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + THIRTEEN_ARMIES));
    }

    @Test
    public void addArmiesToAvailableBasedOnTerritoriesWithFortyTwoTerritoriesRaisesException() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addTerritoriesToPlayer(player, FORTY_TWO_TERRITORIES);
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

        addTerritoriesToPlayer(player, FORTY_THREE_TERRITORIES);
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

        addOneOfEachCard(player);

        boolean traded = player.tradeCardsAndAddArmies(
                List.of(FIRST_CARD_INDEX, SECOND_CARD_INDEX),
                new Deck(),
                ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithMoreThanThreeSelectedCardsReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);
        player.addCard(createCard(CardType.INFANTRY));

        boolean traded = player.tradeCardsAndAddArmies(
                List.of(FIRST_CARD_INDEX, SECOND_CARD_INDEX, THIRD_CARD_INDEX, FOURTH_CARD_INDEX),
                new Deck(),
                ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(FOUR_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSelectedIndexBelowOneReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = player.tradeCardsAndAddArmies(
                List.of(ZERO_ARMIES, FIRST_CARD_INDEX, SECOND_CARD_INDEX),
                new Deck(),
                ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSelectedIndexAboveHandSizeReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = player.tradeCardsAndAddArmies(
                List.of(FIRST_CARD_INDEX, SECOND_CARD_INDEX, FOURTH_CARD_INDEX),
                new Deck(),
                ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithDuplicateSelectedIndicesReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = player.tradeCardsAndAddArmies(
                List.of(FIRST_CARD_INDEX, FIRST_CARD_INDEX, SECOND_CARD_INDEX),
                new Deck(),
                ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThreeInfantryCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.INFANTRY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertTrue(traded);
        assertEquals(ZERO_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThreeCavalryCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.CAVALRY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertTrue(traded);
        assertEquals(ZERO_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThreeArtilleryCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.ARTILLERY));
        player.addCard(createCard(CardType.ARTILLERY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertTrue(traded);
        assertEquals(ZERO_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithOneOfEachCardTypeAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertTrue(traded);
        assertEquals(ZERO_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithTwoMatchingAndOneDifferentCardReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithoutCavalryCardReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.ARTILLERY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithoutArtilleryCardReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.CAVALRY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithoutInfantryCardReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));
        player.addCard(createCard(CardType.ARTILLERY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithOneWildAndTwoMatchingCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.INFANTRY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertTrue(traded);
        assertEquals(ZERO_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithOneWildAndTwoDifferentCardsAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertTrue(traded);
        assertEquals(ZERO_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithTwoWildCardsReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.INFANTRY));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThreeWildCardsReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.WILD));
        player.addCard(createCard(CardType.WILD));

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithInconsistentWildClassificationReturnsFalse() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);
        RiskCard firstCard = createMock(RiskCard.class);
        RiskCard secondCard = createMock(RiskCard.class);
        RiskCard thirdCard = createMock(RiskCard.class);

        expect(firstCard.getType()).andReturn(CardType.INFANTRY).times(FIRST_CARD_TYPE_CALLS);
        expect(secondCard.getType()).andReturn(CardType.WILD).times(SECOND_CARD_TYPE_CALLS);
        expect(thirdCard.getType()).andReturn(CardType.CAVALRY).times(THIRD_CARD_TYPE_CALLS_BEFORE_WILD);
        expect(thirdCard.getType()).andReturn(CardType.WILD);
        replay(firstCard, secondCard, thirdCard);

        player.addCard(firstCard);
        player.addCard(secondCard);
        player.addCard(thirdCard);

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertFalse(traded);
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFirstTradeInAddsFourInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, ZERO_TRADE_SETS);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FOUR_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSecondTradeInAddsSixInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, ONE_TRADE_SET);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + SIX_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithThirdTradeInAddsEightInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, TWO_TRADE_SETS);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + EIGHT_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFourthTradeInAddsTenInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, THREE_TRADE_SETS);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + TEN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFifthTradeInAddsTwelveInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, FOUR_TRADE_SETS);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains("INFANTRY=" + TWELVE_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSixthTradeInAddsFifteenInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, FIVE_TRADE_SETS);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FIFTEEN_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithSeventhTradeInAddsTwentyInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, SIX_TRADE_SETS);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + TWENTY_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFourteenthTradeInAddsFiftyFiveInfantry() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        boolean traded = tradeFirstThreeCards(player, THIRTEEN_TRADE_SETS);

        assertTrue(traded);
        assertTrue(player.getAvailableArmies().contains(
                "INFANTRY=" + FIFTY_FIVE_CARD_TRADE_IN_ARMIES));
    }

    @Test
    public void tradeCardsAndAddArmiesWithFifteenthTradeInRaisesException() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, ZERO_INFANTRY);

        addOneOfEachCard(player);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.tradeCardsAndAddArmies(
                        firstThreeCardIndices(),
                        new Deck(),
                        FOURTEEN_TRADE_SETS));

        assertEquals(
                "Cannot trade cards after 14 sets because a 44-card deck supports at most 14 traded sets.",
                exception.getMessage());
        assertEquals(THREE_CARDS, player.getCardCount());
        assertTrue(player.getAvailableArmies().contains("INFANTRY=0"));
    }

    @Test
    public void ownsTerritoryReturnsTrueForAddedRealTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Territory territory = createRealTerritory();

        player.addTerritory(territory);

        assertTrue(player.ownsTerritory(territory));
    }

    @Test
    public void ownsTerritoryReturnsFalseForNonAddedRealTerritory() {
        HumanPlayer player = new HumanPlayer("Player 1", PlayerColor.RED, STARTING_INFANTRY);
        Continent continent = new Continent("Asia", FIVE_ARMIES);
        Territory owned = new Territory("Japan", continent, Collections.emptyList());
        Territory unowned = new Territory("China", continent, Collections.emptyList());

        player.addTerritory(owned);

        assertFalse(player.ownsTerritory(unowned));
    }

    @Test
    public void toStringContainsPlayerNameAndColor() {
        HumanPlayer player = new HumanPlayer("Alice", PlayerColor.RED, ONE_INFANTRY);

        String result = player.toString();

        assertTrue(result.contains("Alice"));
        assertTrue(result.contains("RED"));
    }

    @Test
    public void removeArmiesExactCavalryMatchPreservesRemainingInfantry() {
        HumanPlayer player = new HumanPlayer(
                "Player 1",
                PlayerColor.RED,
                FIVE_ARMIES);
        HashMap<ArmyType, Integer> cavalryToAdd =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);
        player.addArmies(cavalryToAdd);

        HashMap<ArmyType, Integer> cavalryToRemove =
                createArmies(ZERO_ARMIES, ONE_ARMY, ZERO_ARMIES);
        player.removeArmies(cavalryToRemove);

        String remainingArmies = player.getAvailableArmies();

        assertTrue(remainingArmies.contains("INFANTRY=5"));
        assertTrue(remainingArmies.contains("CAVALRY=0"));
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

    private void addOneOfEachCard(final HumanPlayer player) {
        player.addCard(createCard(CardType.INFANTRY));
        player.addCard(createCard(CardType.CAVALRY));
        player.addCard(createCard(CardType.ARTILLERY));
    }

    private List<Integer> firstThreeCardIndices() {
        return List.of(FIRST_CARD_INDEX, SECOND_CARD_INDEX, THIRD_CARD_INDEX);
    }

    private boolean tradeFirstThreeCards(final HumanPlayer player, final int tradedSetCount) {
        return player.tradeCardsAndAddArmies(firstThreeCardIndices(), new Deck(), tradedSetCount);
    }

    private Territory createRealTerritory() {
        Continent continent = new Continent("Asia", FIVE_ARMIES);
        return new Territory("Japan", continent, Collections.emptyList());
    }
}
