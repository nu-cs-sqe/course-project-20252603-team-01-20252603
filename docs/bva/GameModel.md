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

### Method under test: `placeArmiesDuringReinforcement(String territoryName, HashMap<ArmyType, Integer> pieces)`

- **TC51: Place one Infantry on territory owned by current player** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; current player has available armies containing `INFANTRY -> 1`; `pieces` contains `INFANTRY -> 1`
    - **Expected output**: Method returns `true`; selected territory army count increases; current player's available army pool decreases to zero

- **TC52: Place multiple Infantry on territory owned by current player** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; current player has available armies containing `INFANTRY -> 3`; `pieces` contains `INFANTRY -> 3`
    - **Expected output**: Method returns `true`; selected territory army count increases by three; current player's available army pool decreases to zero

- **TC53: Place mixed army types on territory owned by current player** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; current player has enough equivalent available army value; `pieces` contains Infantry, Cavalry, and Artillery
    - **Expected output**: Method returns `true`; selected territory receives all requested army pieces; current player's available army pool is reduced by equivalent value

- **TC54: Cannot place zero total armies** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; `pieces` contains `INFANTRY -> 0`, `CAVALRY -> 0`, and `ARTILLERY -> 0`
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC55: Cannot place negative Infantry count** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; `pieces` contains `INFANTRY -> -1`
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC56: Cannot place negative Cavalry count** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; `pieces` contains `CAVALRY -> -1`
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC57: Cannot place negative Artillery count** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; `pieces` contains `ARTILLERY -> -1`
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC58: Cannot place more total army value than available** ( :white_check_mark: )
    - **State of the system**: Current player owns `"Alaska"`; current player has 10 total army value; `pieces` is worth 15 total army value
    - **Expected output**: Method returns `false`; selected territory army count does not change; current player's available army pool remains unchanged

- **TC59: Cannot place armies on territory owned by another player** ( :white_check_mark: )
    - **State of the system**: Player 1 owns `"Alaska"`; current player is Player 2; Player 2 has enough available armies
    - **Expected output**: Method returns `false`; `"Alaska"` army count does not change; Player 2's available army pool remains unchanged

- **TC60: Cannot place armies on unowned territory** ( :white_check_mark: )
    - **State of the system**: `"Alaska"` is unclaimed; current player has enough available armies
    - **Expected output**: Method returns `false`; `"Alaska"` army count does not change; current player's available army pool remains unchanged

---

### Method under test: `currentPlayerHasAvailableArmies()`

- **TC61: Returns false when current player has zero available army value** ( :white_check_mark: )
    - **State of the system**: Current player has no available Infantry, Cavalry, or Artillery
    - **Expected output**: Returns `false`

- **TC62: Returns true when current player has exactly one Infantry available** ( :white_check_mark: )
    - **State of the system**: Current player has available armies containing `INFANTRY -> 1`
    - **Expected output**: Returns `true`

- **TC63: Returns true when current player has only Cavalry available** ( :white_check_mark: )
    - **State of the system**: Current player has available armies containing `CAVALRY -> 1`
    - **Expected output**: Returns `true`

- **TC64: Returns true when current player has only Artillery available** ( :white_check_mark: )
    - **State of the system**: Current player has available armies containing `ARTILLERY -> 1`
    - **Expected output**: Returns `true`

---

### Method under test: `addArmiesToCurrentPlayerBasedOnContinents()`

- **TC65: Adds no bonus when current player owns no full continent** ( :white_check_mark: )
    - **State of the system**: Current player owns territories in multiple continents but does not fully own any continent; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry remains `0`

- **TC66: Adds Australia bonus when current player fully owns Australia** ( :x: )
    - **State of the system**: Current player owns all `4` Australia territories and no other fully controlled continent; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry increases to `2`

- **TC67: Adds South America bonus when current player fully owns South America** ( :x: )
    - **State of the system**: Current player owns all `4` South America territories and no other fully controlled continent; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry increases to `2`

- **TC68: Adds Africa bonus when current player fully owns Africa** ( :x: )
    - **State of the system**: Current player owns all `6` Africa territories and no other fully controlled continent; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry increases to `3`

- **TC69: Adds Europe bonus when current player fully owns Europe** ( :x: )
    - **State of the system**: Current player owns all `7` Europe territories and no other fully controlled continent; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry increases to `5`

- **TC70: Adds North America bonus when current player fully owns North America** ( :x: )
    - **State of the system**: Current player owns all `9` North America territories and no other fully controlled continent; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry increases to `5`

- **TC71: Adds Asia bonus when current player fully owns Asia** ( :x: )
    - **State of the system**: Current player owns all `12` Asia territories and no other fully controlled continent; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry increases to `7`

- **TC72: Adds bonuses for multiple fully controlled continents** ( :x: )
    - **State of the system**: Current player owns all Australia territories and all South America territories; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry increases to `4`

- **TC73: Adds bonus only for fully controlled continent when another continent is only partially owned** ( :x: )
    - **State of the system**: Current player owns all Australia territories and `3` of South America's `4` territories; current player has `0` available Infantry before continent bonus is applied
    - **Expected output**: Current player's available Infantry increases to `2`

---

### Method under test: `handleCardTradeIn(List<Integer> cardIndices)`

- **TC74: Empty card selection skips card trade-in** ( :x: )
    - **State of the system**: Current player has cards in hand, `numSetsTradedIn = 0`, and passes an empty card index list
    - **Expected output**: Returns `true`; no trade-in armies are added; current player's hand is unchanged; `numSetsTradedIn` remains `0`

- **TC75: `null` card selection skips card trade-in** ( :x: )
    - **State of the system**: Current player has cards in hand, `numSetsTradedIn = 0`, and passes `null` for card indices
    - **Expected output**: Returns `true`; no trade-in armies are added; current player's hand is unchanged; `numSetsTradedIn` remains `0`

- **TC76: Invalid card trade-in returns false and does not increment traded-set count** ( :x: )
    - **State of the system**: Current player has cards in hand, `numSetsTradedIn = 0`, and passes an invalid card selection
    - **Expected output**: Returns `false`; no trade-in armies are added; selected cards remain in hand; `numSetsTradedIn` remains `0`

- **TC77: First valid card trade-in adds first trade-in armies and increments traded-set count** ( :x: )
    - **State of the system**: Current player holds a valid trade-in set and `numSetsTradedIn = 0`
    - **Expected output**: Returns `true`; current player receives `4` Infantry from the trade-in; traded cards are removed from the hand; `numSetsTradedIn` increases to `1`

- **TC78: Second valid card trade-in uses incremented trade-in count** ( :x: )
    - **State of the system**: Current player holds a valid trade-in set and `numSetsTradedIn = 1`
    - **Expected output**: Returns `true`; current player receives `6` Infantry from the trade-in; traded cards are removed from the hand; `numSetsTradedIn` increases to `2`

- **TC79: Fourteenth valid card trade-in awards the maximum legal bonus** ( :x: )
    - **State of the system**: Current player holds a valid trade-in set and `numSetsTradedIn = 13`
    - **Expected output**: Returns `true`; current player receives `60` Infantry from the trade-in; traded cards are removed from the hand; `numSetsTradedIn` increases to `14`

- **TC80: Fifteenth trade-in is rejected because a 44-card deck supports at most 14 traded sets** ( :x: )
    - **State of the system**: Current player holds a valid trade-in set and `numSetsTradedIn = 14`
    - **Expected output**: `IllegalArgumentException` is raised with message `"Cannot trade cards after 14 sets because a 44-card deck supports at most 14 traded sets."`; current player's available armies and hand are unchanged; `numSetsTradedIn` remains `14`

---

### Method under test: `checkCardTradeInPossibility()`

- **TC81: Fewer than three cards does not allow a trade-in** ( :x: )
    - **State of the system**: Current player has `0`, `1`, or `2` cards in hand
    - **Expected output**: Returns `TradeInPossibility.NOT_ALLOWED`

- **TC82: Three cards with no valid set does not allow a trade-in** ( :x: )
    - **State of the system**: Current player has exactly `3` cards in hand, but those cards do not form a valid Risk set
    - **Expected output**: Returns `TradeInPossibility.NOT_ALLOWED`

- **TC83: Three cards with a valid set allows an optional trade-in** ( :x: )
    - **State of the system**: Current player has exactly `3` cards in hand, and those cards form a valid Risk set
    - **Expected output**: Returns `TradeInPossibility.ALLOWED`

- **TC84: Four cards with no valid set does not allow a trade-in** ( :x: )
    - **State of the system**: Current player has exactly `4` cards in hand, but no three-card subset forms a valid Risk set
    - **Expected output**: Returns `TradeInPossibility.NOT_ALLOWED`

- **TC85: Four cards with at least one valid set allows an optional trade-in** ( :x: )
    - **State of the system**: Current player has exactly `4` cards in hand, and at least one three-card subset forms a valid Risk set
    - **Expected output**: Returns `TradeInPossibility.ALLOWED`

- **TC86: Five cards requires a trade-in** ( :x: )
    - **State of the system**: Current player has exactly `5` cards in hand
    - **Expected output**: Returns `TradeInPossibility.REQUIRED`

- **TC87: More than five cards also requires a trade-in** ( :x: )
    - **State of the system**: Current player has `6` or more cards in hand
    - **Expected output**: Returns `TradeInPossibility.REQUIRED`
