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

    private final GameModel model;

    private final ConsoleView view;

    private static final int TERRITORY_INPUT_INDEX = 0;

    private static final int INFANTRY_INPUT_INDEX = 1;

    private static final int CAVALRY_INPUT_INDEX = 2;

    private static final int ARTILLERY_INPUT_INDEX = 3;

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
            model.handleCardTradeIn(view.promptChooseCardsToTradeIn());
            view.displayCurrentPlayerArmies(model.getCurrentPlayerAvailableArmies());
        }
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
}
