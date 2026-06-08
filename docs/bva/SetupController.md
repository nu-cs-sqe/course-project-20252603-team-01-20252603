# BVA Analysis — `SetupController`


### Method under test: `SetupController(GameModel model, ConsoleView view)`

- **TC1: Valid construction** ( :white_check_mark: )
    - **State of the system**: Constructing `SetupController` with a valid `GameModel` and `ConsoleView`
    - **Expected output**: Object created without error

---

### Method under test: `initializeBoard()`

- **TC2: Delegates to model — 6 continents created** ( :white_check_mark: )
    - **State of the system**: `SetupController` constructed with a fresh `GameModel`; `initializeBoard()` called
    - **Expected output**: `model.getContinents().size()` returns `6`

- **TC3: Delegates to model — 42 territories created** ( :white_check_mark: )
    - **State of the system**: `initializeBoard()` called
    - **Expected output**: Total territory count across all continents equals `42`

- **TC4: Delegates to model — deck ready with 44 cards** ( :white_check_mark: )
    - **State of the system**: `initializeBoard()` called
    - **Expected output**: `model.getDeck().size()` returns `44`

- **TC5: initializeBoard() can be called only once** ( :x: )
    - **State of the system**: `initializeBoard()` called twice on the same `SetupController`
    - **Expected output**: Second call has no effect — continent and territory counts remain 6 and 42 with no duplicates