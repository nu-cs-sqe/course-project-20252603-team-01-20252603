package code.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Verifies every territory adjacency pair defined in initializeAdjacencies.
 * Each row kills the mutation that removes the corresponding connect() call,
 * and the bidirectional assertion kills the two mutations that remove one of
 * the two addAdjacentTerritory calls inside the connect() method.
 */
public final class GameModelAdjacencyTest {

    private GameModel createInitializedModel() {
        GameModel model = new GameModel();
        model.initializeContinentsAndTerritories();
        return model;
    }

    @org.junit.jupiter.api.Test
    public void alaskaIsNotAdjacentToEasternAustralia() {
        GameModel model = createInitializedModel();

        org.junit.jupiter.api.Assertions.assertFalse(
                model.areTerritoriesAdjacent("Alaska", "Eastern Australia"));
        org.junit.jupiter.api.Assertions.assertFalse(
                model.areTerritoriesAdjacent("Eastern Australia", "Alaska"));
    }

    @ParameterizedTest
    @CsvSource({
            "Alaska,Northwest Territory",
            "Alaska,Alberta",
            "Alaska,Kamchatka",
            "Northwest Territory,Alberta",
            "Northwest Territory,Ontario",
            "Northwest Territory,Greenland",
            "Greenland,Ontario",
            "Greenland,Quebec",
            "Greenland,Iceland",
            "Alberta,Ontario",
            "Alberta,Western United States",
            "Ontario,Quebec",
            "Ontario,Western United States",
            "Ontario,Eastern United States",
            "Quebec,Eastern United States",
            "Western United States,Eastern United States",
            "Western United States,Central America",
            "Eastern United States,Central America",
            "Central America,Venezuela",
            "Venezuela,Peru",
            "Venezuela,Brazil",
            "Peru,Brazil",
            "Peru,Argentina",
            "Brazil,Argentina",
            "Brazil,North Africa",
            "Iceland,Scandinavia",
            "Iceland,Great Britain",
            "Scandinavia,Ukraine",
            "Scandinavia,Great Britain",
            "Great Britain,Northern Europe",
            "Great Britain,Western Europe",
            "Northern Europe,Western Europe",
            "Northern Europe,Southern Europe",
            "Northern Europe,Ukraine",
            "Western Europe,Southern Europe",
            "Western Europe,North Africa",
            "Southern Europe,North Africa",
            "Southern Europe,Egypt",
            "Southern Europe,Middle East",
            "Southern Europe,Ukraine",
            "Ukraine,Ural",
            "Ukraine,Afghanistan",
            "Ukraine,Middle East",
            "North Africa,Egypt",
            "North Africa,East Africa",
            "North Africa,Congo",
            "Egypt,East Africa",
            "Egypt,Middle East",
            "East Africa,Middle East",
            "East Africa,Congo",
            "East Africa,South Africa",
            "East Africa,Madagascar",
            "Congo,South Africa",
            "South Africa,Madagascar",
            "Ural,Siberia",
            "Ural,China",
            "Ural,Afghanistan",
            "Siberia,Yakutsk",
            "Siberia,Irkutsk",
            "Siberia,Mongolia",
            "Siberia,China",
            "Yakutsk,Kamchatka",
            "Yakutsk,Irkutsk",
            "Kamchatka,Irkutsk",
            "Kamchatka,Mongolia",
            "Kamchatka,Japan",
            "Irkutsk,Mongolia",
            "Mongolia,Japan",
            "Mongolia,China",
            "Afghanistan,China",
            "Afghanistan,Middle East",
            "Afghanistan,India",
            "China,India",
            "China,Siam",
            "Middle East,India",
            "India,Siam",
            "Siam,Indonesia",
            "Indonesia,New Guinea",
            "Indonesia,Western Australia",
            "New Guinea,Western Australia",
            "New Guinea,Eastern Australia",
            "Western Australia,Eastern Australia"
    })
    public void territoriesAreAdjacentInBothDirections(
            final String territory1,
            final String territory2) {
        GameModel model = createInitializedModel();

        assertTrue(
                model.areTerritoriesAdjacent(territory1, territory2),
                territory1 + " should be adjacent to " + territory2);
        assertTrue(
                model.areTerritoriesAdjacent(territory2, territory1),
                territory2 + " should be adjacent to " + territory1);
    }
}
