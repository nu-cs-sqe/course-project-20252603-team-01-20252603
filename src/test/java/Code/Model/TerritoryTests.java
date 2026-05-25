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

    @Test
    void setOwner_TerritoryHasNoOwner_SetsOwnerToPlayer() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);
        Player player = new Player("Player 1", PlayerColor.RED);

        territory.setOwner(player);

        assertSame(player, territory.getOwner());
    }

    @Test
    void setOwner_TerritoryHasOwnerAndNewOwnerIsNull_MakesTerritoryUnclaimed() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);
        Player player = new Player("Player 1", PlayerColor.RED);

        territory.setOwner(player);
        territory.setOwner(null);

        assertNull(territory.getOwner());
        assertTrue(territory.isUnclaimed());
    }

    @Test
    void isOwnedBy_SamePlayerObjectOwnsTerritory_ReturnsTrue() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);
        Player player = new Player("Player 1", PlayerColor.RED);

        territory.setOwner(player);

        assertTrue(territory.isOwnedBy(player));
    }

    @Test
    void isOwnedBy_DifferentPlayerObjectDoesNotOwnTerritory_ReturnsFalse() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);
        Player player1 = new Player("Player 1", PlayerColor.RED);
        Player player2 = new Player("Player 2", PlayerColor.BLUE);

        territory.setOwner(player1);

        assertFalse(territory.isOwnedBy(player2));
    }

    @Test
    void isOwnedBy_NullPlayerCheckedAgainstOwnedTerritory_ReturnsFalse() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);
        Player player = new Player("Player 1", PlayerColor.RED);

        territory.setOwner(player);

        assertFalse(territory.isOwnedBy(null));
    }

    @Test
    void isOwnedBy_NullPlayerCheckedAgainstUnclaimedTerritory_ReturnsFalse() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        assertFalse(territory.isOwnedBy(null));
    }

    @Test
    void addArmies_CountIsNegative_ReturnsFalseAndArmyCountUnchanged() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        boolean result = territory.addArmies(-1);

        assertFalse(result);
        assertEquals(0, territory.getArmyCount());
    }

    @Test
    void addArmies_CountIsZero_ReturnsFalseAndArmyCountUnchanged() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        boolean result = territory.addArmies(0);

        assertFalse(result);
        assertEquals(0, territory.getArmyCount());
    }
}
