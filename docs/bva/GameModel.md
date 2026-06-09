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

- **TC23: Rejects duplicate color** ( :white_check_mark: )
    - **State of the system**: `setPlayerCount(3)` has returned `true`; one player with color `RED` already exists
    - **Expected output**: A second call using `PlayerColor.RED` is rejected; no duplicate-color player is added

- **TC24: Rejects adding more players than configured** ( :white_check_mark: )
    - **State of the system**: `setPlayerCount(3)` has returned `true`; three players have already been added
    - **Expected output**: A fourth `addPlayer(...)` call is rejected; player list remains size `3`

---

### Method under test: `showAvailableColors()`

- **TC25: Shows all colors before registration** ( :white_check_mark: )
    - **State of the system**: `GameModel` constructed; no players have been added
    - **Expected output**: Returns all six colors: Red, Blue, Green, Yellow, Black, Purple

- **TC26: Excludes a chosen color** ( :white_check_mark: )
    - **State of the system**: One player with color `RED` has already been added
    - **Expected output**: Returned colors do not include `RED`; the remaining five colors are available

- **TC27: Shows one remaining color when almost full** ( :white_check_mark: )
    - **State of the system**: Five players with five distinct colors have already been added
    - **Expected output**: Returned color list contains exactly the one unchosen color

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

### Method under test: `getCurrentPlayer()`

- **TC32: Returns selected first player** ( :x: )
    - **State of the system**: Three players have been registered; current player index is set to `0`
    - **Expected output**: Returns the first registered player

- **TC33: Returns selected last player** ( :x: )
    - **State of the system**: Three players have been registered; current player index is set to `2`
    - **Expected output**: Returns the third registered player
