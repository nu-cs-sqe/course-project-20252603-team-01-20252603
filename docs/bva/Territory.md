# Boundary Value Analysis for `Territory` class

### Method under test: `isUnclaimed()`

- **TC1: New territory has no owner** ( :white_check_mark: )
    - **State of the system**: Create a new `Territory`; `owner == null`.
    - **Expected output**: `isUnclaimed()` returns `true`.

- **TC2: Territory with owner is not unclaimed** ( :white_check_mark: )
    - **State of the system**: Create a `Territory`; call `setOwner(player)`.
    - **Expected output**: `isUnclaimed()` returns `false`.

---

### Method under test: `setOwner(Player player)`

- **TC3: Set owner from null to valid player** ( :white_check_mark: )
    - **State of the system**: Territory starts unclaimed; call `setOwner(player1)`.
    - **Expected output**: `getOwner()` returns `player1`.

- **TC4: Replace existing owner with another player** ( :white_check_mark: )
    - **State of the system**: Territory owner is `player1`; call `setOwner(player2)`.
    - **Expected output**: `getOwner()` returns `player2`.

- **TC5: Set owner to null** ( :white_check_mark: )
    - **State of the system**: Territory owner is `player1`; call `setOwner(null)`.
    - **Expected output**: Either reject with error or make territory unclaimed. You should decide this behavior explicitly.

---

### Method under test: `isOwnedBy(Player player)`

- **TC6: Same player object owns territory** ( :white_check_mark: )
    - **State of the system**: Territory owner is `player1`; call `isOwnedBy(player1)`.
    - **Expected output**: Returns `true`.

- **TC7: Different player object does not own territory** ( :white_check_mark: )
    - **State of the system**: Territory owner is `player1`; call `isOwnedBy(player2)`.
    - **Expected output**: Returns `false`.

- **TC8: Null player checked against owned territory** ( :white_check_mark: )
    - **State of the system**: Territory owner is `player1`; call `isOwnedBy(null)`.
    - **Expected output**: Returns `false`.

- **TC9: Null player checked against unclaimed territory** ( :white_check_mark: )
    - **State of the system**: Territory owner is `null`; call `isOwnedBy(null)`.
    - **Expected output**: Should return `false`, not `true`. This is important if your code uses `owner == player`.

---

### Method under test: `addArmies(int count)`

- **TC10: Add negative armies** ( :white_check_mark: )
    - **State of the system**: Territory has `armyCount = 0`; call `addArmies(-1)`.
    - **Expected output**: Returns `false`; `armyCount` remains `0`.

- **TC11: Add zero armies** ( :white_check_mark: )
    - **State of the system**: Territory has `armyCount = 0`; call `addArmies(0)`.
    - **Expected output**: Returns `false`; `armyCount` remains `0`.

- **TC12: Add one army** ( :white_check_mark: )
    - **State of the system**: Territory has `armyCount = 0`; call `addArmies(1)`.
    - **Expected output**: Returns `true`; `armyCount` becomes `1`.

- **TC13: Add more than one army** ( :white_check_mark: )
    - **State of the system**: Territory has `armyCount = 0`; call `addArmies(3)`.
    - **Expected output**: Returns `true`; `armyCount` becomes `3`.

- **TC14: Add armies to nonzero existing army count** ( :white_check_mark: )
    - **State of the system**: Territory has `armyCount = 2`; call `addArmies(3)`.
    - **Expected output**: Returns `true`; `armyCount` becomes `5`.

---

### Method under test: `getArmyCount()`

- **TC15: Initial army count is zero** ( :white_check_mark: )
    - **State of the system**: Create a new `Territory`.
    - **Expected output**: `getArmyCount()` returns `0`.

- **TC16: Army count after valid addition** ( :white_check_mark: )
    - **State of the system**: Call `addArmies(1)`.
    - **Expected output**: `getArmyCount()` returns `1`.

---

### Method under test: `getName()`

- **TC17: Normal territory name** ( :white_check_mark: )
    - **State of the system**: Create `Territory("Alaska", continent)`.
    - **Expected output**: `getName()` returns `"Alaska"`.

- **TC18: Empty territory name** ( :white_check_mark: )
    - **State of the system**: Create `Territory("", continent)`.
    - **Expected output**: Should be rejected if empty names are invalid.

---

### Method under test: `getOwner()`

- **TC19: Owner before claiming** ( implemented in TC1 )
    - **State of the system**: New territory.
    - **Expected output**: `getOwner()` returns `null`.

- **TC20: Owner after claiming** ( implemented in TC3 )
    - **State of the system**: Call `setOwner(player1)`.
    - **Expected output**: `getOwner()` returns `player1`.

---

### Method under test: `getContinent()`

- **TC21: Territory has assigned continent** ( :x: )
    - **State of the system**: Create `Territory("Alaska", northAmerica)`.
    - **Expected output**: `getContinent()` returns `northAmerica`.

- **TC22: Territory created with null continent** ( :x: )
    - **State of the system**: Create `Territory("Alaska", null)`.
    - **Expected output**: Should be rejected if every territory must belong to a continent.