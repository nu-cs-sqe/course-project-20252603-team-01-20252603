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

- **TC7: Registers maximum number of players** ( :white_check_mark: )
    - **State of the system**: Board initialized; view returns player count `6`; view returns six unique names and colors
    - **Expected output**: Model stores exactly six players, each with `20` available Infantry

- **TC8: Re-prompts after invalid player count** ( :white_check_mark: )
    - **State of the system**: Board initialized; view first returns player count `2`, then returns `3`
    - **Expected output**: Error is displayed after `2`; setup asks again and continues registration using player count `3`

- **TC9: Re-prompts after duplicate color** ( :white_check_mark: )
    - **State of the system**: Board initialized; second player first selects a color already chosen by player 1, then selects an available color
    - **Expected output**: Error is displayed for duplicate color; second player is asked for color again; final registered players have unique colors

- **TC10: Sets first player to lowest random index** ( :white_check_mark: )
    - **State of the system**: Three players registered; random starting index resolves to `0`
    - **Expected output**: Current player index is set to `0`; view announces the first registered player

- **TC11: Sets first player to highest random index** ( :white_check_mark: )
    - **State of the system**: Three players registered; random starting index resolves to `2`
    - **Expected output**: Current player index is set to `2`; view announces the third registered player

---

### Method under test: `handleTerritoryClaiming()`

- **TC12: Current player claims one unclaimed territory successfully** ( :x: )
    - **State of the system**: Setup controller has a model with at least one unclaimed territory; current player selects an unclaimed territory and exactly one Infantry
    - **Expected output**: `claimTerritoryDuringSetup()` is called successfully; updated board is displayed; current player's remaining armies are displayed; controller advances to the next player

- **TC13: Players take turns in established order while territories remain unclaimed** ( :x: )
    - **State of the system**: Setup controller has multiple players in an established turn order; more than one territory remains unclaimed
    - **Expected output**: Each successful claim advances to the next player in order; after the last player claims, turn order wraps back to the first player

- **TC14: Already claimed territory re-prompts same player** ( :x: )
    - **State of the system**: Current player selects a territory already owned by another player
    - **Expected output**: Error message is displayed; controller does not advance to the next player; same player is prompted again

- **TC15: Invalid Infantry count re-prompts same player** ( :x: )
    - **State of the system**: Current player selects an unclaimed territory but enters a number of Infantry other than exactly `1`
    - **Expected output**: Error message is displayed; controller does not advance to the next player; same player is prompted again

- **TC16: Territory claiming stops when all territories are claimed** ( :x: )
    - **State of the system**: Controller is running territory claiming; the final unclaimed territory is successfully claimed
    - **Expected output**: Claiming loop ends; system announces that all territories have been claimed; controller proceeds toward initial army placement

- **TC17: Territory claiming continues when one territory remains unclaimed** ( :x: )
    - **State of the system**: 41 territories are claimed and exactly 1 territory remains unclaimed
    - **Expected output**: Controller continues prompting players; claiming phase does not end yet