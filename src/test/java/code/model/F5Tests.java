package code.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for F5 - Reinforcement Phase.
 */
public final class F5Tests {

    private static final int ONE_INFANTRY = 1;

    private static final int THREE_PLAYER_COUNT = 3;

    private static final int AUSTRALIA_REINFORCEMENT_TOTAL = 5;

    private static final int INDONESIA_ARMIES_AFTER_REINFORCEMENT = 6;

    private HashMap<ArmyType, Integer> createInfantryPieces(final int infantryCount) {
        HashMap<ArmyType, Integer> pieces = new HashMap<>();
        pieces.put(ArmyType.INFANTRY, infantryCount);
        return pieces;
    }

    private GameModel createThreePlayerGame() {
        GameModel model = new GameModel(new Random(0));
        model.initializeContinentsAndTerritories();
        model.setPlayerCount(THREE_PLAYER_COUNT);
        model.addPlayer("Player 1", PlayerColor.RED);
        model.addPlayer("Player 2", PlayerColor.BLUE);
        model.addPlayer("Player 3", PlayerColor.GREEN);
        model.setCurrentPlayerIndex(0);
        return model;
    }

    private void claimTerritoriesForCurrentPlayer(
            final GameModel model,
            final List<String> territoryNames) {
        for (String territoryName : territoryNames) {
            model.setCurrentPlayerIndex(0);
            model.claimTerritoryDuringSetup(
                    territoryName,
                    createInfantryPieces(ONE_INFANTRY));
        }
    }

    @Test
    public void reinforcementPhaseWithFullAustraliaAddsAndPlacesFiveInfantry() {
        GameModel model = createThreePlayerGame();
        claimTerritoriesForCurrentPlayer(
                model,
                List.of(
                        "Indonesia",
                        "New Guinea",
                        "Western Australia",
                        "Eastern Australia"));

        model.addArmiesToCurrentPlayerBasedOnTerritories();
        model.addArmiesToCurrentPlayerBasedOnContinents();

        boolean placed = model.placeArmiesDuringReinforcement(
                "Indonesia",
                createInfantryPieces(AUSTRALIA_REINFORCEMENT_TOTAL));

        Territory indonesia = model.findTerritoryByName("Indonesia");
        assertTrue(placed);
        assertEquals(
                INDONESIA_ARMIES_AFTER_REINFORCEMENT,
                indonesia.getArmiesOfType(ArmyType.INFANTRY));
    }

    @Test
    public void reinforcementPhaseRejectsPlacementOnEnemyTerritory() {
        GameModel model = createThreePlayerGame();

        model.claimTerritoryDuringSetup("Indonesia", createInfantryPieces(ONE_INFANTRY));
        model.setCurrentPlayerIndex(1);
        model.claimTerritoryDuringSetup("Alaska", createInfantryPieces(ONE_INFANTRY));
        model.setCurrentPlayerIndex(0);

        model.addArmiesToCurrentPlayerBasedOnTerritories();

        boolean placed = model.placeArmiesDuringReinforcement(
                "Alaska",
                createInfantryPieces(ONE_INFANTRY));

        Territory alaska = model.findTerritoryByName("Alaska");
        assertFalse(placed);
        assertEquals(ONE_INFANTRY, alaska.getArmiesOfType(ArmyType.INFANTRY));
    }
}
