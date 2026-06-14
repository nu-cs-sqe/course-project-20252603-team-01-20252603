# BVA Analysis — `GameModel`


### Method under test: `GameModel()` *(constructor)*

- **TC1: GameModel constructs without error** ( :white_check_mark: )
    - **State of the system**: `new GameModel()` called
    - **Expected output**: Object created; `getContinents()` returns an empty list before initialization

---

### Method under test: `initializeContinentsAndTerritories()`

- **TC2: Creates exactly 6 continents** ( :white_check_mark: )
    - **State of the system**: `GameModel` constructed; `initializeContinentsAndTerritories()` called
    - **Expected output**: `getContinents().size()` returns `6`

- **TC3: Creates exactly 42 territories across all continents** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: Sum of `continent.getTerritories().size()` across all 6 continents equals `42`

    
- **TC5: All territories start with zero armies** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: Every territory across all continents returns `getArmyCount() == 0`

- **TC6: Each continent has the correct territory count** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: North America = 9, South America = 4, Europe = 7, Africa = 6, Asia = 12, Australia = 4

- **TC7: Each continent has the correct bonus army value** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: North America = 5, South America = 2, Europe = 5, Africa = 3, Asia = 7, Australia = 2

- **TC8: Every territory is assigned to exactly one continent** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: No territory appears in more than one continent's territory list; total unique territories = 42

- **TC9: Every territory has at least one adjacent territory** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: Every territory's `getAdjacentTerritories().size()` is greater than `0`

- **TC10: Adjacency is reciprocal for all territories** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: For every territory A, if B is in A's adjacency list then A is in B's adjacency list

- **TC11: Alaska is adjacent to Kamchatka** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: Alaska's adjacency list contains Kamchatka; Kamchatka's adjacency list contains Alaska

- **TC12: No territory is adjacent to itself** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: For every territory, its adjacency list does not contain itself

- **TC13: Deck is initialized with 44 cards after board initialization** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: `getDeck().size()` returns `44`

- **TC14: Deck is shuffled — draw pile is ready** ( :white_check_mark: )
    - **State of the system**: `initializeContinentsAndTerritories()` called
    - **Expected output**: `getDeck().isEmpty()` returns `false`

---

### Method under test: `setPlayerCount(int count)`

- **TC15: Rejects player count below minimum** ( :white_check_mark: )
    - **State of the system**: `GameModel` constructed; `setPlayerCount(2)` called
    - **Expected output**: Returns `false`; player count is not accepted because valid player counts are in `[3, 6]`

- **TC16: Accepts minimum player count** ( :white_check_mark: )
    - **State of the system**: `GameModel` constructed; `setPlayerCount(3)` called
    - **Expected output**: Returns `true`; player count is stored as `3`

- **TC17: Accepts maximum player count** ( :white_check_mark: )
    - **State of the system**: `GameModel` constructed; `setPlayerCount(6)` called
    - **Expected output**: Returns `true`; player count is stored as `6`

- **TC18: Rejects player count above maximum** ( :white_check_mark: )
    - **State of the system**: `GameModel` constructed; `setPlayerCount(7)` called
    - **Expected output**: Returns `false`; player count is not accepted because valid player counts are in `[3, 6]`

---

### Method under test: `addPlayer(String name, PlayerColor color)`

- **TC19: Adds first player with correct minimum-count infantry** ( :white_check_mark: )
    - **State of the system**: `setPlayerCount(3)` has returned `true`; `addPlayer("Player 1", PlayerColor.RED)` called
    - **Expected output**: Returns a player named `"Player 1"` with color `RED` and `35` available Infantry

- **TC20: Adds player with correct four-player infantry** ( :white_check_mark: )
    - **State of the system**: `setPlayerCount(4)` has returned `true`; `addPlayer("Player 1", PlayerColor.BLUE)` called
    - **Expected output**: Returns a player with `30` available Infantry

- **TC21: Adds player with correct five-player infantry** ( :white_check_mark: )
    - **State of the system**: `setPlayerCount(5)` has returned `true`; `addPlayer("Player 1", PlayerColor.GREEN)` called
    - **Expected output**: Returns a player with `25` available Infantry

- **TC22: Adds player with correct maximum-count infantry** ( :white_check_mark: )
    - **State of the system**: `setPlayerCount(6)` has returned `true`; `addPlayer("Player 1", PlayerColor.YELLOW)` called
    - **Expected output**: Returns a player with `20` available Infantry

- **TC24: Rejects adding more players than configured** ( :white_check_mark: )
    - **State of the system**: `setPlayerCount(3)` has returned `true`; three players have already been added
    - **Expected output**: A fourth `addPlayer(...)` call is rejected; player list remains size `3`

---

### Method under test: `setCurrentPlayerIndex(int index)`

- **TC28: Accepts first player index** ( :white_check_mark: )
    - **State of the system**: Three players have been registered; `setCurrentPlayerIndex(0)` called
    - **Expected output**: Current player becomes the first registered player

- **TC29: Accepts last valid player index** ( :white_check_mark: )
    - **State of the system**: Three players have been registered; `setCurrentPlayerIndex(2)` called
    - **Expected output**: Current player becomes the third registered player

- **TC30: Rejects index below valid range** ( :white_check_mark: )
    - **State of the system**: Three players have been registered; `setCurrentPlayerIndex(-1)` called
    - **Expected output**: Current player index is not changed because valid indices are in `[0, playerCount - 1]`

- **TC31: Rejects index above valid range** ( :white_check_mark: )
    - **State of the system**: Three players have been registered; `setCurrentPlayerIndex(3)` called
    - **Expected output**: Current player index is not changed because `3` is one past the last valid index

---

### Method under test: `getCurrentPlayerName()`

- **TC32: Returns selected first player name** ( :white_check_mark: )
    - **State of the system**: Three players have been registered; current player index is set to `0`
    - **Expected output**: Returns the first registered player name

- **TC33: Returns selected last player name** ( :white_check_mark: )
    - **State of the system**: Three players have been registered; current player index is set to `2`
    - **Expected output**: Returns the third registered player name

---

### Method under test: `claimTerritoryDuringSetup(Player player, Territory territory, HashMap<ArmyType, Integer> pieces)`

- **TC34: Claim unclaimed territory with exactly one Infantry** ( :white_check_mark: )
    - **State of the system**: Territory is unclaimed; player has available armies containing at least `INFANTRY -> 1`; `claimTerritoryDuringSetup()` is called with a pieces map containing exactly `INFANTRY -> 1`
    - **Expected output**: Method returns `true`; territory owner is set to the player; territory contains exactly one Infantry; player owns the territory; player's available Infantry decreases by one

- **TC35: Cannot claim already claimed territory** ( :white_check_mark: )
    - **State of the system**: Territory is already owned by `playerOne`; `playerTwo` attempts to claim the same territory with exactly `INFANTRY -> 1`
    - **Expected output**: Method returns `false`; territory owner remains `playerOne`; territory Infantry count remains unchanged; `playerTwo` does not gain the territory; `playerTwo`'s available Infantry count remains unchanged

- **TC36: Cannot claim territory with zero Infantry** ( :white_check_mark: )
    - **State of the system**: Territory is unclaimed; player has available Infantry; `claimTerritoryDuringSetup()` is called with a pieces map containing `INFANTRY -> 0`
    - **Expected output**: Method returns `false`; territory remains unclaimed; no Infantry is placed; player does not gain the territory; player's available Infantry count remains unchanged

- **TC37: Cannot claim territory with more than one Infantry** ( :white_check_mark: )
    - **State of the system**: Territory is unclaimed; player has available Infantry; `claimTerritoryDuringSetup()` is called with a pieces map containing `INFANTRY -> 2`
    - **Expected output**: Method returns `false`; territory remains unclaimed; no Infantry is placed; player does not gain the territory; player's available Infantry count remains unchanged

- **TC38: Cannot claim territory when player lacks available Infantry** ( :white_check_mark: )
    - **State of the system**: Territory is unclaimed; player has available armies containing `INFANTRY -> 0`; `claimTerritoryDuringSetup()` is called with a pieces map containing exactly `INFANTRY -> 1`
    - **Expected output**: Method returns `false`; territory remains unclaimed; no Infantry is placed; player does not gain the territory

---

### Method under test: `areAllTerritoriesClaimed()`

- **TC39: Returns false when no territories are claimed** ( :white_check_mark: )
    - **State of the system**: Game board has been initialized; all territories are still unclaimed
    - **Expected output**: Returns `false`

- **TC40: Returns false when one territory remains unclaimed** ( :white_check_mark: )
    - **State of the system**: 41 territories are claimed and exactly 1 territory is still unclaimed
    - **Expected output**: Returns `false`

- **TC41: Returns true when all territories are claimed** ( :white_check_mark: )
    - **State of the system**: All 42 territories have been claimed
    - **Expected output**: Returns `true`

---

### Method under test: `advanceCurrentPlayerIndex()`

- **TC34: Advances from first player to second player** ( :white_check_mark: )
    - **State of the system**: Three-player game has been created; current player index is `0`
    - **Expected output**: Current player advances to index `1`; `getCurrentPlayerName()` returns the second player name

- **TC35: Advances from middle player to next player** ( :white_check_mark: )
    - **State of the system**: Three-player game has been created; current player index is `1`
    - **Expected output**: Current player advances to index `2`; `getCurrentPlayerName()` returns the third player name

- **TC36: Wraps from last player back to first player** ( :white_check_mark: )
    - **State of the system**: Three-player game has been created; current player index is `2`
    - **Expected output**: Current player advances to index `0`; `getCurrentPlayerName()` returns the first player name

- **TC37: Does not advance when no players are registered** ( :white_check_mark: )
    - **State of the system**: GameModel has no registered players
    - **Expected output**: Current player remains unavailable; method does not change player state
---

### Method under test: `getCurrentPlayerName()`

- **TC42: Returns first player's name when current player index is first player** ( :white_check_mark: )
    - **State of the system**: Three-player game has been created; current player index is `0`
    - **Expected output**: Returns `"Player 1"`

- **TC43: Returns middle player's name when current player index is middle player** ( :white_check_mark: )
    - **State of the system**: Three-player game has been created; current player index is `1`
    - **Expected output**: Returns `"Player 2"`

---

### Method under test: `getUnclaimedTerritoriesByContinent()`

- **TC46: Returns all territories grouped by continent when no territories are claimed** ( :white_check_mark: )
    - **State of the system**: Game board has been initialized; all territories are unclaimed
    - **Expected output**: Returns a string containing continent names and all unclaimed territory names grouped under their continents

- **TC47: Excludes claimed territory from unclaimed territory display** ( :white_check_mark: )
    - **State of the system**: Game board has been initialized; `"Alaska"` has been claimed by the current player
    - **Expected output**: Returned string contains `"North America"` but does not contain `"Alaska"`

---

### Method under test: `getCurrentPlayerTerritoriesByContinent()`

- **TC48: Returns empty grouped display when current player owns no territories** ( :white_check_mark: )
    - **State of the system**: Three-player game has been created; current player owns no territories
    - **Expected output**: Returns a string that does not list any territory names for the current player

- **TC49: Returns current player's owned territories grouped by continent** ( :white_check_mark: )
    - **State of the system**: Current player has claimed `"Alaska"` in `"North America"`
    - **Expected output**: Returned string contains `"North America"` and `"Alaska"`

- **TC50: Excludes territories owned by other players** ( :white_check_mark:)
    - **State of the system**: Player 1 owns `"Alaska"`; current player is Player 2
    - **Expected output**: Returned string does not contain `"Alaska"`

---

### Method under test: `hasCurrentPlayerAvailableArmies()`

- **TC51: Current player has zero armies remaining** ( :white_check_mark: )
    - **State of the system**: Current player has placed all setup Infantry and has `0` available Infantry
    - **Expected output**: Returns `false`, allowing setup to skip this player during remaining-army placement

- **TC52: Current player has exactly one army remaining** ( :white_check_mark: )
    - **State of the system**: Current player has exactly `1` available Infantry left after territory claiming
    - **Expected output**: Returns `true`, allowing setup to prompt this player for one final placement

- **TC53: Current player has more than one army remaining** ( :white_check_mark: )
    - **State of the system**: Current player has multiple available Infantry left after territory claiming
    - **Expected output**: Returns `true`, allowing setup to prompt this player for army placement

---

### Method under test: `addArmiesDuringSetup(String territoryName, HashMap<ArmyType, Integer> pieces)`

- **TC54: Adds exactly one Infantry to current player's owned territory** ( :white_check_mark: )
    - **State of the system**: All territories are claimed; current player owns `"Alaska"`; current player has at least `1` available Infantry; `pieces` contains exactly `INFANTRY -> 1`
    - **Expected output**: Returns `true`; `"Alaska"` army count increases by `1`; current player's available Infantry decreases by `1`

- **TC55: Adds final remaining Infantry to owned territory** ( :white_check_mark: )
    - **State of the system**: Current player owns the selected territory and has exactly `1` available Infantry
    - **Expected output**: Returns `true`; selected territory gains `1` Infantry; current player's available Infantry becomes `0`

- **TC56: Rejects territory owned by another player** ( :white_check_mark: )
    - **State of the system**: Current player selects a territory that exists but is owned by another player; current player has available Infantry
    - **Expected output**: Returns `false`; territory army count is unchanged; current player's available Infantry is unchanged

- **TC57: Rejects unknown territory name** ( :white_check_mark: )
    - **State of the system**: Current player enters a territory name that does not match any board territory
    - **Expected output**: Returns `false`; no territory army count changes; current player's available Infantry is unchanged

- **TC58: Rejects zero Infantry placement** ( :white_check_mark: )
    - **State of the system**: Current player owns the selected territory; `pieces` contains `INFANTRY -> 0`
    - **Expected output**: Returns `false`; selected territory army count is unchanged; current player's available Infantry is unchanged

- **TC59: Rejects more than one Infantry placement** ( :white_check_mark: )
    - **State of the system**: Current player owns the selected territory; `pieces` contains `INFANTRY -> 2`
    - **Expected output**: Returns `false`; selected territory army count is unchanged; current player's available Infantry is unchanged

- **TC60: Rejects placement when current player has no armies remaining** ( :white_check_mark: )
    - **State of the system**: Current player owns the selected territory but has `0` available Infantry
    - **Expected output**: Returns `false`; selected territory army count is unchanged; current player's available Infantry remains `0`
### Method under test: `placeArmiesDuringReinforcement(String territoryName, HashMap<ArmyType, Integer> pieces)`

- **TC61: Place one Infantry on territory owned by current player** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; current player has available armies containing `INFANTRY -> 1`; `pieces` contains `INFANTRY -> 1`
    - **Expected output**: Method returns `true`; selected territory army count increases; current player's available army pool decreases to zero

- **TC62: Place multiple Infantry on territory owned by current player** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; current player has available armies containing `INFANTRY -> 3`; `pieces` contains `INFANTRY -> 3`
    - **Expected output**: Method returns `true`; selected territory army count increases by three; current player's available army pool decreases to zero

- **TC63: Place mixed army types on territory owned by current player** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; current player has enough equivalent available army value; `pieces` contains Infantry, Cavalry, and Artillery
    - **Expected output**: Method returns `true`; selected territory receives all requested army pieces; current player's available army pool is reduced by equivalent value

- **TC64: Cannot place zero total armies** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; `pieces` contains `INFANTRY -> 0`, `CAVALRY -> 0`, and `ARTILLERY -> 0`
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC65: Cannot place negative Infantry count** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; `pieces` contains `INFANTRY -> -1`
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC66: Cannot place negative Cavalry count** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; `pieces` contains `CAVALRY -> -1`
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC67: Cannot place negative Artillery count** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; `pieces` contains `ARTILLERY -> -1`
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC68: Cannot place more total army value than available** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; current player has 10 total army value; `pieces` is worth 15 total army value
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC69: Cannot place armies on territory owned by another player** ( :white_check_mark: )
    - **State of the system**: Player 1 owns `"Alaska"`; current player is Player 2; Player 2 has enough available armies
    - **Expected output**: Method returns `false`; `"Alaska"` army count does not change; Player 2's available army pool remains unchanged

- **TC70: Cannot place armies on unowned territory** ( :white_check_mark: )
    - **State of the system**: `"Alaska"` is unclaimed; current player has enough available armies
    - **Expected output**: Method returns `false`; `"Alaska"` army count does not change; current player's available army pool remains unchanged

---

### Method under test: `currentPlayerHasAvailableArmies()`

- **TC71: Returns false when current player has zero available army value** ( :white_check_mark: )
    - **State of the system**: Current player has no available Infantry, Cavalry, or Artillery
    - **Expected output**: Returns `false`

- **TC72: Returns true when current player has exactly one Infantry available** ( :white_check_mark: )
    - **State of the system**: Current player has available armies containing `INFANTRY -> 1`
    - **Expected output**: Returns `true`

- **TC73: Returns true when current player has only Cavalry available** ( :white_check_mark: )
    - **State of the system**: Current player has available armies containing `CAVALRY -> 1`
    - **Expected output**: Returns `true`

- **TC74: Returns true when current player has only Artillery available** ( :white_check_mark: )
    - **State of the system**: Current player has available armies containing `ARTILLERY -> 1`
    - **Expected output**: Returns `true`

---

### Method under test: `fortifyTerritory(String sourceName, String destinationName, int armyCount)`

- **TC75: Moves one army between adjacent owned territories** ( :white_check_mark: )
    - **State of the system**: Current player owns adjacent source and destination territories; source has exactly `2` armies; `armyCount` is `1`
    - **Expected output**: Returns `true`; source army count decreases to `1`; destination army count increases by `1`

- **TC76: Moves multiple armies while leaving one behind** ( :white_check_mark: )
    - **State of the system**: Current player owns connected source and destination territories; source has `4` armies; `armyCount` is `3`
    - **Expected output**: Returns `true`; source keeps exactly `1` army; destination gains `3` armies

- **TC77: Rejects zero armies moved** ( :white_check_mark: )
    - **State of the system**: Current player owns connected source and destination territories; source has more than one army; `armyCount` is `0`
    - **Expected output**: Returns `false`; source and destination army counts do not change

- **TC78: Rejects negative armies moved** ( :white_check_mark: )
    - **State of the system**: Current player owns connected source and destination territories; source has more than one army; `armyCount` is `-1`
    - **Expected output**: Returns `false`; source and destination army counts do not change

- **TC79: Rejects moving all armies from source** ( :white_check_mark: )
    - **State of the system**: Current player owns connected source and destination territories; source has `3` armies; `armyCount` is `3`
    - **Expected output**: Returns `false`; source and destination army counts do not change because at least one army must remain

- **TC80: Rejects moving more armies than source can allow** ( :white_check_mark: )
    - **State of the system**: Current player owns connected source and destination territories; source has `3` armies; `armyCount` is `4`
    - **Expected output**: Returns `false`; source and destination army counts do not change

- **TC81: Rejects source with only one army** ( :white_check_mark: )
    - **State of the system**: Current player owns connected source and destination territories; source has exactly `1` army; `armyCount` is `1`
    - **Expected output**: Returns `false`; source and destination army counts do not change

- **TC82: Rejects source not owned by current player** ( :white_check_mark: )
    - **State of the system**: Source territory is owned by another player; destination is owned by current player; `armyCount` is positive
    - **Expected output**: Returns `false`; source and destination army counts do not change

- **TC83: Rejects destination not owned by current player** ( :white_check_mark: )
    - **State of the system**: Source territory is owned by current player and has more than one army; destination is owned by another player
    - **Expected output**: Returns `false`; source and destination army counts do not change

- **TC84: Rejects same source and destination territory** ( :white_check_mark: )
    - **State of the system**: Current player owns the selected territory with more than one army; the same territory name is passed as source and destination
    - **Expected output**: Returns `false`; territory army count does not change

- **TC85: Allows path through exactly one owned territory** ( :white_check_mark: )
    - **State of the system**: Current player owns source, one intermediate adjacent territory, and destination; source and destination are not directly adjacent
    - **Expected output**: Returns `true`; armies move from source to destination through the owned path

- **TC86: Allows path through more than one owned territory** ( :white_check_mark: )
    - **State of the system**: Current player owns source, multiple intermediate connected territories, and destination
    - **Expected output**: Returns `true`; armies move from source to destination through the owned path

- **TC87: Rejects path blocked by another player's territory** ( :white_check_mark: )
    - **State of the system**: Source and destination are connected on the board only through at least one territory owned by another player
    - **Expected output**: Returns `false`; source and destination army counts do not change

- **TC88: Rejects disconnected owned territories** ( :white_check_mark: )
    - **State of the system**: Current player owns source and destination, but there is no connected path of current-player-owned territories between them
    - **Expected output**: Returns `false`; source and destination army counts do not change

---

### Method under test: `hasOwnedPath(Territory source, Territory destination, Player player)`

- **TC89: Finds destination immediately adjacent to source** ( :white_check_mark: )
    - **State of the system**: Source and destination are adjacent and both owned by the current player
    - **Expected output**: Returns `true`

- **TC90: Finds destination at the end of a longer owned path** ( :white_check_mark: )
    - **State of the system**: Source and destination are connected through more than one intermediate territory owned by the current player
    - **Expected output**: Returns `true`

- **TC91: Handles circular owned territory structure** ( :white_check_mark: )
    - **State of the system**: Current player's owned territories contain a cycle in the adjacency graph
    - **Expected output**: Search terminates and returns whether the destination is reachable without looping forever

- **TC92: Does not traverse enemy-owned territory** ( :white_check_mark: )
    - **State of the system**: Destination can only be reached by crossing a territory not owned by the current player
    - **Expected output**: Returns `false`
