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

### Method under test: `displayPlayers(List<Player> players)`

- **TC10: Displays minimum registered player list** ( :white_check_mark: )
    - **State of the system**: Three players have been registered with unique colors
    - **Expected output**: Displays all three player names and colors for confirmation

- **TC11: Displays maximum registered player list** ( :x: )
    - **State of the system**: Six players have been registered with unique colors
    - **Expected output**: Displays all six player names and colors for confirmation

---

### Method under test: `displayStartingPlayer(Player player)`

- **TC12: Displays selected starting player** ( :x: )
    - **State of the system**: A first player has been selected automatically by setup
    - **Expected output**: Displays the selected player's name and color
