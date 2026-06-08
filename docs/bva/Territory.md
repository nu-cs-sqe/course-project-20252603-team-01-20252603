# BVA Analysis — `Territory`

---

### Method under test: `Territory(String name, Continent continent, List<Territory> adjacentTerritories)`

- **TC1: Valid construction** ( :white_check_mark: )
    - **State of the system**: Constructing `Territory("Alaska", northAmerica, List.of(northwestTerritory, alberta, kamchatka))`
    - **Expected output**: Object created; `getName()` returns `"Alaska"`; `getContinent()` returns `northAmerica`

  - **TC2: Territory starts unclaimed** ( :white_check_mark: )
      - **State of the system**: Freshly constructed `Territory("Alaska", northAmerica, neighbours)`
      - **Expected output**: `isUnclaimed()` returns `true`

  - **TC3: Adjacency list stored correctly** ( :white_check_mark: )
      - **State of the system**: Constructed with a list of 3 neighbours
      - **Expected output**: `getAdjacentTerritories().size()` returns `3`; list contains the correct territories

  - **TC4: Empty adjacency list** ( :white_check_mark: )
      - **State of the system**: Constructed with an empty `List<Territory>`
      - **Expected output**: `getAdjacentTerritories()` returns an empty list — no exception thrown

  - **TC5: Null name** ( :white_check_mark: )
      - **State of the system**: Constructing `Territory(null, northAmerica, neighbours)`
      - **Expected output**: `IllegalArgumentException` thrown

  - **TC6: Empty name** ( :white_check_mark: )
      - **State of the system**: Constructing `Territory("", northAmerica, neighbours)`
      - **Expected output**: `IllegalArgumentException` thrown

  - **TC7: Null continent** ( :white_check_mark: )
      - **State of the system**: Constructing `Territory("Alaska", null, neighbours)`
      - **Expected output**: `IllegalArgumentException` thrown

  - **TC8: Null adjacency list** ( :white_check_mark: )
      - **State of the system**: Constructing `Territory("Alaska", northAmerica, null)`
      - **Expected output**: `IllegalArgumentException` thrown

---

### Method under test: `getName()`

- **TC9: Returns correct name** ( implemented in TC1 )
    - **State of the system**: Territory constructed with `name = "Alaska"`
    - **Expected output**: Returns `"Alaska"`

---

### Method under test: `isUnclaimed()`

- **TC10: Returns true at initialization** ( implemented in TC2 )
    - **State of the system**: Freshly constructed territory; owner never set
    - **Expected output**: Returns `true`

---

### Method under test: `getAdjacentTerritories()`

- **TC11: Returns correct adjacency list** ( implemented in TC3 )
    - **State of the system**: Territory constructed with 3 neighbours
    - **Expected output**: Returns list of size 3 containing the correct territories

  - **TC12: Returns empty list when no neighbours passed** ( implemented in TC4 )
      - **State of the system**: Territory constructed with empty adjacency list
      - **Expected output**: Returns empty list