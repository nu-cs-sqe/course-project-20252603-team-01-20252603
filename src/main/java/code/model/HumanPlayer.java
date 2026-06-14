package code.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents a human player in the Risk game.
 */
public class HumanPlayer extends Player {

    private static final int INFANTRY_VALUE = 1;

    private static final int CAVALRY_VALUE = 5;

    private static final int ARTILLERY_VALUE = 10;

    private static final int MIN_TERRITORY_REINFORCEMENT = 3;

    private static final int TERRITORIES_PER_REINFORCEMENT_ARMY = 3;

    private static final int TOTAL_TERRITORY_COUNT = 42;

    private static final int FIRST_CARD_TRADE_IN_BONUS = 4;

    private static final int SECOND_CARD_TRADE_IN_BONUS = 6;

    private static final int THIRD_CARD_TRADE_IN_BONUS = 8;

    private static final int FOURTH_CARD_TRADE_IN_BONUS = 10;

    private static final int FIFTH_CARD_TRADE_IN_BONUS = 12;

    private static final int SIXTH_CARD_TRADE_IN_BONUS = 15;

    private static final int CARD_TRADE_IN_BONUS_INCREMENT = 5;

    private static final int MAX_CARD_TRADE_IN_COUNT = 14;

    private final List<Territory> territories;

    private final List<RiskCard> availableCards;

    private HashMap<ArmyType, Integer> availableArmies;

    public HumanPlayer(
            final String playerName,
            final PlayerColor playerColor,
            final int startingInfantry) {
        super(playerName, playerColor, startingInfantry);
        territories = new ArrayList<>();
        availableCards = new ArrayList<>();
        availableArmies = new HashMap<>();
        availableArmies.put(ArmyType.INFANTRY, startingInfantry);
    }

    @Override
    public void addTerritory(final Territory territory) {
        territories.add(territory);
    }

    @Override
    public boolean ownsTerritory(final Territory territory) {
        return territories.contains(territory);
    }

    @Override
    public int getTerritoryCount() {
        return territories.size();
    }

    @Override
    public void addArmies(final HashMap<ArmyType, Integer> armiesToAdd) {
        for (Map.Entry<ArmyType, Integer> entry : armiesToAdd.entrySet()) {
            ArmyType armyType = entry.getKey();
            int addedCount = entry.getValue();
            int currentCount = availableArmies.getOrDefault(armyType, 0);

            availableArmies.put(armyType, currentCount + addedCount);
        }
    }

    @Override
    public void removeArmies(final HashMap<ArmyType, Integer> armiesToRemove) {
        if (hasExactArmies(armiesToRemove)) {
            removeExactArmies(armiesToRemove);
            return;
        }

        int remainingValue = calculateArmyValue(availableArmies)
                - calculateArmyValue(armiesToRemove);

        availableArmies = convertValueToArmies(remainingValue);
    }

    private boolean hasExactArmies(final HashMap<ArmyType, Integer> requiredArmies) {
        for (Map.Entry<ArmyType, Integer> entry : requiredArmies.entrySet()) {
            ArmyType armyType = entry.getKey();
            int requiredCount = entry.getValue();
            int availableCount = availableArmies.getOrDefault(armyType, 0);

            if (availableCount < requiredCount) {
                return false;
            }
        }

        return true;
    }

    private void removeExactArmies(final HashMap<ArmyType, Integer> armiesToRemove) {
        for (Map.Entry<ArmyType, Integer> entry : armiesToRemove.entrySet()) {
            ArmyType armyType = entry.getKey();
            int removedCount = entry.getValue();
            int currentCount = availableArmies.getOrDefault(armyType, 0);

            availableArmies.put(armyType, currentCount - removedCount);
        }
    }

    @Override
    public String getAvailableArmies() {
        return availableArmies.toString();
    }

    @Override
    public boolean hasAvailableArmies(final HashMap<ArmyType, Integer> requiredArmies) {
        return calculateArmyValue(availableArmies) >= calculateArmyValue(requiredArmies);
    }

    @Override
    public void addArmiesToAvailableBasedOnTerritories() {
        if (getTerritoryCount() == 0) {
            throw new IllegalStateException(
                    "Player cannot own 0 territories and play a turn because they have been eliminated.");
        }

        if (getTerritoryCount() == TOTAL_TERRITORY_COUNT) {
            throw new IllegalStateException(
                    "Player cannot own 42 territories and play a turn because they should have already won.");
        }

        if (getTerritoryCount() > TOTAL_TERRITORY_COUNT) {
            throw new IllegalStateException(
                    "Player cannot own " + getTerritoryCount()
                            + " territories because there are only 42 territories on the board.");
        }

        int territoryReinforcement = getTerritoryCount() / TERRITORIES_PER_REINFORCEMENT_ARMY;

        if (territoryReinforcement < MIN_TERRITORY_REINFORCEMENT) {
            territoryReinforcement = MIN_TERRITORY_REINFORCEMENT;
        }

        HashMap<ArmyType, Integer> reinforcementArmies = new HashMap<>();
        reinforcementArmies.put(ArmyType.INFANTRY, territoryReinforcement);
        addArmies(reinforcementArmies);
    }

    void addCard(final RiskCard card) {
        availableCards.add(card);
    }

    int getCardCount() {
        return availableCards.size();
    }

    List<RiskCard> getAvailableCards() {
        return new ArrayList<>(availableCards);
    }

    @Override
    public boolean tradeCardsAndAddArmies(
            final List<Integer> cardIndices,
            final Deck deck,
            final int numSetsTradedIn) {
        if (numSetsTradedIn >= MAX_CARD_TRADE_IN_COUNT) {
            throw new IllegalArgumentException(
                    "Cannot trade cards after " + numSetsTradedIn
                            + " sets because a 44-card deck supports at most 14 traded sets.");
        }

        if (cardIndices.size() != 3) {
            return false;
        }

        if (cardIndices.stream()
                .anyMatch(index -> index < 1 || index > availableCards.size())) {
            return false;
        }

        if (cardIndices.stream().distinct().count() != cardIndices.size()) {
            return false;
        }

        List<RiskCard> selectedCards = cardIndices.stream()
                .map(index -> availableCards.get(index - 1))
                .collect(Collectors.toList());

        CardType firstCardType = selectedCards.get(0).getType();
        boolean hasThreeCardsOfSameType = firstCardType != CardType.WILD
                && selectedCards.stream()
                        .allMatch(card -> card.getType() == firstCardType);
        boolean hasOneOfEachType = selectedCards.stream()
                .anyMatch(card -> card.getType() == CardType.INFANTRY)
                && selectedCards.stream().anyMatch(card -> card.getType() == CardType.CAVALRY)
                && selectedCards.stream().anyMatch(card -> card.getType() == CardType.ARTILLERY);
        long wildCardCount = selectedCards.stream()
                .filter(card -> card.getType() == CardType.WILD)
                .count();
        List<RiskCard> nonWildCards = selectedCards.stream()
                .filter(card -> card.getType() != CardType.WILD)
                .collect(Collectors.toList());
        boolean hasOneWildAndTwoNonWildCards = wildCardCount == 1
                && nonWildCards.size() == 2;

        if (!hasThreeCardsOfSameType
                && !hasOneOfEachType
                && !hasOneWildAndTwoNonWildCards) {
            return false;
        }

        HashMap<ArmyType, Integer> armiesToAdd = new HashMap<>();
        armiesToAdd.put(ArmyType.INFANTRY, calculateCardTradeInBonus(numSetsTradedIn));
        addArmies(armiesToAdd);

        cardIndices.stream()
                .sorted((firstIndex, secondIndex) -> secondIndex - firstIndex)
                .forEach(index -> availableCards.remove(index - 1));

        return true;
    }

    private int calculateCardTradeInBonus(final int numSetsTradedIn) {
        if (numSetsTradedIn == 1) {
            return SECOND_CARD_TRADE_IN_BONUS;
        }

        if (numSetsTradedIn == 2) {
            return THIRD_CARD_TRADE_IN_BONUS;
        }

        if (numSetsTradedIn == 3) {
            return FOURTH_CARD_TRADE_IN_BONUS;
        }

        if (numSetsTradedIn == 4) {
            return FIFTH_CARD_TRADE_IN_BONUS;
        }

        if (numSetsTradedIn == 5) {
            return SIXTH_CARD_TRADE_IN_BONUS;
        }

        if (numSetsTradedIn > 5) {
            return SIXTH_CARD_TRADE_IN_BONUS
                    + CARD_TRADE_IN_BONUS_INCREMENT * (numSetsTradedIn - 5);
        }

        return FIRST_CARD_TRADE_IN_BONUS;
    }

    private int calculateArmyValue(final HashMap<ArmyType, Integer> armies) {
        int infantryCount = armies.getOrDefault(ArmyType.INFANTRY, 0);
        int cavalryCount = armies.getOrDefault(ArmyType.CAVALRY, 0);
        int artilleryCount = armies.getOrDefault(ArmyType.ARTILLERY, 0);

        return infantryCount * INFANTRY_VALUE
                + cavalryCount * CAVALRY_VALUE
                + artilleryCount * ARTILLERY_VALUE;
    }

    private HashMap<ArmyType, Integer> convertValueToArmies(final int armyValue) {
        HashMap<ArmyType, Integer> convertedArmies = new HashMap<>();
        int remainingValue = armyValue;

        convertedArmies.put(ArmyType.ARTILLERY, remainingValue / ARTILLERY_VALUE);
        remainingValue %= ARTILLERY_VALUE;

        convertedArmies.put(ArmyType.CAVALRY, remainingValue / CAVALRY_VALUE);
        remainingValue %= CAVALRY_VALUE;

        convertedArmies.put(ArmyType.INFANTRY, remainingValue);

        return convertedArmies;
    }


}
