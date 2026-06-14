package code.controller;

import code.model.ArmyType;
import code.model.GameModel;
import code.model.TradeInPossibility;
import code.view.ConsoleView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.HashMap;
import java.util.List;

/**
 * Coordinates the phases of a player's turn.
 */
public class TurnController {

    private static final int MALFORMED_CARD_INPUT_SENTINEL = Integer.MIN_VALUE;

    private static final int MALFORMED_DICE_INPUT_SENTINEL = Integer.MIN_VALUE;

    private final GameModel model;

    private final ConsoleView view;

    private static final int TERRITORY_INPUT_INDEX = 0;

    private static final int INFANTRY_INPUT_INDEX = 1;

    private static final int CAVALRY_INPUT_INDEX = 2;

    private static final int ARTILLERY_INPUT_INDEX = 3;

    private static final int ATTACKING_TERRITORY_INDEX = 0;

    private static final int DEFENDING_TERRITORY_INDEX = 1;

    private static final int ATTACKER_DICE_INDEX = 0;

    private static final int DEFENDER_DICE_INDEX = 1;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "TurnController intentionally stores the model and view it controls."
    )
    public TurnController(final GameModel gameModel, final ConsoleView consoleView) {
        model = gameModel;
        view = consoleView;
    }

    public void handleArmiesToAdd() {
        model.addArmiesToCurrentPlayerBasedOnTerritories();
        model.addArmiesToCurrentPlayerBasedOnContinents();

        TradeInPossibility tradeInPossibility = model.checkCardTradeInPossibility();

        if (tradeInPossibility == TradeInPossibility.NOT_ALLOWED) {
            view.displayCurrentPlayerArmies(model.getCurrentPlayerAvailableArmies());
            return;
        }

        if (tradeInPossibility == TradeInPossibility.ALLOWED) {
            view.displayCurrentPlayerCards(model.getCurrentPlayerCards());
            List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

            while (isMalformedCardTradeInInput(cardIndices)) {
                view.displayError("Invalid card trade-in input.");
                cardIndices = view.promptChooseCardsToTradeIn();
            }

            while (!model.handleCardTradeIn(cardIndices)) {
                view.displayError("Invalid card trade-in selection.");
                cardIndices = view.promptChooseCardsToTradeIn();

                while (isMalformedCardTradeInInput(cardIndices)) {
                    view.displayError("Invalid card trade-in input.");
                    cardIndices = view.promptChooseCardsToTradeIn();
                }
            }

            view.displayCurrentPlayerArmies(model.getCurrentPlayerAvailableArmies());
            return;
        }

        if (tradeInPossibility == TradeInPossibility.REQUIRED) {
            view.displayCurrentPlayerCards(model.getCurrentPlayerCards());
            List<Integer> cardIndices = view.promptChooseCardsToTradeIn();

            while (isMalformedCardTradeInInput(cardIndices)) {
                view.displayError("Invalid card trade-in input.");
                cardIndices = view.promptChooseCardsToTradeIn();
            }

            while (cardIndices.isEmpty() || !model.handleCardTradeIn(cardIndices)) {
                if (cardIndices.isEmpty()) {
                    view.displayError("Card trade-in is required.");
                } else {
                    view.displayError("Invalid card trade-in selection.");
                }

                cardIndices = view.promptChooseCardsToTradeIn();

                while (isMalformedCardTradeInInput(cardIndices)) {
                    view.displayError("Invalid card trade-in input.");
                    cardIndices = view.promptChooseCardsToTradeIn();
                }
            }

            view.displayCurrentPlayerArmies(model.getCurrentPlayerAvailableArmies());
        }
    }

    private boolean isMalformedCardTradeInInput(final List<Integer> cardIndices) {
        return cardIndices.size() == 1
                && cardIndices.get(0) == MALFORMED_CARD_INPUT_SENTINEL;
    }

    public void handleReinforcement() {
        while (model.currentPlayerHasAvailableArmies()) {
            view.displayCurrentPlayer(model.getCurrentPlayerName());
            view.displayCurrentPlayerClaimingStatus(
                    model.getCurrentPlayerTerritoriesByContinent());

            List<String> reinforcementInput = view.promptReinforcement();

            if (reinforcementInput.isEmpty()) {
                view.displayError("Invalid reinforcement input.");
            } else {
                HashMap<ArmyType, Integer> pieces =
                        createReinforcementPieces(reinforcementInput);

                boolean placed = model.placeArmiesDuringReinforcement(
                        reinforcementInput.get(TERRITORY_INPUT_INDEX),
                        pieces);

                if (!placed) {
                    view.displayError("Invalid reinforcement placement.");
                }
            }
        }
    }

    private HashMap<ArmyType, Integer> createReinforcementPieces(
            final List<String> reinforcementInput) {
        HashMap<ArmyType, Integer> pieces = new HashMap<>();

        pieces.put(
                ArmyType.INFANTRY,
                Integer.parseInt(reinforcementInput.get(INFANTRY_INPUT_INDEX)));
        pieces.put(
                ArmyType.CAVALRY,
                Integer.parseInt(reinforcementInput.get(CAVALRY_INPUT_INDEX)));
        pieces.put(
                ArmyType.ARTILLERY,
                Integer.parseInt(reinforcementInput.get(ARTILLERY_INPUT_INDEX)));

        return pieces;
    }

    public void handleAttackPhase(final Object player) {
        view.displayCurrentPlayer(model.getCurrentPlayerName());
        view.displayCurrentPlayerClaimingStatus(model.getCurrentPlayerTerritoriesByContinent());

        boolean validTerritories = false;
        String attackerTerritoryName = "";
        String defenderTerritoryName = "";

        while (!validTerritories) {
            List<String> territoryChoices = view.promptTerritoriesToAttack();
            attackerTerritoryName = territoryChoices.get(ATTACKING_TERRITORY_INDEX);
            defenderTerritoryName = territoryChoices.get(DEFENDING_TERRITORY_INDEX);

            try {
                model.validateTerritoriesForAttackAndReturnDefenderName(
                        attackerTerritoryName,
                        defenderTerritoryName);
                validTerritories = true;
            } catch (IllegalArgumentException exception) {
                view.displayError(exception.getMessage());
            }
        }

        List<Integer> diceCounts = view.promptNumberOfDice(
                attackerTerritoryName,
                defenderTerritoryName);
        int attackerDice = diceCounts.get(ATTACKER_DICE_INDEX);
        int defenderDice = diceCounts.get(DEFENDER_DICE_INDEX);
        model.validateNumberOfDice(
                attackerTerritoryName,
                defenderTerritoryName,
                attackerDice,
                defenderDice);

        List<String> battleResult = model.executeBattleAndReturnWinner(
                attackerTerritoryName,
                defenderTerritoryName,
                attackerDice,
                defenderDice);
        view.displayBattleResult(battleResult);
    }
}
