# BVA Analysis — `RiskCard`


---

### Method under test: `RiskCard(Territory territory, CardType type, boolean wild)`

- **TC1: Non-wild card with territory and type** ( :x: )
    - **State of the system**: Constructing `RiskCard(alaska, CardType.INFANTRY, false)`
    - **Expected output**: Object created; `isWild()` returns `false`; `getType()` returns `INFANTRY`; `matchesTerritory(alaska)` returns `true`

- **TC2: Wild card** ( :x: )
    - **State of the system**: Constructing `RiskCard(null, CardType.WILD, true)`
    - **Expected output**: Object created; `isWild()` returns `true`; `getType()` returns `WILD`

- **TC3: Non-wild card with CAVALRY type** ( :x: )
    - **State of the system**: Constructing `RiskCard(alaska, CardType.CAVALRY, false)`
    - **Expected output**: `getType()` returns `CAVALRY`

- **TC4: Non-wild card with ARTILLERY type** ( :x: )
    - **State of the system**: Constructing `RiskCard(alaska, CardType.ARTILLERY, false)`
    - **Expected output**: `getType()` returns `ARTILLERY`

---

### Method under test: `isWild()`

- **TC5: Non-wild card returns false** ( implemented in TC1 )
    - **State of the system**: Card constructed with `wild = false`
    - **Expected output**: Returns `false`

- **TC6: Wild card returns true** ( implemented in TC2 )
    - **State of the system**: Card constructed with `wild = true`
    - **Expected output**: Returns `true`

---

### Method under test: `getType()`

- **TC7: Returns INFANTRY** ( implemented in TC1 )
    - **State of the system**: Card constructed with `CardType.INFANTRY`
    - **Expected output**: Returns `INFANTRY`

- **TC8: Returns CAVALRY** ( implemented in TC3 )
    - **State of the system**: Card constructed with `CardType.CAVALRY`
    - **Expected output**: Returns `CAVALRY`

- **TC9: Returns ARTILLERY** ( implemented in TC4 )
    - **State of the system**: Card constructed with `CardType.ARTILLERY`
    - **Expected output**: Returns `ARTILLERY`

- **TC10: Returns WILD** ( implemented in TC2 )
    - **State of the system**: Card constructed with `CardType.WILD`
    - **Expected output**: Returns `WILD`

---

### Method under test: `matchesTerritory(Territory territory)`

- **TC11: Matches correct territory** ( implemented in TC1 )
    - **State of the system**: Card constructed with `alaska`; queried with `alaska`
    - **Expected output**: Returns `true`

- **TC12: Does not match different territory** ( :x: )
    - **State of the system**: Card constructed with `alaska`; queried with `brazil`
    - **Expected output**: Returns `false`

- **TC13: Wild card does not match any territory** ( :x: )
    - **State of the system**: Wild card constructed with `territory = null`; queried with any territory
    - **Expected output**: Returns `false`