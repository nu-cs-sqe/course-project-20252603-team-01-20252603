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

### Method under test: `displayCurrentPlayerArmies(String availableArmies)`

- **TC17: Displays current player's available army pool** ( :x: )
    - **State of the system**: Model provides a formatted string containing available Infantry, Cavalry, and Artillery counts
    - **Expected output**: Output displays the available-armies string

---

### Method under test: `promptReinforcement()`

- **TC18: Returns territory name with one Infantry placement** ( :x: )
    - **State of the system**: Player enters `"Alaska 1 0 0"`
    - **Expected output**: Returns a list containing `"Alaska"`, `"1"`, `"0"`, and `"0"`

- **TC19: Returns territory name with multiple mixed army types** ( :x: )
    - **State of the system**: Player enters `"Alaska 15 2 3"`
    - **Expected output**: Returns a list containing `"Alaska"`, `"15"`, `"2"`, and `"3"`

- **TC20: Returns territory name with zero army placement for model validation** ( :x: )
    - **State of the system**: Player enters `"Alaska 0 0 0"`
    - **Expected output**: Returns a list containing `"Alaska"`, `"0"`, `"0"`, and `"0"`

- **TC21: Returns territory name with negative army count for model validation** ( :x: )
    - **State of the system**: Player enters `"Alaska -1 0 0"`
    - **Expected output**: Returns a list containing `"Alaska"`, `"-1"`, `"0"`, and `"0"`

