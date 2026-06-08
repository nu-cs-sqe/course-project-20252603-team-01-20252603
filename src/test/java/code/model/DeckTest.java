package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests boundary values and core behavior for the Deck class.
 */
public final class DeckTest {

    private static final int TOTAL_CARD_COUNT = 44;

    @Test
    public void freshDeckContainsFortyFourCards() {
        Deck deck = new Deck();

        assertEquals(TOTAL_CARD_COUNT, deck.size());
    }
}