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

- **TC5: initializeBoard() can be called only once** ( :white_check_mark: )
    - **State of the system**: `initializeBoard()` called twice on the same `SetupController`
    - **Expected output**: Second call has no effect — continent and territory counts remain 6 and 42 with no duplicates

---

### Method under test: `initializePlayers()`

- **TC6: Registers minimum number of players** ( :white_check_mark: )
    - **State of the system**: Board initialized; view returns player count `3`; view returns three unique names and colors
    - **Expected output**: Model stores exactly three players, each with `35` available Infantry

- **TC7: Registers maximum number of players** ( :x: )
    - **State of the system**: Board initialized; view returns player count `6`; view returns six unique names and colors
    - **Expected output**: Model stores exactly six players, each with `20` available Infantry

- **TC8: Re-prompts after invalid player count** ( :x: )
    - **State of the system**: Board initialized; view first returns player count `2`, then returns `3`
    - **Expected output**: Error is displayed after `2`; setup asks again and continues registration using player count `3`

- **TC9: Re-prompts after duplicate color** ( :x: )
    - **State of the system**: Board initialized; second player first selects a color already chosen by player 1, then selects an available color
    - **Expected output**: Error is displayed for duplicate color; second player is asked for color again; final registered players have unique colors

- **TC10: Sets first player to lowest random index** ( :x: )
    - **State of the system**: Three players registered; random starting index resolves to `0`
    - **Expected output**: Current player index is set to `0`; view announces the first registered player

- **TC11: Sets first player to highest random index** ( :x: )
    - **State of the system**: Three players registered; random starting index resolves to `2`
    - **Expected output**: Current player index is set to `2`; view announces the third registered player
