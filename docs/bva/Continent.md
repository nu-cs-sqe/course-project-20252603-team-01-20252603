# BVA Analysis — `Continent`


### Method under test: `Continent(String name, int bonusArmies)` *(constructor)*

- **TC1: Valid name and bonus** ( :white_check_mark: )
    - **State of the system**: Constructing `Continent("Asia", 7)`
    - **Expected output**: Object created successfully; `getName()` returns `"Asia"`

- **TC2: Minimum valid bonus armies** ( :white_check_mark: )
    - **State of the system**: Constructing a continent with `bonusArmies = 2` (Australia / South America)
    - **Expected output**: Object created successfully; `getBonusArmies()` returns `2`

- **TC3: Maximum valid bonus armies** ( :white_check_mark: )
    - **State of the system**: Constructing a continent with `bonusArmies = 7` (Asia)
    - **Expected output**: Object created successfully; `getBonusArmies()` returns `7`

- **TC4: Below minimum bonus armies — boundary just outside** ( :white_check_mark: )
    - **State of the system**: Constructing a continent with `bonusArmies = 1`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC5: Above maximum bonus armies — boundary just outside** ( :white_check_mark: )
    - **State of the system**: Constructing a continent with `bonusArmies = 8`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC6: Zero bonus armies** ( :white_check_mark: )
    - **State of the system**: Constructing a continent with `bonusArmies = 0`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC7: Negative bonus armies** ( :white_check_mark: )
    - **State of the system**: Constructing a continent with `bonusArmies = -1`
    - **Expected output**: `IllegalArgumentException` thrown

- **TC9: Empty name** ( :white_check_mark: )
    - **State of the system**: Constructing a continent with `name = ""`, `bonusArmies = 5`
    - **Expected output**: `IllegalArgumentException` thrown

---

### Method under test: `getBonusArmies()`

- **TC10: Returns correct bonus — minimum value** ( implemented in TC2 )
    - **State of the system**: Continent constructed with `bonusArmies = 2`
    - **Expected output**: Returns `2`

- **TC11: Returns correct bonus — maximum value** ( implemented in TC3 )
    - **State of the system**: Continent constructed with `bonusArmies = 7`
    - **Expected output**: Returns `7`

---

### Method under test: `containsTerritory(Territory territory)`

- **TC12: Territory found — first position** ( :white_check_mark: )
    - **State of the system**: Continent has 4 territories; target territory is at index 0
    - **Expected output**: Returns `true`

- **TC13: Territory found — last position** ( :white_check_mark: )
    - **State of the system**: Continent has 4 territories; target territory is at the last index
    - **Expected output**: Returns `true`

- **TC14: Territory not in list** ( :white_check_mark: )
    - **State of the system**: Continent has 4 territories; queried territory belongs to a different continent
    - **Expected output**: Returns `false`

- **TC15: Null territory argument** ( :white_check_mark: )
    - **State of the system**: Continent has 4 territories; `null` passed as argument
    - **Expected output**: Returns `false` — no NPE leaked to caller

- **TC16: Empty territory list** ( :white_check_mark: )
    - **State of the system**: Continent has no territories added yet (freshly constructed, empty list)
    - **Expected output**: Returns `false` — nothing to match against

---

### Board correctness — all 6 Risk continents *(parameterised, one commit)*

- **TC17: North America** ( :white_check_mark: )
    - **State of the system**: Continent constructed with `name = "North America"`, `bonusArmies = 5`
    - **Expected output**: `getBonusArmies()` returns `5`

- **TC18: South America** ( :white_check_mark: )
    - **State of the system**: Continent constructed with `name = "South America"`, `bonusArmies = 2`
    - **Expected output**: `getBonusArmies()` returns `2`

- **TC19: Europe** ( :white_check_mark: )
    - **State of the system**: Continent constructed with `name = "Europe"`, `bonusArmies = 5`
    - **Expected output**: `getBonusArmies()` returns `5`

- **TC20: Africa** ( :white_check_mark: )
    - **State of the system**: Continent constructed with `name = "Africa"`, `bonusArmies = 3`
    - **Expected output**: `getBonusArmies()` returns `3`

- **TC21: Asia** ( :white_check_mark: )
    - **State of the system**: Continent constructed with `name = "Asia"`, `bonusArmies = 7`
    - **Expected output**: `getBonusArmies()` returns `7`

- **TC22: Australia** ( :white_check_mark: )
    - **State of the system**: Continent constructed with `name = "Australia"`, `bonusArmies = 2`
    - **Expected output**: `getBonusArmies()` returns `2`

