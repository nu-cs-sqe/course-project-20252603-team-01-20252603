# BVA Analysis — `NullPlayer`


### Method under test: `NullPlayer()`

- **TC1: Constructs placeholder player for unassigned ownership** ( :x: )
    - **State of the system**: `new NullPlayer()` called for an unclaimed territory owner
    - **Expected output**: Object is created and can be used anywhere a `Player` reference is required for unassigned ownership

---

### Method under test: `getName()`

- **TC2: Returns empty name placeholder** ( :x: )
    - **State of the system**: `NullPlayer` constructed; `getName()` called
    - **Expected output**: Returns an empty string or other agreed placeholder value indicating no assigned player

---

### Method under test: `getColor()`

- **TC3: Returns no-color placeholder** ( :x: )
    - **State of the system**: `NullPlayer` constructed; `getColor()` called
    - **Expected output**: Returns no playable color, an unassigned color value, or another agreed placeholder that cannot conflict with Red, Blue, Green, Yellow, Black, or Purple

---

### Method under test: `getAvailableArmies()`

- **TC4: Returns empty available-army pool** ( :x: )
    - **State of the system**: `NullPlayer` constructed; `getAvailableArmies()` called
    - **Expected output**: Available armies contain zero Infantry, zero Cavalry, and zero Artillery

---

### Method under test: `hasAvailableArmies()`

- **TC5: Returns false for placeholder player** ( :x: )
    - **State of the system**: `NullPlayer` constructed with no assigned armies
    - **Expected output**: Returns `false`
