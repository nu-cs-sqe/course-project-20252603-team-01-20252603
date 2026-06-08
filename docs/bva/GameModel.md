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