package Code.Model;

public class Territory {
    private String name;
    private int armyCount;
    private Player owner;
    private Continent continent;

    public Territory(String name, Continent continent) {
        this.name = name;
        this.continent = continent;
        this.armyCount = 0;
        this.owner = null;
    }

    public boolean isUnclaimed() {
        return owner == null;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public Player getOwner() {
        return this.owner;
    }

    public boolean isOwnedBy(Player player) {
        return this.owner != null && this.owner.equals(player);
    }
}
