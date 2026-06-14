# BVA Analysis — `GameController`


### Method under test: `startGame()`

- **TC1: Board is initialized after startGame() is called** ( :white_check_mark: )
    - **State of the system**: `GameController` constructed with a fresh `GameModel`, `ConsoleView`, and `SetupController`; `startGame()` called
    - **Expected output**: `model.getContinents().size()` returns `6`; total territory count equals `42`

- **TC2: Deck is ready after startGame() is called** ( :white_check_mark: )
    - **State of the system**: `startGame()` called
    - **Expected output**: `model.getDeck().size()` returns `44`; `model.getDeck().isEmpty()` returns `false`

- **TC3: Game loop begins only after setup completes** ( :white_check_mark: )
    - **State of the system**: `startGame()` is called on a controller with setup and turn dependencies
    - **Expected output**: `setupController.initializeBoard()` is called before any player turn is run

- **TC4: First winner check happens only after first completed turn** ( :white_check_mark: )
    - **State of the system**: First current player already controls all `42` territories before the loop starts
    - **Expected output**: Controller still calls `turnController.runPlayerTurn()` once before calling `model.currentPlayerHasWon()`

- **TC5: Winner after completed turn is displayed and loop stops** ( :white_check_mark: )
    - **State of the system**: Active current player completes a turn; `model.currentPlayerHasWon()` returns `true`
    - **Expected output**: Controller calls `view.displayWinner(model.getCurrentPlayerName())` exactly once and does not advance to the next player

- **TC6: No winner after completed turn advances to next active player** 
    - **State of the system**: Active current player completes a turn; `model.currentPlayerHasWon()` returns `false`
    - **Expected output**: Controller calls `model.advanceToNextActivePlayer()` and continues the game loop

- **TC7: Eliminated current player is skipped before running a turn**
    - **State of the system**: `model.currentPlayerIsEliminated()` returns `true` for the current player
    - **Expected output**: Controller calls `model.advanceToNextActivePlayer()` and does not call `turnController.runPlayerTurn()` for that player

- **TC8: Turn order wraps through model advancement**
    - **State of the system**: Last active player completes a turn without winning; `model.advanceToNextActivePlayer()` wraps to the first active player
    - **Expected output**: Controller delegates advancement to the model and then runs the next active player's turn

- **TC9: Game continues across multiple non-winning turns**
    - **State of the system**: Two active players complete turns; `model.currentPlayerHasWon()` returns `false` after the first turn and `true` after the second turn
    - **Expected output**: Controller runs two turns, advances once between turns, displays the winner after the second turn, and stops

- **TC10: Winner display uses current player name**
    - **State of the system**: `model.currentPlayerHasWon()` returns `true`; `model.getCurrentPlayerName()` returns `"Player 1"`
    - **Expected output**: Controller calls `view.displayWinner("Player 1")`

- **TC11: Game loop stops after winner is displayed**
    - **State of the system**: `model.currentPlayerHasWon()` returns `true` after a completed turn
    - **Expected output**: Controller does not run another turn and does not call `model.advanceToNextActivePlayer()`
