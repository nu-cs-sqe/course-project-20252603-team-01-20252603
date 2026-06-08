package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;

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
}