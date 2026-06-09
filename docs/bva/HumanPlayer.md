# BVA Analysis — `HumanPlayer`


### Method under test: `HumanPlayer(String name, PlayerColor color, int startingInfantry)`

- **TC1: Constructs player with minimum setup infantry** ( :white_check_mark: )
    - **State of the system**: `new HumanPlayer("Player 1", PlayerColor.RED, 20)` called for a six-player game
    - **Expected output**: Object is created with name `"Player 1"`, color `RED`, and `20` available Infantry

- **TC2: Constructs player with maximum setup infantry** ( :white_check_mark: )
    - **State of the system**: `new HumanPlayer("Player 1", PlayerColor.BLUE, 35)` called for a three-player game
    - **Expected output**: Object is created with name `"Player 1"`, color `BLUE`, and `35` available Infantry

---

### Method under test: `getName()`

- **TC3: Returns registered player name** ( :white_check_mark: )
    - **State of the system**: Human player constructed with name `"Player 1"`
    - **Expected output**: Returns `"Player 1"`

---

### Method under test: `getColor()`

- **TC4: Returns registered player color** ( :white_check_mark: )
    - **State of the system**: Human player constructed with color `PlayerColor.RED`
    - **Expected output**: Returns `PlayerColor.RED`


### Method under test: `addTerritory(Territory territory)`

- **TC8: Add first claimed territory** ( :x: )
    - **State of the system**: Human player owns no territories; `addTerritory(alaska)` is called
    - **Expected output**: Player territory count becomes `1`; `ownsTerritory(alaska)` returns `true`

- **TC9: Add another claimed territory** ( :x: )
    - **State of the system**: Human player already owns one territory; `addTerritory(alberta)` is called
    - **Expected output**: Player territory count becomes `2`; `ownsTerritory(alberta)` returns `true`; previously owned territory is still owned

---

### Method under test: `ownsTerritory(Territory territory)`

- **TC10: Returns true for owned territory** ( implemented in TC8 )
    - **State of the system**: `addTerritory(alaska)` has been called
    - **Expected output**: `ownsTerritory(alaska)` returns `true`

- **TC11: Returns false for territory not owned by player** ( :x: )
    - **State of the system**: Human player owns `alaska`; `alberta` has not been added to this player
    - **Expected output**: `ownsTerritory(alberta)` returns `false`

---

### Method under test: `getTerritoryCount()`

- **TC12: Returns zero before any territory is claimed** ( :x: )
    - **State of the system**: Human player has just been constructed
    - **Expected output**: `getTerritoryCount()` returns `0`

- **TC13: Returns one after first territory is claimed** ( implemented in TC8 )
    - **State of the system**: One territory has been added to the player
    - **Expected output**: `getTerritoryCount()` returns `1`

- **TC14: Returns more than one after multiple territories are claimed** ( implemented in TC9 )
    - **State of the system**: Two territories have been added to the player
    - **Expected output**: `getTerritoryCount()` returns `2`

---

### Method under test: `setAvailableArmies(HashMap<ArmyType, Integer> availableArmies)`

- **TC15: Set available armies to one Infantry** ( :x: )
    - **State of the system**: Human player has an available army map; `setAvailableArmies()` is called with a map containing `INFANTRY -> 1`
    - **Expected output**: Player available armies are updated to contain exactly one Infantry

- **TC16: Set available armies to multiple Infantry** ( :x: )
    - **State of the system**: Human player has an available army map; `setAvailableArmies()` is called with a map containing `INFANTRY -> 20`
    - **Expected output**: Player available armies are updated to contain exactly twenty Infantry

- **TC17: Set available armies to zero Infantry** ( :x: )
    - **State of the system**: Human player has an available army map; `setAvailableArmies()` is called with a map containing `INFANTRY -> 0`
    - **Expected output**: Player available armies are updated to contain zero Infantry

---

### Method under test: `hasAvailableArmies(HashMap<ArmyType, Integer> requiredArmies)`

- **TC18: Returns true when required Infantry is available** ( :x: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 20`; `hasAvailableArmies()` is called with `INFANTRY -> 1`
    - **Expected output**: Returns `true`

- **TC19: Returns true when required Infantry exactly equals available Infantry** ( :x: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 1`; `hasAvailableArmies()` is called with `INFANTRY -> 1`
    - **Expected output**: Returns `true`

- **TC20: Returns false when required Infantry is greater than available Infantry** ( :x: )
    - **State of the system**: Player has available armies containing `INFANTRY -> 0`; `hasAvailableArmies()` is called with `INFANTRY -> 1`
    - **Expected output**: Returns `false`

---

### Method under test: `getAvailableArmies()`

- **TC21: Returns available army map as display string when Infantry is available** ( :x: )
    - **State of the system**: Player available armies contain `INFANTRY -> 20`
    - **Expected output**: Returns a string containing `INFANTRY` and `20`

- **TC22: Returns available army map as display string when no Infantry is available** ( :x: )
    - **State of the system**: Player available armies contain `INFANTRY -> 0`
    - **Expected output**: Returns a string containing `INFANTRY` and `0`