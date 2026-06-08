# BVA Analysis — `GameController`


### Method under test: `startGame()`

- **TC1: Board is initialized after startGame() is called** ( :x: )
    - **State of the system**: `GameController` constructed with a fresh `GameModel`, `ConsoleView`, and `SetupController`; `startGame()` called
    - **Expected output**: `model.getContinents().size()` returns `6`; total territory count equals `42`

- **TC2: All territories are unclaimed after startGame() is called** ( :x: )
    - **State of the system**: `startGame()` called
    - **Expected output**: Every territory returns `isUnclaimed() == true`

- **TC3: Deck is ready after startGame() is called** ( :x: )
    - **State of the system**: `startGame()` called
    - **Expected output**: `model.getDeck().size()` returns `44`; `model.getDeck().isEmpty()` returns `false`