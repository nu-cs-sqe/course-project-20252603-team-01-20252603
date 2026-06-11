# Risk Setup Phase — Design

## `RiskGame`
**Methods**
- `main(args: String[]): void`

---

## `GameController`
**Fields**
- `model: GameModel`
- `view: ConsoleView`
- `setupController: SetupController`

**Methods**
- `run(): void`
- `startGame(): void`

---

## `SetupController`
**Fields**
- `model: GameModel`
- `view: ConsoleView`

**Methods**
- `runSetup(): void`
- `collectPlayerInfo(): void`
- `handleTerritoryClaiming(): void`
- `handleInitialArmyPlacement(): void`
- `completeSetup(): void`

---

## `SetupManager`
**Methods**
- `isValidPlayerCount(count: int): boolean`
- `calculateStartingArmies(playerCount: int): int`
- `areColorsUnique(colors: List<PlayerColor>): boolean`
- `allTerritoriesClaimed(board: GameBoard): boolean`
- `allPlayersPlacedArmies(players: List<Player>): boolean`

---

## `GameModel`
**Fields**
- `players: List<Player>`
- `board: GameBoard`
- `deck: Deck`
- `currentPlayerIndex: int`
- `gameState: GameState`
- `setupManager: SetupManager`

**Methods**
- `startGame(playerNames: List<String>, colors: List<PlayerColor>): boolean`
- `claimTerritory(player: Player, territoryName: String): boolean`
- `placeInitialArmies(player: Player, territoryName: String, pieces: List<ArmyPiece>): boolean`
- `finishSetup(): boolean`
- `getCurrentPlayerName(): String`
- `nextSetupPlayer(): Player`
- `isSetupComplete(): boolean`
- `getGameState(): GameState`
- `advanceGameState(): void`

---

## `GameBoard`
**Fields**
- `continents: List<Continent>`
- `territories: List<Territory>`

**Methods**
- `initializeBoard(): void`
- `getTerritoryByName(name: String): Territory`
- `isTerritoryUnclaimed(name: String): boolean`
- `claimTerritory(name: String, player: Player): boolean`
- `allTerritoriesClaimed(): boolean`
- `getUnclaimedTerritories(): List<Territory>`

---

## `Player`
**Fields**
- `name: String`
- `color: PlayerColor`
- `territories: List<Territory>`
- `hand: List<RiskCard>`
- `availableArmies: int`
- `eliminated: boolean`

**Methods**
- `addTerritory(territory: Territory): void`
- `ownsTerritory(territory: Territory): boolean`
- `reinforceTerritory(territory: Territory, pieces: List<ArmyPiece>): boolean`
- `hasAvailableArmies(): boolean`
- `getAvailableArmies(): int`
- `getTerritoryCount(): int`

---

## `Territory`
**Fields**
- `name: String`
- `owner: Player`
- `pieces: List<ArmyPiece>`
- `continent: Continent`
- `adjacentTerritories: List<Territory>`

**Methods**
- `setOwner(player: Player): void`
- `isOwnedBy(player: Player): boolean`
- `isUnclaimed(): boolean`
- `placeArmies(pieces: List<ArmyPiece>): boolean`
- `getArmyCount(): int`
- `getPieces(): List<ArmyPiece>`
- `getName(): String`

---

## `Continent`
**Fields**
- `name: String`
- `territories: List<Territory>`
- `bonusArmies: int`

**Methods**
- `containsTerritory(territory: Territory): boolean`

---

## `Deck`
**Fields**
- `cards: List<RiskCard>`
- `discardPile: List<RiskCard>`
- `globalTradeInCount: int`

**Methods**
- `initializeClassicDeck(territories: List<Territory>): void`
- `shuffle(): void`

---

## `RiskCard`
**Fields**
- `territory: Territory`
- `type: CardType`
- `wild: boolean`

**Methods**
- `getType(): CardType`
- `isWild(): boolean`

---

## `ArmyPiece`
**Fields**
- `type: ArmyType`

**Methods**
- `getValue(): int`
- `getType(): ArmyType`

---

## `ConsoleView`
**Methods**
- `displayWelcomeMessage(): void`
- `displaySetupInstructions(): void`
- `promptNumberOfPlayers(): int`
- `promptPlayerName(playerNumber: int): String`
- `promptPlayerColor(playerName: String): PlayerColor`
- `promptTerritoryChoice(player: Player, territories: List<Territory>): String`
- `promptArmyCount(player: Player, max: int): int`
- `displayBoard(board: GameBoard): void`
- - `displayCurrentPlayer(currentPlayerName: String): void`
- `displayError(message: String): void`
- `displaySetupComplete(): void`

---

## Enums
- `GameState`: `SETUP_CLAIM`, `SETUP_PLACE`
- `PlayerColor`: `RED`, `BLUE`, `GREEN`, `YELLOW`, `BLACK`, `PURPLE`
- `ArmyType`: `INFANTRY`, `CAVALRY`, `ARTILLERY`
- `CardType`: `INFANTRY`, `CAVALRY`, `ARTILLERY`, `WILD`