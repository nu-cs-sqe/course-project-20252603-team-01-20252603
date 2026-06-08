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