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
}
