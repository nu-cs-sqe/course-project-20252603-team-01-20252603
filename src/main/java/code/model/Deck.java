package code.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the deck of Risk cards used in the game.
 */
public class Deck {

    private static final int TERRITORY_CARD_COUNT = 42;


    private static final int WILD_CARD_COUNT = 2;

    private static final int DEFAULT_CONTINENT_BONUS = 5;

    private static final int CARD_TYPE_COUNT = 3;

    private final List<RiskCard> cards;

    private final List<RiskCard> discardPile;

    public Deck() {
        cards = new ArrayList<>();
        discardPile = new ArrayList<>();
        initializeTerritoryCards();
        initializeWildCards();
    }

    public int size() {
        return cards.size();
    }

    public List<RiskCard> getCards() {
        return new ArrayList<>(cards);
    }

    public int getDiscardPileSize() {
        return discardPile.size();
    }

    private void initializeTerritoryCards() {
        Continent continent = new Continent(
                "Deck Continent",
                DEFAULT_CONTINENT_BONUS);

        for (int index = 0; index < TERRITORY_CARD_COUNT; index++) {
            Territory territory = new Territory(
                    "Territory " + (index + 1),
                    continent,
                    Collections.emptyList());
            CardType type = getCardTypeForIndex(index);

            cards.add(new RiskCard(territory, type, false));
        }
    }

    private void initializeWildCards() {
        for (int index = 0; index < WILD_CARD_COUNT; index++) {
            cards.add(new RiskCard(null, CardType.WILD, true));
        }
    }

    private CardType getCardTypeForIndex(final int index) {
        int typeIndex = index % CARD_TYPE_COUNT;

        if (typeIndex == 0) {
            return CardType.INFANTRY;
        }

        if (typeIndex == 1) {
            return CardType.CAVALRY;
        }

        return CardType.ARTILLERY;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

}
