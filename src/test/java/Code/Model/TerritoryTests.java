package Code.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TerritoryTests {
    @Test
    void isUnclaimed_NewTerritoryHasNoOwner_ReturnsTrue() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        assertTrue(territory.isUnclaimed());
    }

    @Test
    void isUnclaimed_TerritoryHasOwner_ReturnsFalse() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);
        Player player = new Player("Player 1", PlayerColor.RED);

        territory.setOwner(player);

        assertFalse(territory.isUnclaimed());
    }
}
