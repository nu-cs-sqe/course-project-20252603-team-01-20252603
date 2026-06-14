# BVA Analysis — `Territory`

---

### Method under test: `Territory(String name, Continent continent, List<Territory> adjacentTerritories)`

- **TC1: Valid construction** ( :white_check_mark: )
    - **State of the system**: Constructing `Territory("Alaska", northAmerica, List.of(northwestTerritory, alberta, kamchatka))`
    - **Expected output**: Object created; `getName()` returns `"Alaska"`; `getContinent()` returns `northAmerica`

  - **TC2: Adjacency list stored correctly** ( :white_check_mark: )
      - **State of the system**: Constructed with a list of 3 neighbours
      - **Expected output**: `getAdjacentTerritories().size()` returns `3`; list contains the correct territories

  - **TC3: Empty adjacency list** ( :white_check_mark: )
      - **State of the system**: Constructed with an empty `List<Territory>`
      - **Expected output**: `getAdjacentTerritories()` returns an empty list — no exception thrown
  
  - **TC8: New territory starts owned by NullPlayer** ( :white_check_mark: )
    - **State of the system**: Freshly constructed territory; no human player has claimed it yet
    - **Expected output**: Territory owner is a `NullPlayer`; `isUnclaimed()` returns `true`

  
---

### Method under test: `getName()`

- **TC4: Returns correct name** ( implemented in TC1 )
    - **State of the system**: Territory constructed with `name = "Alaska"`
    - **Expected output**: Returns `"Alaska"`

---

### Method under test: `isUnclaimed()`

- **TC5: Returns true at initialization** ( implemented in TC2 )
    - **State of the system**: Freshly constructed territory; owner never set
    - **Expected output**: Returns `true`

---

### Method under test: `getAdjacentTerritories()`

- **TC6: Returns correct adjacency list** ( implemented in TC3 )
    - **State of the system**: Territory constructed with 3 neighbours
    - **Expected output**: Returns list of size 3 containing the correct territories

  - **TC7: Returns empty list when no neighbours passed** ( implemented in TC4 )
      - **State of the system**: Territory constructed with empty adjacency list
      - **Expected output**: Returns empty list

---

### Method under test: `setOwner(Player player)`

- **TC9: Set owner from NullPlayer to HumanPlayer** ( :white_check_mark: )
    - **State of the system**: Territory owner is initially `NullPlayer`; `setOwner(playerOne)` is called with a `HumanPlayer`
    - **Expected output**: Territory owner is updated to `playerOne`; `isUnclaimed()` returns `false`; `isOwnedBy(playerOne)` returns `true`

- **TC10: Change owner from one HumanPlayer to another HumanPlayer** ( :white_check_mark: )
    - **State of the system**: Territory owner is already `playerOne`; `setOwner(playerTwo)` is called
    - **Expected output**: Territory owner is updated to `playerTwo`; `isOwnedBy(playerOne)` returns `false`; `isOwnedBy(playerTwo)` returns `true`

- **TC11: Setting owner to NullPlayer raises exception** ( :white_check_mark: )
    - **State of the system**: Territory owner is currently `playerOne`; `setOwner(nullPlayer)` is called
    - **Expected output**: Exception is raised; territory owner remains `playerOne`

---

### Method under test: `isOwnedBy(Player player)`

- **TC12: Returns true when territory is owned by given HumanPlayer** ( implemented in TC9 )
    - **State of the system**: Territory owner is `playerOne`
    - **Expected output**: `isOwnedBy(playerOne)` returns `true`

- **TC13: Returns false when territory is owned by a different HumanPlayer** ( implemented in TC10 )
    - **State of the system**: Territory owner is `playerOne`
    - **Expected output**: `isOwnedBy(playerTwo)` returns `false`

- **TC14: Returns false when territory is unclaimed** ( :white_check_mark: )
    - **State of the system**: Territory owner is `NullPlayer`
    - **Expected output**: `isOwnedBy(playerOne)` returns `false`

---

### Method under test: `isUnclaimed()`

- **TC15: Returns true when owner is NullPlayer** ( implemented in TC8 )
    - **State of the system**: Territory owner is `NullPlayer`
    - **Expected output**: `isUnclaimed()` returns `true`

- **TC16: Returns false after owner is set to HumanPlayer** ( implemented in TC9 )
    - **State of the system**: `setOwner(playerOne)` has been called with a `HumanPlayer`
    - **Expected output**: `isUnclaimed()` returns `false`

---

### Method under test: `placeArmies(HashMap<ArmyType, Integer> pieces)`

- **TC17: Place one Infantry on territory with no existing pieces** ( :white_check_mark: )
    - **State of the system**: Territory has no pieces; `placeArmies()` is called with a map containing `INFANTRY -> 1`
    - **Expected output**: Method returns `true`; territory contains exactly one Infantry piece

- **TC18: Place one Infantry on territory with existing Infantry** ( :white_check_mark: )
    - **State of the system**: Territory already contains `INFANTRY -> 1`; `placeArmies()` is called with a map containing `INFANTRY -> 1`
    - **Expected output**: Method returns `true`; territory contains exactly two Infantry pieces

---

### Method under test: `addArmies(HashMap<ArmyType, Integer> armiesToAdd)`

- **TC19: Add zero armies** ( :white_check_mark: )
    - **State of the system**: Territory has an existing army count; `addArmies()` is called with `INFANTRY -> 0`
    - **Expected output**: Method returns `true`; territory army count does not change

- **TC20: Add one army to empty territory** ( :white_check_mark: )
    - **State of the system**: Territory has `0` armies; `addArmies()` is called with `INFANTRY -> 1`
    - **Expected output**: Method returns `true`; territory army count becomes `1`

- **TC21: Add multiple armies to occupied territory** ( :white_check_mark: )
    - **State of the system**: Territory already has at least `1` army; `addArmies()` is called with more than one army
    - **Expected output**: Method returns `true`; territory army count increases by the total armies added

---

### Method under test: `removeArmies(HashMap<ArmyType, Integer> armiesToRemove)`

- **TC22: Reject zero armies removed** ( :white_check_mark: )
    - **State of the system**: Territory has at least `1` army; `removeArmies()` is called with `INFANTRY -> 0`
    - **Expected output**: Method returns `false`; territory army count does not change

- **TC23: Reject negative armies removed** ( :x: )
    - **State of the system**: Territory has at least `1` army; `removeArmies()` is called with `INFANTRY -> -1`
    - **Expected output**: Method returns `false`; territory army count does not change

- **TC24: Remove one army from territory with one army** ( :x: )
    - **State of the system**: Territory has exactly `1` army; `removeArmies()` is called with `INFANTRY -> 1`
    - **Expected output**: Method returns `true`; territory army count becomes `0`

- **TC25: Remove one army while armies remain** ( :x: )
    - **State of the system**: Territory has more than `1` army; `removeArmies()` is called with `INFANTRY -> 1`
    - **Expected output**: Method returns `true`; territory army count decreases by `1`

- **TC26: Remove multiple armies while armies remain** ( :x: )
    - **State of the system**: Territory has more armies than the requested removal; `removeArmies()` is called with more than one army
    - **Expected output**: Method returns `true`; territory army count decreases by the total armies removed

- **TC27: Remove exactly all armies** ( :x: )
    - **State of the system**: Territory has exactly the same number of armies as the requested removal
    - **Expected output**: Method returns `true`; territory army count becomes `0`

- **TC28: Reject removing more armies than present** ( :x: )
    - **State of the system**: Territory has `2` armies; `removeArmies()` is called with `INFANTRY -> 3`
    - **Expected output**: Method returns `false`; territory army count remains `2`
