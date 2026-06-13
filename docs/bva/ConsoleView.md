# BVA Analysis — `ConsoleView`


### Method under test: `promptNumberOfPlayers()`

- **TC1: Reads minimum valid player count** ( :white_check_mark: )
    - **State of the system**: User enters `3` when prompted for number of players
    - **Expected output**: Returns `3`

- **TC2: Reads maximum valid player count** ( :white_check_mark: )
    - **State of the system**: User enters `6` when prompted for number of players
    - **Expected output**: Returns `6`

- **TC3: Reads below-minimum player count for model validation** ( :white_check_mark: )
    - **State of the system**: User enters `2` when prompted for number of players
    - **Expected output**: Returns `2` so `GameModel.setPlayerCount(2)` can reject it and setup can re-prompt

- **TC4: Reads above-maximum player count for model validation** ( :white_check_mark: )
    - **State of the system**: User enters `7` when prompted for number of players
    - **Expected output**: Returns `7` so `GameModel.setPlayerCount(7)` can reject it and setup can re-prompt

---

### Method under test: `promptPlayerName(int playerNumber)`

- **TC5: Reads first player name** ( :white_check_mark: )
    - **State of the system**: `promptPlayerName(1)` called and user enters a non-empty name
    - **Expected output**: Returns the entered name for player 1

- **TC6: Reads last player name in maximum-size game** ( :white_check_mark: )
    - **State of the system**: Six-player game; `promptPlayerName(6)` called and user enters a non-empty name
    - **Expected output**: Returns the entered name for player 6

---

### Method under test: `promptPlayerColor(String playerName, List<PlayerColor> availableColors)`

- **TC7: Reads color when all colors are available** ( :white_check_mark: )
    - **State of the system**: `availableColors` contains all six colors; user selects `RED`
    - **Expected output**: Returns `PlayerColor.RED`

- **TC8: Reads color when one color remains** ( :white_check_mark: )
    - **State of the system**: `availableColors` contains exactly one color; user selects that color
    - **Expected output**: Returns the only available `PlayerColor`

- **TC9: Reads unavailable color for model validation** ( :white_check_mark: )
    - **State of the system**: `availableColors` does not contain `RED`; user selects `RED`
    - **Expected output**: Returns `PlayerColor.RED` so `GameModel.addPlayer(...)` can reject the duplicate color and setup can re-prompt

---

### Method under test: `displayCurrentPlayer(String currentPlayerName)`

- **TC12: Displays selected starting player** ( :white_check_mark: )
    - **State of the system**: A first player has been selected automatically by setup; `currentPlayerName` contains the selected player name
    - **Expected output**: Output displays the selected current player name

---

### Method under test: `displayUnclaimedTerritoriesByContinent(String unclaimedTerritories)`

- **TC13: Displays unclaimed territories grouped by continent** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string containing only unclaimed territories grouped by continent
    - **Expected output**: Output displays the unclaimed territories string exactly as provided by the model

---

### Method under test: `displayCurrentPlayerClaimingStatus(String playerClaimingStatus)`

- **TC14: Displays current player's claiming status** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string containing the current player's name, available Infantry, and currently owned territories
    - **Expected output**: Output displays the current player's claiming status exactly as provided by the model

---

### Method under test: `getTerritoryChoiceDuringSetup()`

- **TC15: Returns entered territory choice during setup** ( :white_check_mark: )
    - **State of the system**: Player is prompted to enter the name of a territory to claim
    - **Expected output**: Returns the territory name entered by the player

---

### Method under test: `getInfantryChoiceDuringSetup()`

- **TC16: Returns entered Infantry count during setup** ( :white_check_mark: )
    - **State of the system**: Player is prompted to enter the number of Infantry to place
    - **Expected output**: Returns the Infantry count entered by the player so the model can validate it

---

### Method under test: `displayCurrentPlayerTerritoriesByContinent(String territoriesByContinent)`

- **TC17: Displays one owned territory grouped by continent** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string where the current player owns exactly one territory
    - **Expected output**: Output displays the provided territory string exactly

- **TC18: Displays multiple owned territories grouped by continent** ( :white_check_mark: )
    - **State of the system**: Model provides a formatted string where the current player owns more than one territory across one or more continents
    - **Expected output**: Output displays the provided territory string exactly

---

### Method under test: `promptCurrentPlayerTerritoryChoice()`

- **TC19: Returns first owned territory name entered for remaining-army placement** ( :white_check_mark: )
    - **State of the system**: Current player is prompted to choose one of their territories and enters the name of a territory they own
    - **Expected output**: Returns the entered territory name so the model can place one Infantry

- **TC20: Returns unowned territory name for model validation** ( :white_check_mark: )
    - **State of the system**: Current player enters the name of a territory owned by another player
    - **Expected output**: Returns the entered territory name so `addArmiesDuringSetup()` can reject it and setup can re-prompt

---

### Method under test: `displaySetupPhaseComplete()`

- **TC21: Displays setup completion message once placement is complete** ( :white_check_mark: )
    - **State of the system**: All territories are claimed and all players have `0` available Infantry
    - **Expected output**: Output displays a message saying setup is complete and the game is starting now
