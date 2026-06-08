package code.model;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests boundary values and core behavior for the Deck class.
 */
public final class DeckTest {

    private static final int TOTAL_CARD_COUNT = 44;
    private static final int TERRITORY_CARD_COUNT = 42;
    private static final int WILD_CARD_COUNT = 2;

    @Test
    public void freshDeckContainsFortyFourCards() {
        Deck deck = new Deck();

        assertEquals(TOTAL_CARD_COUNT, deck.size());
    }

    @Test
    public void freshDeckContainsFortyTwoTerritoryCards() {
        Deck deck = new Deck();

        long territoryCardCount = deck.getCards()
                .stream()
                .filter(card -> !card.isWild())
                .count();

        assertEquals(TERRITORY_CARD_COUNT, territoryCardCount);
    }

    @Test
    public void freshDeckContainsTwoWildCards() {
        Deck deck = new Deck();

        long wildCardCount = deck.getCards()
                .stream()
                .filter(RiskCard::isWild)
                .count();

        assertEquals(WILD_CARD_COUNT, wildCardCount);
    }

    @Test
    public void everyTerritoryCardHasTerritory() {
        Deck deck = new Deck();

        deck.getCards()
                .stream()
                .filter(card -> !card.isWild())
                .forEach(card -> assertNotNull(card.getTerritory()));
    }

    @Test
    public void everyTerritoryAppearsOnExactlyOneCard() {
        Deck deck = new Deck();
        Set<Territory> territories = new HashSet<>();

        deck.getCards()
                .stream()
                .filter(card -> !card.isWild())
                .forEach(card -> territories.add(card.getTerritory()));

        assertEquals(TERRITORY_CARD_COUNT, territories.size());
    }

    @Test
    public void deckContainsEachNonWildCardType() {
        Deck deck = new Deck();

        boolean hasInfantry = deck.getCards()
                .stream()
                .filter(card -> !card.isWild())
                .anyMatch(card -> card.getType() == CardType.INFANTRY);
        boolean hasCavalry = deck.getCards()
                .stream()
                .filter(card -> !card.isWild())
                .anyMatch(card -> card.getType() == CardType.CAVALRY);
        boolean hasArtillery = deck.getCards()
                .stream()
                .filter(card -> !card.isWild())
                .anyMatch(card -> card.getType() == CardType.ARTILLERY);

        assertTrue(hasInfantry);
        assertTrue(hasCavalry);
        assertTrue(hasArtillery);
    }

    @Test
    public void freshDeckIsNotEmpty() {
        Deck deck = new Deck();

        assertFalse(deck.isEmpty());
    }
}