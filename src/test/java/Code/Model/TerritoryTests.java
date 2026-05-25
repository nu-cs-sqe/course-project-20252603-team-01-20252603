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

    @Test
    void addArmies_CountIsOne_ReturnsTrueAndArmyCountBecomesOne() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        boolean result = territory.addArmies(1);

        assertTrue(result);
        assertEquals(1, territory.getArmyCount());
    }

    @Test
    void addArmies_CountIsMoreThanOne_ReturnsTrueAndArmyCountIncreasesByCount() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        boolean result = territory.addArmies(3);

        assertTrue(result);
        assertEquals(3, territory.getArmyCount());
    }

    @Test
    void addArmies_ArmyCountAlreadyNonzero_ReturnsTrueAndAddsToExistingCount() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        territory.addArmies(2);
        boolean result = territory.addArmies(3);

        assertTrue(result);
        assertEquals(5, territory.getArmyCount());
    }

    @Test
    void getArmyCount_NewTerritory_ReturnsZero() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        assertEquals(0, territory.getArmyCount());
    }

    @Test
    void getArmyCount_AfterAddingOneArmy_ReturnsOne() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        territory.addArmies(1);

        assertEquals(1, territory.getArmyCount());
    }

    @Test
    void getName_NormalTerritoryName_ReturnsTerritoryName() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        assertEquals("Alaska", territory.getName());
    }

    @Test
    void getName_EmptyTerritoryName_ThrowsIllegalArgumentException() {
        Continent continent = new Continent("North America");

        assertThrows(IllegalArgumentException.class, () -> {
            new Territory("", continent);
        });
    }

    @Test
    void getOwner_NewTerritory_ReturnsNull() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        assertNull(territory.getOwner());
    }

    @Test
    void getOwner_AfterSettingOwner_ReturnsOwner() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);
        Player player = new Player("Player 1", PlayerColor.RED);

        territory.setOwner(player);

        assertSame(player, territory.getOwner());
    }

    @Test
    void getContinent_TerritoryHasAssignedContinent_ReturnsContinent() {
        Continent continent = new Continent("North America");
        Territory territory = new Territory("Alaska", continent);

        assertSame(continent, territory.getContinent());
    }
}
