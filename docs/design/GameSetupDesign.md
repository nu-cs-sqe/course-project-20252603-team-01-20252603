# Game Setup Design — Classes, Fields, and Methods

## 1. `GameModel`

**Fields**
- `List<Player> players`
- `GameBoard board`
- `Deck deck`
- `int currentPlayerIndex`
- `GameState gameState`
- `Player firstPlayer`

**Methods**
- `boolean startGame(List<String> playerNames, List<PlayerColor> colors)`
- `boolean claimTerritory(Player player, String territoryName)`
- `boolean placeInitialArmes(Player player, String territoryName, int count)`
- `boolean isSetupComplete()`
- `Player getCurrentPlayer()`
- `Player getFirstPlayer()`
- `GameState getGameState()`
- `GameBoard getBoard()`
- `Deck getDeck()`
- `List<Player> getPlayers()`

---

## 2. `Player`

**Fields**
- `String name`
- `PlayerColor color`
- `List<Territory> territories`
- `int availableArmies`

**Methods**
- `void addTerritory(Territory territory)`
- `boolean ownsTerritory(Territory territory)`
- `boolean placeArmies(Territory territory, int count)`
- `boolean hasAvailableArmies()`
- `int getAvailableArmies()`
- `int getTerritoryCount()`
- `String getName()`
- `PlayerColor getColor()`

---

## 3. `GameBoard`

**Fields**
- `List<Continent> continents`
- `List<Territory> territories`

**Methods**
- `void initializeBoard()`
- `Territory getTerritoryByName(String name)`
- `boolean claimTerritory(String territoryName, Player player)`
- `boolean isTerritoryUnclaimed(String territoryName)`
- `boolean allTerritoriesClaimed()`
- `List<Territory> getUnclaimedTerritories()`
- `List<Territory> getTerritories()`
- `List<Continent> getContinents()`

---

## 4. `Territory`

**Fields**
- `String name`
- `Player owner`
- `int armyCount`
- `Continent continent`

**Methods**
- `void setOwner(Player player)`
- `boolean isUnclaimed()`
- `boolean isOwnedBy(Player player)`
- `boolean addArmies(int count)`
- `int getArmyCount()`
- `String getName()`
- `Player getOwner()`
- `Continent getContinent()`

---

## 5. `Continent`

**Fields**
- `String name`
- `List<Territory> territories`

**Methods**
- `String getName()`
- `List<Territory> getTerritories()`
- `boolean containsTerritory(Territory territory)`

---

## 6. `Deck`

**Fields**
- `List<RiskCard> cards`

**Methods**
- `void initializeClassicDeck(List<Territory> territories)`
- `void shuffle()`
- `int size()`
- `boolean isEmpty()`

---

## 7. `RiskCard`

**Fields**
- `Territory territory`
- `CardType type`
- `boolean wild`

**Methods**
- `Territory getTerritory()`
- `CardType getType()`
- `boolean isWild()`

---

## 8. `SetupManager`

**Fields**
- None required

**Methods**
- `boolean isValidPlayerCount(int count)`
- `int calculateStartingArmies(int playerCount)`
- `boolean areColorsUnique(List<PlayerColor> colors)`
- `boolean allPlayersPlacedArmies(List<Player> players)`

---

## 9. `ConsoleView`

**Fields**
- `Scanner scanner`

**Methods**
- `void displayWelcomeMessage()`
- `int promptNumberOfPlayers()`
- `String promptPlayerName(int playerNumber)`
- `PlayerColor promptPlayerColor(String playerName)`
- `String promptTerritoryChoice(Player player, List<Territory> territories)`
- `int promptArmyPlacement(Player player)`
- `void displayBoard(GameBoard board)`
- `void displayError(String message)`
- `void displaySetupComplete()`

---

## 10. `GameController`

**Fields**
- `GameModel model`
- `ConsoleView view`
- `SetupController setupController`

**Methods**
- `void run()`
- `void startGame()`

---

## 11. `SetupController`

**Fields**
- `GameModel model`
- `ConsoleView view`

**Methods**
- `void runSetup()`
- `void collectPlayerInfo()`
- `void handleTerritoryClaiming()`
- `void handleInitialArmyPlacement()`
- `void completeSetup()`

---

## 12. `GameState`

**Values**
- `SETUP`
- `REINFORCEMENT`

---

## 13. `CardType`

**Values**
- `INFANTRY`
- `CAVALRY`
- `ARTILLERY`
- `WILD`

---

## 14. `PlayerColor`

**Values**
- `RED`
- `BLUE`
- `GREEN`
- `YELLOW`
- `BLACK`
- `PURPLE`