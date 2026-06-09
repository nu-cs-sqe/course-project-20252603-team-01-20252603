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

- **TC4: Returns registered player color** ( :x: )
    - **State of the system**: Human player constructed with color `PlayerColor.RED`
    - **Expected output**: Returns `PlayerColor.RED`

---

### Method under test: `getAvailableArmies()`

- **TC5: Returns available Infantry at lower setup boundary** ( :x: )
    - **State of the system**: Human player constructed with `20` starting Infantry
    - **Expected output**: Available armies contain `20` Infantry and no Cavalry or Artillery

- **TC6: Returns available Infantry at upper setup boundary** ( :x: )
    - **State of the system**: Human player constructed with `35` starting Infantry
    - **Expected output**: Available armies contain `35` Infantry and no Cavalry or Artillery

---

### Method under test: `hasAvailableArmies()`

- **TC7: Returns true after setup army assignment** ( :x: )
    - **State of the system**: Human player constructed with any valid setup Infantry count from `20` to `35`
    - **Expected output**: Returns `true`
