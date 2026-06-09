# BVA Analysis — `NullPlayer`


### Method under test: `NullPlayer()`

- **TC1: Constructs placeholder player for unassigned ownership** ( :white_check_mark: )
    - **State of the system**: `new NullPlayer()` called for an unclaimed territory owner
    - **Expected output**: Object is created and can be used anywhere a `Player` reference is required for unassigned ownership

---

### Method under test: `getName()`

- **TC2: Returns empty name placeholder** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `getName()` called
    - **Expected output**: Returns an empty string or other agreed placeholder value indicating no assigned player

---

### Method under test: `getColor()`

- **TC3: Returns no-color placeholder** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `getColor()` called
    - **Expected output**: Returns no playable color, an unassigned color value, or another agreed placeholder that cannot conflict with Red, Blue, Green, Yellow, Black, or Purple

---

### Method under test: `getAvailableArmies()`

- **TC4: Returns empty available-army display string** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `getAvailableArmies()` called
      **Expected output**: Exception is raised because `NullPlayer` does not represent a real player with an available army pool

---

### Method under test: `hasAvailableArmies(HashMap<ArmyType, Integer> requiredArmies)`

- **TC5: Returns false when one Infantry is required** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `hasAvailableArmies()` is called with a map containing `INFANTRY -> 1`
    - **Expected output**: Exception is raised because `NullPlayer` does not represent a real player with an available army pool

- **TC6: Returns false when zero Infantry is required** ( :x: )
    - **State of the system**: `NullPlayer` constructed; `hasAvailableArmies()` is called with a map containing `INFANTRY -> 0`
    - **Expected output**: Exception is raised because `NullPlayer` does not represent a real player with an available army pool