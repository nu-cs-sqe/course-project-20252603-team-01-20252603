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

- **TC12: Current player claims one unclaimed territory successfully** ( :white_check_mark: )
    - **State of the system**: Setup controller has a model with at least one unclaimed territory; current player selects an unclaimed territory and exactly one Infantry
    - **Expected output**: `claimTerritoryDuringSetup()` is called successfully; updated board is displayed; current player's remaining armies are displayed; controller advances to the next player

- **TC13: Players take turns in established order while territories remain unclaimed** ( :white_check_mark: )
    - **State of the system**: Setup controller has multiple players in an established turn order; more than one territory remains unclaimed
    - **Expected output**: Each successful claim advances to the next player in order; after the last player claims, turn order wraps back to the first player

- **TC14: Already claimed territory re-prompts same player** ( :white_check_mark: )
    - **State of the system**: Current player selects a territory already owned by another player
    - **Expected output**: Error message is displayed; controller does not advance to the next player; same player is prompted again

- **TC15: Invalid Infantry count re-prompts same player** ( :white_check_mark: )
    - **State of the system**: Current player selects an unclaimed territory but enters a number of Infantry other than exactly `1`
    - **Expected output**: Error message is displayed; controller does not advance to the next player; same player is prompted again

- **TC16: Territory claiming stops when all territories are claimed** ( :white_check_mark: )
    - **State of the system**: Controller is running territory claiming; the final unclaimed territory is successfully claimed
    - **Expected output**: Claiming loop ends; system announces that all territories have been claimed; controller proceeds toward initial army placement

- **TC17: Territory claiming continues when one territory remains unclaimed** ( :white_check_mark: )
    - **State of the system**: 41 territories are claimed and exactly 1 territory remains unclaimed
    - **Expected output**: Controller continues prompting players; claiming phase does not end yet

- **TC18: Remaining-army placement does not start until all territories are claimed** ( :white_check_mark: )
    - **State of the system**: Territory claiming loop is running and at least one territory is still unclaimed
    - **Expected output**: Controller continues the territory-claiming flow and does not prompt for remaining-army placement yet

- **TC19: Current player with one remaining army places it successfully** ( :white_check_mark: )
    - **State of the system**: All 42 territories are claimed; current player has exactly `1` available Infantry and selects one of their owned territories
    - **Expected output**: Controller displays current player's territories, prompts for a territory, calls `addArmiesDuringSetup()` with exactly one Infantry, advances to the next player, and the player's available Infantry becomes `0`

- **TC20: Current player with more than one remaining army places one and continues later** ( :white_check_mark: )
    - **State of the system**: All territories are claimed; current player has more than `1` available Infantry and selects one of their owned territories
    - **Expected output**: Controller places exactly `1` Infantry, advances to the next player, and leaves the current player with remaining armies for a later turn

- **TC21: Skips current player with zero armies remaining** ( :white_check_mark: )
    - **State of the system**: All territories are claimed; current player has `0` available Infantry; at least one later player still has available Infantry
    - **Expected output**: Controller does not display placement prompt for the current player; it calls `advanceCurrentPlayerIndex()` and continues with the next eligible player

- **TC22: Skips multiple players with zero armies remaining** ( :white_check_mark: )
    - **State of the system**: All territories are claimed; two or more consecutive players have `0` available Infantry; a later player has at least `1` available Infantry
    - **Expected output**: Controller advances past each player with `0` armies and prompts the next player who can still place an Infantry

- **TC23: Unowned territory during remaining-army placement re-prompts same player** ( :white_check_mark: )
    - **State of the system**: All territories are claimed; current player has available Infantry but selects a territory owned by another player
    - **Expected output**: `addArmiesDuringSetup()` returns `false`; controller displays an error; controller does not advance to the next player; same player is prompted again

- **TC24: Placement loop stops when all players have zero armies remaining** ( :white_check_mark: )
    - **State of the system**: All territories are claimed and every player has `0` available Infantry
    - **Expected output**: Controller exits the remaining-army placement loop and calls `displaySetupPhaseComplete()`

- **TC25: Last remaining army across all players completes setup** ( :white_check_mark: )
    - **State of the system**: Exactly one player has exactly `1` available Infantry and all other players have `0`
    - **Expected output**: That player places the Infantry; controller advances or checks completion; setup completion message is displayed

---

### Method under test: `handleFortifyPhase()`

- **TC26: Player skips fortification** ( :x: )
    - **State of the system**: Current player is prompted for fortification and enters `"no"`
    - **Expected output**: No source, destination, or army-count prompts are shown; no territory army counts change; controller advances to the next player

- **TC27: Player chooses to fortify** ( :x: )
    - **State of the system**: Current player is prompted for fortification and enters `"yes"`; source, destination, and army count form a valid fortify move
    - **Expected output**: Controller displays current player's territories, passes the move to `GameModel.fortifyTerritory(...)`, displays the updated territories, and advances to the next player

- **TC28: Invalid fortify choice re-prompts** ( :x: )
    - **State of the system**: Current player enters a value other than yes/y/no/n at the fortify choice prompt
    - **Expected output**: Controller displays `"Invalid fortify choice."`; no army movement is attempted; the fortify choice is requested again

- **TC29: Non-numeric army count re-prompts move input** ( :x: )
    - **State of the system**: Current player chooses to fortify, enters valid source and destination names, then enters a non-numeric army count
    - **Expected output**: Controller displays `"Invalid army count."`; `GameModel.fortifyTerritory(...)` is not called; source, destination, and army count are requested again

- **TC30: Model rejects invalid fortify move** ( :x: )
    - **State of the system**: Current player chooses to fortify and enters numeric move input, but `GameModel.fortifyTerritory(...)` returns `false`
    - **Expected output**: Controller displays `"Invalid fortify move."`; current player is not advanced; source, destination, and army count are requested again

- **TC31: Valid move after one invalid move ends fortify phase** ( :x: )
    - **State of the system**: First fortify attempt returns `false`; second fortify attempt returns `true`
    - **Expected output**: Controller allows exactly one successful fortify move, displays the updated territories once after success, advances to the next player, and exits the phase
