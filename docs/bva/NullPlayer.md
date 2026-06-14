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

### Method under test: `getAvailableArmies()`

- **TC4: Returns empty available-army display string** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `getAvailableArmies()` called
      **Expected output**: Exception is raised because `NullPlayer` does not represent a real player with an available army pool

---

### Method under test: `hasAvailableArmies(HashMap<ArmyType, Integer> requiredArmies)`

- **TC5: Returns false when one Infantry is required** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `hasAvailableArmies()` is called with a map containing `INFANTRY -> 1`
    - **Expected output**: Exception is raised because `NullPlayer` does not represent a real player with an available army pool

- **TC6: Returns false when zero Infantry is required** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `hasAvailableArmies()` is called with a map containing `INFANTRY -> 0`
    - **Expected output**: Exception is raised because `NullPlayer` does not represent a real player with an available army pool

---

### Method under test: `addArmiesToAvailableBasedOnTerritories()`

- **TC7: Territory-based reinforcement is rejected for NullPlayer** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `addArmiesToAvailableBasedOnTerritories()` called
    - **Expected output**: `UnsupportedOperationException` is raised with message `"NullPlayer cannot receive armies."` because `NullPlayer` does not represent a real active player

---

### Method under test: `tradeCardsAndAddArmies(List<Integer> cardIndices, Deck deck, int numSetsTradedIn)`

- **TC8: Card trade-in is rejected for NullPlayer** ( :white_check_mark: )
    - **State of the system**: `NullPlayer` constructed; `tradeCardsAndAddArmies(...)` called
    - **Expected output**: `UnsupportedOperationException` is raised with message `"NullPlayer cannot trade cards."` because `NullPlayer` does not represent a real active player
