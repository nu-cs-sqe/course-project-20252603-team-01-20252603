package code.controller;

import code.model.ArmyType;
import code.model.GameModel;
import code.view.ConsoleView;

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

    public TurnController(final GameModel gameModel, final ConsoleView consoleView) {
        model = gameModel;
        view = consoleView;
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

                model.placeArmiesDuringReinforcement(
                        reinforcementInput.get(TERRITORY_INPUT_INDEX),
                        pieces);
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